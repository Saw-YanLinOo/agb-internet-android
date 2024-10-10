package com.agb.customer.billing.activities

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.agb.customer.billing.R
import com.agb.customer.billing.activities.LoginActivity
import com.agb.customer.billing.modelVO.UserVO
import com.agb.customer.billing.utils.DialogUtil
import com.agb.customer.billing.utils.PreferenceUtils
import java.util.*

open class BaseActivity : AppCompatActivity() {

    var showProgressDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        hideStatusBar()
//        statusBarStyle()
        createAlertDialog()
    }

    private fun createAlertDialog() {
        showProgressDialog = DialogUtil(this).showProgressDialog()
    }

    fun hideStatusBar() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    private fun statusBarStyle() {
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorBlue_1x)
    }

    fun changeLocale(language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = getResources().getConfiguration();
        config.locale = locale
        getResources().updateConfiguration(
                config,
                getResources().getDisplayMetrics()
        )
    }

    fun mInvalidSession(mActivity: Activity, message: String) {

        try {
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
//                PreferenceUtils.setUser(UserVO())
                PreferenceUtils.setUser(UserVO())
                val intent = Intent(applicationContext, LoginActivity::class.java)
                startActivity(intent)
                finish()
                mActivity.finish()
            }, 1000)
        }
        catch(ex:Exception){

        }
    }

    fun showVersionUpdateDialog(message: String, storeUrl: String) {
        val builder = AlertDialog.Builder(this, R.style.MyAlertDialogStyle)
        builder.setTitle( getString(R.string.lbl_new_version))
        builder.setMessage(message)
        builder.setPositiveButton(
            getString(R.string.str_ok)
        ) { dialogInterface, _ ->
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(storeUrl)))
            finish()
            dialogInterface.dismiss()
        }
        val dialog = builder.create()
        dialog.window!!.attributes.windowAnimations = R.style.MyAlertDialogStyle
        dialog.show()
        dialog.setCancelable(false)

    }

}