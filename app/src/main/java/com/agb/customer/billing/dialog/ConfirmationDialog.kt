package com.agb.customer.billing.dialog

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.agb.customer.billing.databinding.DialogConfirmationBinding
import com.agb.customer.billing.delegate.ConfirmationDelegate

class ConfirmationDialog(delegate : ConfirmationDelegate) : DialogFragment() {

    private var _binding : DialogConfirmationBinding ?= null
    private val binding get() = _binding!!
    lateinit var mView : View
    private var mDelegate = delegate

    companion object{
        var mTitle = ""
        var mDesc = ""
        fun newInstance(delegate : ConfirmationDelegate,title : String = "",desc : String = "") : ConfirmationDialog{
            mTitle = title
            mDesc = desc
            return ConfirmationDialog(delegate)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogConfirmationBinding.inflate(inflater,container,false)
        mView = binding.root
        isCancelable = false

        binding.apply {
            btnCancel.setOnClickListener {
                dismiss()
            }

            btnOk.setOnClickListener {
                mDelegate.onTapConfirm()
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