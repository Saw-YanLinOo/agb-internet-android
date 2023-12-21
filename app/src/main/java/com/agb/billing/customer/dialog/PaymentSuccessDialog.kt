package com.agb.billing.customer.dialog

import android.R
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.agb.billing.customer.databinding.DialogPaymentInfoBinding

class PaymentSuccessDialog : DialogFragment() {

    private var _binding : DialogPaymentInfoBinding ?= null
    private val binding get() =  _binding!!
    lateinit var mView : View

    companion object{
        var title : String = ""
        var desc : String = ""
        fun newInstance(mTitle : String,mBody : String) : PaymentSuccessDialog{
            title = mTitle
            desc = mBody
            return PaymentSuccessDialog()
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
        binding.apply {
            tvPaymentInfo.text = title
            tvPaymentInfoDesc.text = desc
        }

        binding.tvOkay.setOnClickListener {
            dismiss()
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
                        R.color.transparent
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