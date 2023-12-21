package com.agb.billing.customer.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.agb.billing.customer.R
import com.agb.billing.customer.databinding.DialogConfirmationBinding

class DialogUtil(var mContext: Context?) {

    private var _cbinding : DialogConfirmationBinding?= null
    private val cbinding get() = _cbinding!!

    fun newInstance(context: Context): DialogUtil {
        return DialogUtil(context)
    }

    fun showProgressDialog(): AlertDialog {

        val builder = AlertDialog.Builder(mContext!!, R.style.MyAlertDialogStyle)
        builder.setCancelable(false)
        builder.setView(R.layout.layout_loading_dialog)
        return builder.create()

    }

    fun showErrorDialog(title: String, message: String) {
        val builder = AlertDialog.Builder(mContext!!, R.style.MyAlertDialogStyle)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton(
            mContext!!.getString(R.string.str_ok)
        ) { dialogInterface, _ -> dialogInterface.dismiss() }
        val dialog = builder.create()
        dialog.window!!.attributes.windowAnimations = R.style.MyAlertDialogStyle
        dialog.show()
        dialog.setCancelable(true)

    }

//    fun showConfirmationDialog(title: String, message: String): Dialog {
////        val dialogView =
////            LayoutInflater.from(mContext).inflate(R.layout.dialog_confirmation, null)
//
//        _cbinding = DialogConfirmationBinding.inflate(LayoutInflater.from(mContext))
//        val dialog = android.app.AlertDialog.Builder(mContext).setView(cbinding.root).create()
//        cbinding.tvConfirmMessage.text = message
//        cbinding.tvConfirmTitle.text = title
////        try {
////            if (colorCode != "") {
////                dialogView.btn_ok.backgroundTintList =
////                    ColorStateList.valueOf(Color.parseColor(colorCode))
////                dialogView.btn_cancel.strokeColor =
////                    ColorStateList.valueOf(Color.parseColor(colorCode))
////                dialogView.btn_cancel.setTextColor(Color.parseColor(colorCode))
////            }
////        } catch (ex: Exception) {
////
////        }
//        if (dialog != null) {
//            dialog.window!!.setBackgroundDrawable(
//                ColorDrawable(
//                    ContextCompat.getColor(
//                        mContext!!,
//                        android.R.color.transparent
//                    )
//                )
//            )
//            dialog.window!!.setLayout(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT
//            )
//        }
//        dialog.setCancelable(false)
//        return dialog
//    }

}