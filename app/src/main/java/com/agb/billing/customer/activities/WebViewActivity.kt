package com.agb.billing.customer.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import android.widget.Toast
import com.agb.billing.customer.activities.BaseActivity
import com.agb.billing.customer.databinding.ActivityWebViewBinding

class WebViewActivity : BaseActivity() {
    
    lateinit var binding : ActivityWebViewBinding

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.ivBack.setOnClickListener {
            finish()
        }

        mProgressBar = binding.progressBar
        mProgressBar!!.max = 100
//        binding.webView.webChromeClient = MyWebViewChromeClient()
//        val strUrl = intent.getStringExtra("url")
//        Toast.makeText(this,strUrl,Toast.LENGTH_SHORT).show()
//        binding.webView.settings.javaScriptEnabled = true
//        binding.webView.loadUrl("http://121.54.167.251/gateway/kpay/checkout?paymentid=e83db3dd-5d61-4dd2-93c4-7ac1b02031e6")

        binding.apply {
            webViewOnlinePayment.settings.javaScriptEnabled = true
            webViewOnlinePayment.settings.domStorageEnabled = true
//            webViewOnlinePayment.settings.pluginState = WebSettings.PluginState.ON
//            webViewOnlinePayment.settings.useWideViewPort = true
//            webViewOnlinePayment.settings.loadWithOverviewMode = true
//            webViewOnlinePayment.settings.setSupportZoom(true)
//            webViewOnlinePayment.settings.setAppCacheEnabled(true)
//            webViewOnlinePayment.settings.databaseEnabled = true
//            webViewOnlinePayment.settings.builtInZoomControls = true
//            webViewOnlinePayment.settings.displayZoomControls = true
//            webViewOnlinePayment.settings.javaScriptCanOpenWindowsAutomatically = true
//            webViewOnlinePayment.settings.loadWithOverviewMode = true
//            webViewOnlinePayment.settings.loadsImagesAutomatically = true
//            webViewOnlinePayment.settings.allowFileAccessFromFileURLs = true
//            webViewOnlinePayment.settings.allowFileAccess = true
//            webViewOnlinePayment.settings.allowUniversalAccessFromFileURLs = true
//            webViewOnlinePayment.settings.allowContentAccess = true
//
//            webViewOnlinePayment.settings.defaultTextEncodingName = "utf-8"

            webViewOnlinePayment.webViewClient = OnlinePaymentWebViewClient()
            webViewOnlinePayment.webChromeClient = MyWebViewChromeClient()

//            webViewOnlinePayment.loadUrl(paymentMethodUrl)
        }

    }

    companion object {

        private var mProgressBar: ProgressBar? = null
        var paymentMethodUrl: String = ""

        fun newIntent(context: Context, url: String): Intent {
            paymentMethodUrl = url
            return Intent(context, WebViewActivity::class.java)
        }

        fun changeProgressValue(value: Int) {

            if (mProgressBar != null) {
                mProgressBar!!.progress = value
                if (mProgressBar!!.progress == 100) {
                    mProgressBar!!.visibility = View.GONE
                }
            }
        }


    }

    inner class MyWebViewChromeClient : WebChromeClient() {
        override fun onProgressChanged(view: WebView, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            Toast.makeText(this@WebViewActivity,newProgress.toString(),Toast.LENGTH_SHORT).show()
            changeProgressValue(newProgress)
        }
    }

    inner class OnlinePaymentWebViewClient : WebViewClient() {

        override fun shouldOverrideUrlLoading(
            view: WebView,
            url: String
        ): Boolean {
            val uri: Uri = Uri.parse(url)
            val responseCode = uri.getQueryParameter("responseCode")
            val responseMessage = uri.getQueryParameter("responseMessage")

            if (responseCode != null && responseMessage != null) {

//                val intent = PointPurchaseActivity.newIntentWithUpdatePoint(this@OnlinePaymentActivity)
//                startActivity(intent)
//                setResult(ACTIVITY_FINISH)
//                finish()

            } else {
                view.loadUrl(url)
            }
            return true
        }

    }


}
