package com.agb.billing.customer.dialog

import android.R
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.fragment.app.DialogFragment
import com.agb.billing.customer.databinding.DialogAyaPaymentSuccessBinding
import com.agb.billing.customer.delegate.AYAPaymentInfoDelegate

class AYAPaymentSuccessAndErrorDialog (val mDelegate: AYAPaymentInfoDelegate, val title:String, val desc:String, val code:String): DialogFragment() {

    private var _binding : DialogAyaPaymentSuccessBinding ?= null
    private val binding get() =  _binding!!
    lateinit var mView : View


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogAyaPaymentSuccessBinding.inflate(inflater,container,false)
        mView = binding.root
        isCancelable = false

        binding.apply {
            tvTitle.text = title
            tvDesc.text = HtmlCompat.fromHtml(desc, 0)
            btnOk.setOnClickListener {
                mDelegate.onTapOk(code)
                dismiss()
            }
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