package com.agb.billing.customer.fragments

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.agb.billing.customer.R
import com.agb.billing.customer.activities.MyPlanActivity
import com.agb.billing.customer.adapter.PendingPlanAdapter
import com.agb.billing.customer.databinding.FragmentPendingPlanBinding
import com.agb.billing.customer.delegate.ConfirmationDelegate
import com.agb.billing.customer.delegate.PendingPlanDelegate
import com.agb.billing.customer.dialog.ConfirmationDialog
import com.agb.billing.customer.modelVO.PendingPlanVO
import com.agb.billing.customer.networks.requests.CancelPlanRequest
import com.agb.billing.customer.networks.requests.PaginationRequest
import com.agb.billing.customer.networks.responses.PendingPlanListResponse
import com.agb.billing.customer.networks.responses.SuccessResponse
import com.agb.billing.customer.viewmodels.CancelPlanViewModel
import com.agb.billing.customer.viewmodels.PendingPlanListViewModel
import com.agb.billing.customer.views.CancelPlanView
import com.agb.billing.customer.views.PendingPlanView
import com.google.gson.Gson

class PendingPlanFragment : BaseFragment(),PendingPlanView,
    PendingPlanDelegate,ConfirmationDelegate,CancelPlanView {

    private var _binding : FragmentPendingPlanBinding ?= null
    private val binding get() = _binding!!
    lateinit var mView : View
    lateinit var mAdapter : PendingPlanAdapter
    lateinit var mActivity : MyPlanActivity
    lateinit var mViewModel : PendingPlanListViewModel
    lateinit var mCancelPlanViewModel : CancelPlanViewModel
    var pageNo = 1
    var prePageNo = 2
    var mList : MutableList<PendingPlanVO> = mutableListOf()
    var mCancelPlanVO : PendingPlanVO ?= null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as MyPlanActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPendingPlanBinding.inflate(inflater,container,false)
        mView = binding.root

        initLayout()
        initViewModel()
        viewLayoutVisible(false)
        pendingPlanApiCall()

        return mView
    }

    private fun pendingPlanApiCall() {
        val request = PaginationRequest()
        request.pageNo = pageNo
        mViewModel.getPendingPlanList(request)
    }

    private fun initViewModel() {
        mViewModel = ViewModelProvider(this).get(PendingPlanListViewModel::class.java)
        mViewModel.setView(this)

        mCancelPlanViewModel = ViewModelProvider(this).get(CancelPlanViewModel::class.java)
        mCancelPlanViewModel.setView(this)
    }

    private fun initLayout() {
        mAdapter = PendingPlanAdapter(this)
        binding.apply {

            swipeRefreshLayout.setColorSchemeColors(resources.getColor(R.color.colorSecondary))

            swipeRefreshLayout.setOnRefreshListener {
                viewLayoutVisible(false)
                pageNo = 1
                prePageNo = 2
                pendingPlanApiCall()
            }

            rvPendingPlan.layoutManager = LinearLayoutManager(mView.context)
            rvPendingPlan.setHasFixedSize(true)
            rvPendingPlan.adapter = mAdapter

//            rvPendingPlan.addOnScrollListener(object : RecyclerView.OnScrollListener(){
//                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                    if ((rvPendingPlan.layoutManager as LinearLayoutManager).itemCount >= 3) {
//                        if (((rvPendingPlan.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition() + 1) == (rvPendingPlan.layoutManager as LinearLayoutManager).itemCount) {
//                            if (prePageNo >= pageNo) {
//                                if (prePageNo == pageNo) {
//                                    prePageNo += 1
//                                }
//                                showProgress()
//                                //api call
//                                pendingPlanApiCall()
//                            }
//                        }
//                    }
//                    super.onScrolled(recyclerView, dx, dy)
//                }
//
//            })
        }
    }

    override fun onTapUndoPlan(data: PendingPlanVO) {
        mCancelPlanVO = data
        val dialog = ConfirmationDialog.newInstance(this)
        dialog.show(childFragmentManager,"confirmation")
    }

    override fun onTapConfirm() {
        cancelPlanApiCall()
    }

    private fun viewLayoutVisible(b: Boolean) {
        binding.apply {
            lyError.layoutError.visibility = View.GONE
            if (b) {
                shimmerLoading.stopShimmer()
                lyMain.visibility = View.VISIBLE
                shimmerLoading.visibility = View.GONE
            } else {
                shimmerLoading.startShimmer()
                lyMain.visibility = View.GONE
                shimmerLoading.visibility = View.VISIBLE
            }
        }
    }

    fun onrefreshList(){
        viewLayoutVisible(false)
        pageNo = 1
        prePageNo = 2
        Handler().postDelayed(object : Runnable{
            override fun run() {
                pendingPlanApiCall()
            }

        },2000)
    }

    fun cancelPlanApiCall(){
        showProgress()
        val request = CancelPlanRequest()
        request.id = mCancelPlanVO?.id
        request.pendingplanid = mCancelPlanVO?.pendingplanid
        mCancelPlanViewModel.getCancelPlan(request)
    }

    override fun setPendingPlanData(response: PendingPlanListResponse) {
        viewLayoutVisible(true)
        dismissProgress()
        binding.swipeRefreshLayout.isRefreshing = false

        if(response.data != null){
            Log.e("PENDING_PLAN_LIST", Gson().toJson(response.data))
            if(response.data!!.size > 0){
//                if(prePageNo == 2) {
                    mList = response.data!!
                    mAdapter.setNewData(mList)
//                }else{
//                    mList.addAll(response.data!!)
//                    mAdapter.notifyDataSetChanged()
//                }
//                pageNo += 1
            }else{
                hideLoadingError()
            }
        }else{
            hideLoadingError()
        }
    }

    override fun setCancelPlan(response: SuccessResponse) {
        dismissProgress()
        onrefreshList()
        mActivity.gotoActivePlan()
    }

    override fun showError(message: String, code: String) {
        binding.swipeRefreshLayout.isRefreshing = false
        hideLoadingNetwork()
    }

    override fun showInvalidSession(message: String, code: String) {
        binding.swipeRefreshLayout.isRefreshing = false
        mInvalidSession(mActivity,message)
    }

    override fun showNetworkFailed() {
        binding.swipeRefreshLayout.isRefreshing = false
        hideLoadingNetwork()
    }

    private fun hideLoadingError() {
        binding.apply {
            shimmerLoading.stopShimmer()
            shimmerLoading.visibility = View.GONE
            lyMain.visibility = View.GONE
            lyError.layoutError.visibility = View.VISIBLE
            lyError.ivError.setImageResource(R.drawable.ic_component_service)
            lyError.tvError.text = resources.getString(R.string.error_service)
        }
    }

    private fun hideLoadingNetwork() {
        binding.apply {
            shimmerLoading.stopShimmer()
            shimmerLoading.visibility = View.GONE
            lyMain.visibility = View.GONE
            lyError.layoutError.visibility = View.VISIBLE
        }
    }
    private fun showProgress() {
        try {
            showProgressDialog?.show()
        } catch (ex: Exception) {

        }

    }

    private fun dismissProgress() {
        try {
            showProgressDialog?.dismiss()
        } catch (ex: Exception) {

        }
    }

}