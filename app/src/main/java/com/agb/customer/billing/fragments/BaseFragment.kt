package com.agb.customer.billing.fragments

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.agb.customer.billing.R
import com.agb.customer.billing.activities.LoginActivity

open class BaseFragment : Fragment() {

    var showProgressDialog: AlertDialog? = null

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        createAlertDialog(view.context)
//    }

    override fun onStart() {
        super.onStart()
//        createAlertDialog(requireContext())
    }

    fun createAlertDialog(mContext : Context) {
//        showProgressDialog = DialogUtil(mContext).showProgressDialog()
    }

    fun mInvalidSession(mActivity: Activity, message: String) {

        val dialog = Dialog(mActivity)
        dialog.setContentView(R.layout.dialog_invalid_session)
        if (dialog.window != null) {
            dialog.window!!.setLayout(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            )
            dialog.window!!.setBackgroundDrawable(
                    ColorDrawable(
                            ContextCompat.getColor(
                                    mActivity.applicationContext,
                                    android.R.color.transparent
                            )
                    )
            )
        }
        dialog.findViewById<TextView>(R.id.tvMessage).text = message
        dialog.setCancelable(false)
        dialog.show()
        Handler().postDelayed({
            dialog.dismiss()
//            PreferenceUtils.setUser(UserVO())
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
            mActivity.finish()
        }, 1000)

    }

}