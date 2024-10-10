package com.agb.customer.billing.dialog

import android.R
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.agb.customer.billing.databinding.DialogPaymentTypeBinding
import com.agb.customer.billing.delegate.PaymentTypeDelegate
import com.agb.customer.billing.utils.Constants

class PaymentTypeDialog(delegate : PaymentTypeDelegate) : DialogFragment() {

    private var _binding : DialogPaymentTypeBinding ?= null
    private val binding get() = _binding!!
    lateinit var mView : View
    val mDelegate = delegate

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogPaymentTypeBinding.inflate(inflater,container,false)
        mView = binding.root

        binding.apply {
            lyPayment1.setOnClickListener {
               // mDelegate.onTapPaymentType(Constants.KBZ_PAY)
                dismiss()
            }
            lyPayment2.setOnClickListener {
               // mDelegate.onTapPaymentType(Constants.AYA_PAY)
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