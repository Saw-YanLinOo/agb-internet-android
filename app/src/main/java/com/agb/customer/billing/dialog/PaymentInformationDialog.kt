package com.agb.customer.billing.dialog

import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.agb.customer.billing.databinding.DialogPaymentInfoBinding

class PaymentInformationDialog : DialogFragment() {

    private var _binding : DialogPaymentInfoBinding ?= null
    private val binding get() =  _binding!!
    lateinit var mView : View

    companion object{
        var bodyString = ""
        fun newInstance(body : String) : PaymentInformationDialog{
            bodyString = body
            return PaymentInformationDialog()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogPaymentInfoBinding.inflate(inflater,container,false)
        mView = binding.root
        isCancelable = false

        binding.tvOkay.setOnClickListener {
            dismiss()
        }
        Log.e("BODY", bodyString)

        if (bodyString.contains("<")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                binding.tvPaymentInfoDesc.setText(Html.fromHtml(bodyString, Html.FROM_HTML_MODE_COMPACT));
            } else {
                binding.tvPaymentInfoDesc.setText(Html.fromHtml(bodyString));
            }
        } else {
            binding.tvPaymentInfoDesc.text = bodyString
        }
        return mView
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