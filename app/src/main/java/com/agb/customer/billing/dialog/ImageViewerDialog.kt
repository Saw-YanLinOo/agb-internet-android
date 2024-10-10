package com.agb.customer.billing.dialog

import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.agb.customer.billing.R
import com.agb.customer.billing.databinding.DialogImageViewerBinding
import com.agb.customer.billing.modelVO.ComplainVO
import com.bumptech.glide.Glide

class ImageViewerDialog(val uri: Uri, private val complainVO: ComplainVO?) : DialogFragment() {

    private var _binding: DialogImageViewerBinding? = null
    private val binding get() = _binding!!
    lateinit var mView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogImageViewerBinding.inflate(inflater, container, false)
        mView = binding.root

        if (complainVO == null) {
            Glide.with(binding.root)
                .load(uri)
                .into(binding.image)
        } else {
            Glide.with(binding.root)
                .load(complainVO.complainPhoto)
                .into(binding.image)
        }
        binding.apply {
            ivCancelPhoto.setOnClickListener {
                dismiss()
            }
        }
//        val loupe = Loupe.create(binding.image, binding.container) { // imageView is your ImageView
//            onViewTranslateListener = object : Loupe.OnViewTranslateListener {
//
//                override fun onStart(view: ImageView) {
//                    // called when the view starts moving
//                }
//
//                override fun onViewTranslate(view: ImageView, amount: Float) {
//                    // called whenever the view position changed
//                }
//
//                override fun onRestore(view: ImageView) {
//                    // called when the view drag gesture ended
//                }
//
//                override fun onDismiss(view: ImageView) {
//                    // called when the view drag gesture ended
////                    dismiss()
//                }
//            }
//        }
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
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}