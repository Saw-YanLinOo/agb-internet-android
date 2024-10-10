package com.agb.customer.billing.dialog

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.agb.customer.billing.databinding.DialogPaymentBoxBinding

class PaymentIDBoxDialog() : DialogFragment() {

    private var _binding : DialogPaymentBoxBinding?= null
    private val binding get() = _binding!!
    lateinit var mView : View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogPaymentBoxBinding.inflate(inflater,container,false)
        mView = binding.root

        binding.apply {
            btnPay.setOnClickListener { 
                if(etPaymentId.text.toString().isNotEmpty()){
                    gotoWebView(etPaymentId.text.toString())
                }
            }
        }

        return mView

    }

    private fun gotoWebView(id: String) {
        var url = "http://121.54.167.251/gateway/kpay/checkout?paymentid=$id"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
        //startActivity(WebViewActivity.newIntent(mView.context,"http://121.54.167.251/gateway/kpay/checkout?paymentid=e83db3dd-5d61-4dd2-93c4-7ac1b02031e6"))
        dismiss()
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
}