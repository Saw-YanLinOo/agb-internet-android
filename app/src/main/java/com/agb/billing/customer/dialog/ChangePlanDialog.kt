package com.agb.billing.customer.dialog

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.agb.billing.customer.R
import com.agb.billing.customer.activities.MyPlanActivity
import com.agb.billing.customer.adapter.BandWidthSpinnerAdapter
import com.agb.billing.customer.adapter.PayPerSpinnerAdapter
import com.agb.billing.customer.databinding.DialogChangePlanBinding
import com.agb.billing.customer.delegate.ChangePlanDelegate
import com.agb.billing.customer.modelVO.ActivePlanVO
import com.agb.billing.customer.modelVO.PlanListVO
import com.agb.billing.customer.networks.requests.ChangePlanPreloadRequest
import com.agb.billing.customer.networks.requests.ChangePlanRequest
import com.agb.billing.customer.networks.requests.PlanByBandWidthRequest
import com.agb.billing.customer.networks.responses.ChangePlanPreloadResponse
import com.agb.billing.customer.networks.responses.PlanByBandWidthResponse
import com.agb.billing.customer.networks.responses.SuccessResponse
import com.agb.billing.customer.utils.Constants
import com.agb.billing.customer.utils.DialogUtil
import com.agb.billing.customer.viewmodels.ChangePlanPreloadViewModel
import com.agb.billing.customer.views.ChangePlanPreloadView

class ChangePlanDialog(delegate: ChangePlanDelegate) : BaseDialogFragment(), ChangePlanPreloadView {

    lateinit var mBandWidthAdapter: BandWidthSpinnerAdapter
    lateinit var mPayPerAdapter: PayPerSpinnerAdapter
    private var _binding: DialogChangePlanBinding? = null
    private val binding get() = _binding!!
    lateinit var mView: View
    private var mDelegate = delegate
    lateinit var mViewModel: ChangePlanPreloadViewModel
    var mPlanList: MutableList<PlanListVO>? = null
    var mBandWidth = ""
    var mPlanListVO: PlanListVO? = null
    var mDesc = ""

    companion object {
        lateinit var mActivity: MyPlanActivity
        var mPlanVO: ActivePlanVO? = null
        fun newInstance(
            delegate: ChangePlanDelegate,
            mContext: MyPlanActivity,
            mData: ActivePlanVO
        ): ChangePlanDialog {
            mActivity = mContext
            mPlanVO = mData
            return ChangePlanDialog(delegate)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogChangePlanBinding.inflate(inflater, container, false)
        mView = binding.root
        createAlertDialog(mView.context)
        binding.apply {
            tvDialogCancel.setOnClickListener {
                dismiss()
            }
            tvDialogChangePlan.setOnClickListener {
                mDelegate.onCreateRequest(createPlanRequest(), mDesc)
                dismiss()
            }
            lblDialogTitle.text = mPlanVO?.packagename
        }

        initViewModel()
        preloadApiCall()

        return mView
    }

    private fun initViewModel() {
        mViewModel = ViewModelProvider(this).get(ChangePlanPreloadViewModel::class.java)
        mViewModel.setView(this)
    }

    private fun initLayout() {

    }

    fun preloadApiCall() {
        val request = ChangePlanPreloadRequest()
        request.activePlanId = mPlanVO?.id
        mViewModel.getChangePlanPreload(request)
    }

    fun planByWidthApiCall() {
        val request = PlanByBandWidthRequest()
        request.activePlanId = mPlanVO?.id
        request.bandWidth = mBandWidth
        mViewModel.getPlanByBandWidth(request)
    }

    fun createPlanRequest(): ChangePlanRequest {
        val request = ChangePlanRequest()
        request.id = mPlanVO?.id
        request.newplanid = mPlanListVO?.id
        return request
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            dialog.window!!.setBackgroundDrawable(
                ColorDrawable(
                    ContextCompat.getColor(
                        requireContext(),
                        android.R.color.transparent
                    )
                )
            )
            dialog.window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun setChangePlanPreload(response: ChangePlanPreloadResponse) {
        dismissProgress()
        if (response.data != null) {
            mDesc = response.data!!.termsAndConditionsDesc.toString()

            if (response.data!!.bandWithList != null) {
                mBandWidthAdapter =
                    BandWidthSpinnerAdapter(mView.context, response.data!!.bandWithList!!)
                binding.apply {
                    spBandwidth.adapter = mBandWidthAdapter
                    spBandwidth.onItemSelectedListener =
                        object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                p0: AdapterView<*>?,
                                p1: View?,
                                p2: Int,
                                p3: Long
                            ) {
                                mBandWidth = mBandWidthAdapter.getBandWidthData(p2)
                                planByWidthApiCall()
                            }

                            override fun onNothingSelected(p0: AdapterView<*>?) {

                            }

                        }
                }

            }

            if (response.data!!.planList != null) {
                mPlanList = response.data!!.planList
            }
        }
    }

    override fun setPlanByBandWidth(response: PlanByBandWidthResponse) {
        dismissProgress()
        if (response.data != null) {
            if (response.data!!.planList != null) {
                mPayPerAdapter = PayPerSpinnerAdapter(mView.context, response.data!!.planList!!)
                binding.apply {
                    spPayPer.adapter = mPayPerAdapter
                    spPayPer.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            p0: AdapterView<*>?,
                            p1: View?,
                            p2: Int,
                            p3: Long
                        ) {
                            mPlanListVO = mPayPerAdapter.getPlanVO(p2)
                            tvAmount.text = mPlanListVO?.totalpriceDesc
                            lblDesc.text = mPlanListVO?.enddate
                        }

                        override fun onNothingSelected(p0: AdapterView<*>?) {

                        }

                    }
                }
            }
        }
    }

    override fun setChangePlan(response: SuccessResponse) {
        dismissProgress()
        mDelegate.onTapPlanConfirm()
        dismiss()
    }

    override fun showError(message: String, code: String) {
        dismissProgress()
        DialogUtil(mView.context).showErrorDialog(getString(R.string.errorTitle), message)
    }

    override fun showInvalidSession(message: String, code: String) {
        dismissProgress()
        mInvalidSession(mActivity = mActivity, message)
        dismiss()
    }

    override fun showNetworkFailed() {
        dismissProgress()
        DialogUtil(mView.context).showErrorDialog(
            getString(R.string.errorTitle),
            Constants.CONNECTION_FAIL
        )
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