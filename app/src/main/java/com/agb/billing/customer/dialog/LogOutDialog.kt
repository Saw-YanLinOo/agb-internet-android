package com.agb.billing.customer.dialog

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.agb.billing.customer.databinding.DialogLogoutBinding
import com.agb.billing.customer.delegate.LogOutDelegate

class LogOutDialog(delegate : LogOutDelegate) : DialogFragment(){

    private var _binding : DialogLogoutBinding ?= null
    private val binding get() = _binding!!
    lateinit var mView : View
    var mDelegate = delegate

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogLogoutBinding.inflate(inflater,container,false)
        mView = binding.root
//        isCancelable = false

        binding.apply {
            tvDialogCancel.setOnClickListener {
                dismiss()
            }
            tvDialogOk.setOnClickListener {
                mDelegate.onTapLogout()
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