package com.agb.customer.billing.dialog

import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.agb.customer.billing.databinding.DialogTermConditionBinding
import com.agb.customer.billing.delegate.ChangePlanDelegate

class TermAndConditionDialog(delegate : ChangePlanDelegate) : BaseDialogFragment() {

    var _binding : DialogTermConditionBinding ?= null
    val binding get() = _binding!!
    lateinit var mView : View

    companion object{
        lateinit var mDelegate : ChangePlanDelegate
        var tvDesc = ""
        fun newInstance(delegate : ChangePlanDelegate,desc : String) : TermAndConditionDialog{
            mDelegate = delegate
            tvDesc = desc
            return TermAndConditionDialog(mDelegate)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogTermConditionBinding.inflate(inflater,container,false)
        mView = binding.root
        isCancelable = false

        binding.apply {
//            tvTCDesc.text = tvDesc
            if (tvDesc.contains("<")) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    tvTCDesc.setText(Html.fromHtml(tvDesc, Html.FROM_HTML_MODE_COMPACT));
                } else {
                    tvTCDesc.setText(Html.fromHtml(tvDesc));
                }
            } else {
                tvTCDesc.text = tvDesc
            }

            btnAgree.setOnClickListener {
                mDelegate.onTapPlanConfirm()
                dismiss()
            }
            btnDisagree.setOnClickListener {
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