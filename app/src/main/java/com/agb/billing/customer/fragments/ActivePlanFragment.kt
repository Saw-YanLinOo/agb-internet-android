package com.agb.billing.customer.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.agb.billing.customer.R
import com.agb.billing.customer.activities.MyPlanActivity
import com.agb.billing.customer.adapter.ActivePlanAdapter
import com.agb.billing.customer.databinding.FragmentActivePlanBinding
import com.agb.billing.customer.delegate.ActivePlanDelegate
import com.agb.billing.customer.delegate.ChangePlanDelegate
import com.agb.billing.customer.dialog.ChangePlanDialog
import com.agb.billing.customer.dialog.TermAndConditionDialog
import com.agb.billing.customer.modelVO.ActivePlanVO
import com.agb.billing.customer.networks.requests.ChangePlanRequest
import com.agb.billing.customer.networks.requests.PaginationRequest
import com.agb.billing.customer.networks.responses.ActivePlanListResponse
import com.agb.billing.customer.networks.responses.SuccessResponse
import com.agb.billing.customer.viewmodels.ActivePlanListViewModel
import com.agb.billing.customer.views.ActivePlanView
import com.google.gson.Gson

class ActivePlanFragment : BaseFragment(),ActivePlanView,
    ActivePlanDelegate,ChangePlanDelegate {

    private var _binding : FragmentActivePlanBinding ?= null
    private val binding get() = _binding!!
    lateinit var mAdapter : ActivePlanAdapter
    lateinit var mView : View
    lateinit var mActivity : MyPlanActivity
    lateinit var mViewModel : ActivePlanListViewModel
    var pageNo = 1
    var prePageNo = 2
    var mList : MutableList<ActivePlanVO> = mutableListOf()
    lateinit var mRequest : ChangePlanRequest

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as MyPlanActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentActivePlanBinding.inflate(inflater,container,false)
        mView = binding.root
        initLayout()
        initViewModel()
        viewLayoutVisible(false)
        activePlanApiCall()

        return mView
    }

    private fun activePlanApiCall() {
        val request = PaginationRequest()
        request.pageNo = pageNo
        mViewModel.getActivePlanList(request)
    }

    private fun initViewModel() {
        mViewModel = ViewModelProvider(this).get(ActivePlanListViewModel::class.java)
        mViewModel.setView(this)
    }

    private fun initLayout() {
        mAdapter = ActivePlanAdapter(this)
        binding.apply {
            swipeRefreshLayout.setColorSchemeColors(resources.getColor(R.color.colorSecondary))
            swipeRefreshLayout.setOnRefreshListener {
                viewLayoutVisible(false)
                pageNo = 1
                prePageNo = 2
                activePlanApiCall()
            }

            rvActivePlan.layoutManager = LinearLayoutManager(mView.context)
            rvActivePlan.setHasFixedSize(true)
            rvActivePlan.adapter = mAdapter

//            rvActivePlan.addOnScrollListener(object : RecyclerView.OnScrollListener(){
//                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                    if ((rvActivePlan.layoutManager as LinearLayoutManager).itemCount >= 3) {
//                        if (((rvActivePlan.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition() + 1) == (rvActivePlan.layoutManager as LinearLayoutManager).itemCount) {
//                            if (prePageNo >= pageNo) {
//                                if (prePageNo == pageNo) {
//                                    prePageNo += 1
//                                }
//                                showProgress()
//                                //api call
//                                activePlanApiCall()
//                            }
//                        }
//                    }
//                    super.onScrolled(recyclerView, dx, dy)
//                }
//
//            })
        }
    }


    override fun onTapChangePlan(data: ActivePlanVO) {
        val dialog = ChangePlanDialog.newInstance(this,mActivity,data)
        dialog.show(childFragmentManager,"changePlan")
    }

    override fun onTapPlanConfirm() {
        showProgress()
        mViewModel.getChangePlan(mRequest)
    }

    override fun onCreateRequest(request: ChangePlanRequest,desc : String) {
        mRequest = request
        val dialog = TermAndConditionDialog.newInstance(this,desc)
        dialog.show(childFragmentManager,"T&C")
    }

    private fun viewLayoutVisible(b: Boolean) {
        binding.apply {
            if (b) {
                shimmerLoading.stopShimmer()
                lyActivePlan.visibility = View.VISIBLE
                shimmerLoading.visibility = View.GONE
            } else {
                shimmerLoading.startShimmer()
                lyActivePlan.visibility = View.GONE
                shimmerLoading.visibility = View.VISIBLE
            }
        }
    }

    fun onrefreshList(){
        viewLayoutVisible(false)
        pageNo = 1
        prePageNo = 2
        activePlanApiCall()
    }

    override fun setActivePlanData(response: ActivePlanListResponse) {
        viewLayoutVisible(true)
        dismissProgress()
        binding.swipeRefreshLayout.isRefreshing = false
        if(response.data != null){
            Log.e("ACTIVE_PLAN_LIST", Gson().toJson(response.data))
            if(response.data!!.size > 0){
//                if(prePageNo == 2) {
                    mList = response.data!!
//                    mList.addAll(response.data!!)
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

    override fun setChangePlan(response: SuccessResponse) {
        dismissProgress()
        mActivity.gotoPendingPlan()
    }

    override fun showVersionUpdate(message: String, storeUrl: String) {
        viewLayoutVisible(true)
        dismissProgress()
        mActivity.showVersionUpdateDialog(message, storeUrl)

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
            lyActivePlan.visibility = View.GONE
            lyError.layoutError.visibility = View.VISIBLE
            lyError.ivError.setImageResource(R.drawable.ic_component_service)
            lyError.tvError.text = resources.getString(R.string.error_subscribe)
        }
    }

    private fun hideLoadingNetwork() {
        binding.apply {
            shimmerLoading.stopShimmer()
            shimmerLoading.visibility = View.GONE
            lyActivePlan.visibility = View.GONE
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