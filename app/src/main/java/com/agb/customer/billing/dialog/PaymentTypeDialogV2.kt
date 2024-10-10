package com.agb.customer.billing.dialog


import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.agb.customer.billing.R
import com.agb.customer.billing.adapter.PaymentMethodAdapter
import com.agb.customer.billing.databinding.DialogPaymentTypeV2Binding
import com.agb.customer.billing.delegate.PaymentMethodItemDelegates
import com.agb.customer.billing.delegate.PaymentTypeDelegate
import com.agb.customer.billing.modelVO.PaymentVO
import com.agb.customer.billing.networks.requests.PaymentRequest
import com.agb.customer.billing.networks.responses.AYAPaymentResponse
import com.agb.customer.billing.networks.responses.PaymentMethodResponse
import com.agb.customer.billing.utils.Constants
import com.agb.customer.billing.utils.Constants.Companion.AYA_PAYMENT
import com.agb.customer.billing.utils.DialogUtil
import com.agb.customer.billing.utils.hideKeyboard
import com.agb.customer.billing.viewmodels.PaymentTypeViewModel
import com.agb.customer.billing.views.PaymentTypeView

class PaymentTypeDialogV2(delegate: PaymentTypeDelegate, var invoiceNo: String) :
    BaseDialogFragment(),
    PaymentMethodItemDelegates, PaymentTypeView {

    private var _binding: DialogPaymentTypeV2Binding? = null
    private val binding get() = _binding!!
    lateinit var mView: View
    val mDelegate = delegate
    private var paymentList = mutableListOf<PaymentVO>()
    private val mAdapter by lazy { PaymentMethodAdapter(this) }
    lateinit var mViewModel: PaymentTypeViewModel
    private var paymentVO: PaymentVO? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = DialogPaymentTypeV2Binding.inflate(inflater, container, false)
        mView = binding.root
        createAlertDialog(mView.context)
        initViewModel()


        binding.apply {
            etPhoneNo.setText("09")
            rvPayment.adapter = mAdapter

            btnPayNow.setOnClickListener {
                if (paymentVO != null) {
                    hideKeyboard()
                    if (paymentVO!!.id == 1) {
                        mDelegate.onTapPaymentType(paymentVO!!.code.toString(), paymentVO!!.id, "")
                        dismiss()
                    } else if (paymentVO!!.id == 2){
                        if (etPhoneNo.text.toString().isNotEmpty()) {

                            mDelegate.onTapPaymentType(paymentVO!!.code.toString(),
                                paymentVO!!.id,
                                etPhoneNo.text.toString())
                            dismiss()
                        } else
                            etPhoneNo.error = getString(R.string.require)
                    }
                    else{
                        mDelegate.onTapPaymentType(paymentVO!!.code.toString(), paymentVO!!.id, "")
                        dismiss()
                    }
                }


        }

    }

    return mView

}

private fun initViewModel() {
    mViewModel = ViewModelProvider(this).get(PaymentTypeViewModel::class.java)
    mViewModel.setView(this)

    preloadApiCall()
}

private fun preloadApiCall() {
    showProgress()
    mViewModel.getPaymentMethod()

}

override fun setPaymentTypeData(response: PaymentMethodResponse) {
    dismissProgress()
    response.paymentMethodList?.let { it ->
        paymentList = it
        mAdapter.setNewData(paymentList)
    }

}


override fun onTapPaymentItem(data: PaymentVO) {
    paymentVO = data
    paymentList.map {
        if (it.id != data.id)
            it.isChecked = false
    }

    if (data.id == 2) {
        binding.lyPhoneNumber.visibility = View.VISIBLE
    } else {
        binding.lyPhoneNumber.visibility = View.GONE
    }

    mAdapter.notifyDataSetChanged()

}


override fun showError(message: String, code: String) {
    dismissProgress()
    DialogUtil(mView.context).showErrorDialog(getString(R.string.errorTitle), message)
    dismiss()
}

override fun showInvalidSession(message: String, code: String) {
    dismissProgress()
    mInvalidSession(mActivity = ChangePlanDialog.mActivity, message)
    dismiss()
}

override fun showNetworkFailed() {
    dismissProgress()
    DialogUtil(mView.context).showErrorDialog(getString(R.string.errorTitle),
        Constants.CONNECTION_FAIL)
    dismiss()
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


override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
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


}