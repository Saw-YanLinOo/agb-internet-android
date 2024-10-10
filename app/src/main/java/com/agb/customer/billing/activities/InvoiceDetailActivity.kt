package com.agb.customer.billing.activities

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.google.gson.Gson
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.agb.customer.billing.R
import com.agb.customer.billing.adapter.ChargesAdapter
import com.agb.customer.billing.databinding.ActivityInvoiceDetailBinding
import com.agb.customer.billing.delegate.AYAPaymentInfoDelegate
import com.agb.customer.billing.delegate.PaymentTypeDelegate
import com.agb.customer.billing.dialog.AYAPaymentSuccessAndErrorDialog
import com.agb.customer.billing.dialog.PaymentInformationDialog
import com.agb.customer.billing.dialog.PaymentSuccessDialog
import com.agb.customer.billing.dialog.PaymentTypeDialogV2
import com.agb.customer.billing.events.PaymentStatusEvent
import com.agb.customer.billing.kbzpayment.SHA
import com.agb.customer.billing.modelVO.InvoiceVO
import com.agb.customer.billing.modelVO.KBZPaymentVO
import com.agb.customer.billing.modelVO.PaymentNotificationVO
import com.agb.customer.billing.networks.requests.InvoiceDetailRequest
import com.agb.customer.billing.networks.requests.KBZPaymentRequest
import com.agb.customer.billing.networks.requests.PaymentRequest
import com.agb.customer.billing.networks.responses.AYAPaymentResponse
import com.agb.customer.billing.networks.responses.CBPaymentResponse
import com.agb.customer.billing.networks.responses.InvoiceDetailResponse
import com.agb.customer.billing.networks.responses.KBZPaymentResponse
import com.agb.customer.billing.utils.Constants
import com.agb.customer.billing.utils.PreferenceUtils
import com.agb.customer.billing.viewmodels.InvoiceDetailViewModel
import com.agb.customer.billing.views.InvoiceDetailView
import com.kbzbank.payment.KBZPay
import com.lzy.okgo.OkGo
import com.lzy.okgo.callback.StringCallback
import com.lzy.okgo.model.Response
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.ThreadMode

import org.greenrobot.eventbus.Subscribe

class InvoiceDetailActivity : BaseActivity(), PaymentTypeDelegate, InvoiceDetailView,AYAPaymentInfoDelegate {

    lateinit var binding: ActivityInvoiceDetailBinding
    lateinit var mAdapter: ChargesAdapter
    lateinit var mViewModel: com.agb.customer.billing.viewmodels.InvoiceDetailViewModel
    lateinit var mManager : DownloadManager
    var downLoadUrl = ""

    var paidStatus = 1//1 is paid ,2 is unpaid
    var mInvNumber = ""
    var apiCount = 0
    var dialogShow = false
    var paymentTC = ""
    var infoDialog : PaymentSuccessDialog ?= null
    var paymentDialog : PaymentSuccessDialog ?= null

    //KBZ Payment
    private val TAG: String? = "KBZ_PAYMENT"
    private val API_UAT: String? = "http://api.kbzpay.com/payment/gateway/uat/precreate"
    private val API_PROD = "http://api.kbzpay.com/payment/gateway/precreate"
    private var mOrderInfo: String? = null
    private var mMerchantCode =
        "200177" // Please don't save merchant code in app. This just a demo.

    private var mAppId =
        "kpd73bc3ce6b4945799c6b052410982e" // Please don't save appId in app. This just a demo.

    private var mSignKey = "agb123456789" // Please don't save sign key in app. This just a demo.
    private var notifyUrl = "http://121.54.167.251/gateway/kpay/notify"
    private var mAmount = "20"
    private var callBackInfo = "callbackinfo_test"

    private var mSign = ""
    private var mSignType = "SHA256"
    private val API_URL = API_UAT!!

    private var mPrepayId = ""
    private var mMerchantOrderId = ""

    companion object {
        var mInvoiceVO: InvoiceVO? = null
        fun newInstance(mContext: Context, invoiceVO: InvoiceVO): Intent {
            mInvoiceVO = invoiceVO
            return Intent(mContext, InvoiceDetailActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInvoiceDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        PreferenceUtils.setNotiData(PaymentNotificationVO())
        initLayout()
        initViewModel()
        clickEvent()
        merchantOrderGenerate()
//        viewLayoutVisible(false)
//        detailApiCall()
    }

    private fun detailApiCall() {
        apiCount += 1
        val request = InvoiceDetailRequest()
        request.invnumber = mInvoiceVO?.invnumber
        request.invType = Constants.INVOICE
        mViewModel.getInvoiceDetail(request)
        Log.e("REQUEST", Gson().toJson(request))
    }

    private fun initViewModel() {
        mViewModel = ViewModelProvider(this).get(com.agb.customer.billing.viewmodels.InvoiceDetailViewModel::class.java)
        mViewModel.setView(this)
    }

    private fun clickEvent() {
        binding.apply {
            ivBack.setOnClickListener { onBackPressed() }

            lblPaymentInfo.setOnClickListener {
                val dialog = PaymentInformationDialog.newInstance(paymentTC)
                dialog.show(supportFragmentManager, "paymentInfo")
            }

            btnPayNow.setOnClickListener {
                val dialog = PaymentTypeDialogV2(this@InvoiceDetailActivity,mInvoiceVO?.invnumber.toString())
                dialog.show(supportFragmentManager, "paymentType")
            }

            btnReceipt.setOnClickListener {
                startActivity(
                    ReceiptDetailActivity.newInstance(
                        this@InvoiceDetailActivity,
                        mInvNumber
                    )
                )
                overridePendingTransition(R.anim.left_in, R.anim.left_out)
            }

            btnDownload.setOnClickListener {
                if(downLoadUrl.isNotEmpty()) {
                    downLoadFunctionwithFile(downLoadUrl)
                }else{
                    Toast.makeText(this@InvoiceDetailActivity,"Invoice Not Found!",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun initLayout() {

    }


    private fun paymentApiCall(paymentMethod: String) {
        showProgress()
        val request = KBZPaymentRequest()
        request.invnumber = mInvoiceVO?.invnumber.toString()
        request.paymentMethod = paymentMethod
        mViewModel.getKBZPayment(request)
    }

    private fun viewLayoutVisible(b: Boolean) {
        binding.apply {
            if (b) {
                shimmerEffect.stopShimmer()
                layoutMain.visibility = View.VISIBLE
                shimmerEffect.visibility = View.GONE
            } else {
                shimmerEffect.startShimmer()
                layoutMain.visibility = View.GONE
                shimmerEffect.visibility = View.VISIBLE
            }
        }
    }

    override fun setKBZPaymentData(response: KBZPaymentResponse) {
        dismissProgress()
        if(response.data != null){
            dialogShow = true
            buildOrderInfoWithApi(response.data!!)
            startPay()
        }
    }

    override fun setInvoiceDetail(response: InvoiceDetailResponse) {
        viewLayoutVisible(true)
        dismissProgress()
        if (response.data != null) {
            downLoadUrl = response.data?.pdfUrl.toString()
            mAdapter = ChargesAdapter(response.data?.startdate!!, response.data?.enddate!!)
            binding.apply {
                rvCharges.layoutManager = LinearLayoutManager(this@InvoiceDetailActivity)
                rvCharges.setHasFixedSize(true)
                rvCharges.adapter = mAdapter
            }

            Log.e("DESCRIPTION_LIST", Gson().toJson(response.data!!.description))

            if (response.data!!.description != null) {
                mAdapter.setNewData(response.data!!.description!!)
            }
            val mData = response.data
            paymentTC = mData?.termsAndConditionsDesc.toString()
            Log.e("BODY",paymentTC)
            mInvNumber = mData?.invnumber.toString()
            binding.apply {
                tvServiceId.text = mData?.specialcode
                tvInvoiceId.text = mData?.invnumber
                tvDueDate.text = mData?.duedate
                tvTotal.text = mData?.totalcost

                if (mData!!.paidDesc == "UNPAID") {
                    paidStatus = 2
                    ivPaid.setImageResource(R.drawable.unpaid)
                } else {
                    paidStatus = 1
                    ivPaid.setImageResource(R.drawable.paid)
                }

                if (paidStatus == 2) {
                  //  btnPayNow.isEnabled = mData?.totalcost!!.contains("MMK")
                    val pay= mData.enabledPay == 1 //0 => disable, 1 => enable
                    btnPayNow.isEnabled = pay
                    btnPayNow.visibility = View.VISIBLE
                    btnReceipt.visibility = View.GONE
                } else {
                    btnPayNow.visibility = View.GONE
                    btnReceipt.visibility = View.VISIBLE
                }
            }


            if(dialogShow) {
                dialogShow = false
                var notiData = PreferenceUtils.getNotiData()
                if (notiData.notificationType.toString().isNotEmpty() && notiData.notificationType.toString() == "2") {
                    if(paymentDialog == null) {
                        paymentDialog = PaymentSuccessDialog.newInstance(
                            notiData.title.toString(),
                            notiData.message.toString()
                        )
                        paymentDialog!!.show(supportFragmentManager, "payment")
                        PreferenceUtils.setNotiData(PaymentNotificationVO())
                    }
                } else {
                    if(paidStatus == 1) {
                        infoDialog = PaymentSuccessDialog.newInstance(
                            getString(R.string.str_info),
                            getString(R.string.payment_general_desc)
                        )
                        infoDialog!!.show(supportFragmentManager, "payment")
                    }
                }
//                infoDialog = PaymentSuccessDialog.newInstance("Info", getString(R.string.payment_general_desc))
//                infoDialog!!.show(supportFragmentManager, "payment")
            }
        }
    }


    override fun showError(message: String, code: String) {

        dismissProgress()
        if (code==Constants.FIELD_ERROR_CODE){
            showAyaPayErrorScreen()

            val paymentInfoDialog = AYAPaymentSuccessAndErrorDialog(
                this,
                getString(R.string.title_payment_info),
                message,
                code=Constants.FIELD_ERROR_CODE

            )
            paymentInfoDialog.show(supportFragmentManager, "paymentInfo")
        }
        else {
            hideLoadingError()
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun showInvalidSession(message: String, code: String) {
        dismissProgress()
        mInvalidSession(this, message)
    }

    override fun showNetworkFailed() {
        dismissProgress()
        Toast.makeText(this,"ShowNetWork",Toast.LENGTH_SHORT).show()
//        DialogUtil(this).showErrorDialog(getString(R.string.errorTitle), Constants.CONNECTION_FAIL)
        hideLoadingNetwork()
    }

    override fun showVersionUpdate(message: String, storeUrl: String) {
        showVersionUpdateDialog(message,storeUrl)
        viewLayoutVisible(true)
        dismissProgress()

    }

    private fun hideLoadingError() {
        binding.apply {
            shimmerEffect.stopShimmer()
            shimmerEffect.visibility = View.GONE
            lyError.layoutError.visibility = View.VISIBLE
            lyError.ivError.setImageResource(R.drawable.ic_component_wifi)
            lyError.tvError.text = resources.getString(R.string.error_wifi)
        }
    }
    private fun showAyaPayErrorScreen() {
        binding.apply {
            shimmerEffect.stopShimmer()
            shimmerEffect.visibility = View.GONE
        }
    }


    private fun hideLoadingNetwork() {
        binding.apply {
            shimmerEffect.stopShimmer()
            shimmerEffect.visibility = View.GONE
            lyError.layoutError.visibility = View.VISIBLE
        }
    }

    private fun showProgress() {
        try {
            showProgressDialog?.show()
        } catch (ex: Exception) {

        }

    }

    private fun dismissProgress() {
        try {
            showProgressDialog?.dismiss()
        } catch (ex: Exception) {

        }
    }

    override fun onTapPaymentType(paymentMethod: String, paymentTypeId: Int, phoneNo: String) {
        when (paymentTypeId) {
            1 -> {
//                createOrder()
                paymentApiCall(paymentMethod)
            }
            2 -> {
                ayaApiCall(paymentMethod,phoneNo)
                //testing kbz PWA payment
//                val dialog = PaymentIDBoxDialog()
//                dialog.show(supportFragmentManager, "payment")
            }
            3 -> {

                cbApiCall(paymentMethod)
//                mInvoiceVO?.cbPayQRUrl="https://uat6.advent-soft.com/agb_billing_images/cbpay_qr/202208000104_1687338398389.png"
//                startActivity(CBPayInformationActivity.newInstance(this, mInvoiceVO, isMainScreen = false))
//                overridePendingTransition(R.anim.left_in, R.anim.left_out)
//                finish()
            }
        }
    }

    private fun ayaApiCall(paymentMethod:String,phoneNo:String) {
        showProgress()
        val request = PaymentRequest()
        request.invnumber = mInvoiceVO?.invnumber.toString()
        request.paymentMethod = paymentMethod
        request.phoneNo = phoneNo
        mViewModel.ayaPayment(request)

    }

    override fun setAYAPaymentData(response: AYAPaymentResponse) {
        dismissProgress()
        if (response.data!=null){
            val paymentVO=response.data
            val paymentInfoDialog = AYAPaymentSuccessAndErrorDialog(this,
                paymentVO?.title.toString(),
                paymentVO?.desc.toString(),
                code=Constants.API_SUCCESS_CODE
            )
            paymentInfoDialog.show(supportFragmentManager, "paymentInfo")

        }

    }

    private fun cbApiCall(paymentMethod:String) {
        showProgress()
        val request = PaymentRequest()
        request.invnumber = mInvoiceVO?.invnumber.toString()
        request.paymentMethod = paymentMethod
        mViewModel.cbPayment(request)

    }
    override fun setCBPaymentData(response: CBPaymentResponse) {
        dismissProgress()
        if (response.data!=null){
            val paymentVO=response.data
            mInvoiceVO?.cbPayQRUrl=paymentVO!!.cbPayQRCode
            mInvoiceVO?.referenceNo=paymentVO!!.referenceNo
            startActivity(CBPayInformationActivity.newInstance(this, mInvoiceVO, isMainScreen = false))
            overridePendingTransition(R.anim.left_in, R.anim.left_out)
            finish()
        }
    }


    //KBZ Payment
    fun merchantOrderGenerate() {
        val d = Date()
        val format = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        val today = format.format(d) + ("" + d.time / 1000).toInt()
        mMerchantOrderId = today
    }

    private fun createOrder() {
        var json = ""
        try {
            // Order increase
            try {
                var orderid = mMerchantOrderId.toLong()
                orderid++
                mMerchantOrderId = "" + orderid
            } catch (ex: Exception) {
                Log.d(TAG, ex.toString())
            }
            val nonceStr: String = createRandomStr()
            val timestamp: String = createTimestamp()
            val method = "kbz.payment.precreate"
            val notifyUrl: String = notifyUrl
            val jsonObject = JSONObject()
            val jsonRequest = JSONObject()
            jsonObject.put("Request", jsonRequest)
            jsonRequest.put("timestamp", timestamp)
            jsonRequest.put("method", method)
            jsonRequest.put("notify_url", notifyUrl)
            jsonRequest.put("nonce_str", nonceStr)
            jsonRequest.put("sign_type", "SHA256")
            jsonRequest.put("sign", createOrderSign(method, nonceStr, notifyUrl, timestamp))
            jsonRequest.put("version", "1.0")
            val jsonContent = JSONObject()
            jsonRequest.put("biz_content", jsonContent)
            jsonContent.put("merch_order_id", mMerchantOrderId)
            jsonContent.put("merch_code", mMerchantCode)
            jsonContent.put("appid", mAppId)
            jsonContent.put("trade_type", "APP")
            jsonContent.put("title", "iPhoneX")
            jsonContent.put("total_amount", mAmount)
            jsonContent.put("trans_currency", "MMK")
            jsonContent.put("timeout_express", "100m")
            jsonContent.put("callback_info", callBackInfo)
            json = jsonObject.toString()
        } catch (jex: JSONException) {
            showNotice("json params error：$jex")
            return
        }
        Log.d(TAG, json)
        OkGo.post<String>(API_URL).tag(this).upJson(json).execute(object : StringCallback() {
            override fun onSuccess(response: Response<String>) {
                try {
                    val body = response.body()
                    Log.d(TAG, body)
                    val `object` = JSONObject(body)
                    val jsonResponse = `object`.getJSONObject("Response")
                    val code = jsonResponse.getString("code")
                    if (code == "0") {
                        mPrepayId = jsonResponse.getString("prepay_id")
//                        etPrepayId.setText(mPrepayId)
//                        etMerchantOrderId.setText(mMerchantOrderId)
                        showNotice("Order Success")
                        startPayTest()

                    } else {
                        val msg = jsonResponse.getString("msg")
                        showNotice(msg)
                    }
                } catch (jex: JSONException) {
                    showNotice(jex.toString())
                }
            }

            override fun onError(response: Response<String>) {
                super.onError(response)
                showNotice(response.body())
            }
        })
    }

    private fun createOrderSign(
        method: String,
        nonceStr: String,
        notifyUrl: String,
        timestamp: String
    ): String? {
        val str = "appid=" + mAppId +
                "&callback_info=" + callBackInfo +
                "&merch_code=" + mMerchantCode +
                "&merch_order_id=" + mMerchantOrderId +
                "&method=" + method +
                "&nonce_str=" + nonceStr +
                "&notify_url=" + notifyUrl +
                "&timeout_express=100m" +
                "&timestamp=" + timestamp +
                "&title=iPhoneX" +
                "&total_amount=" + "20" +
                "&trade_type=APP" +
                "&trans_currency=MMK" +
                "&version=1.0"
        val s = "$str&key=$mSignKey"
        Log.d(TAG, "sign string = $s")
        return SHA.getSHA256Str(s)
    }

    private fun createRandomStr(): String {
        val random = Random()
        return java.lang.Long.toString(Math.abs(random.nextLong()))
    }

    private fun createTimestamp(): String {
        val cal = Calendar.getInstance()
        val time = (cal.timeInMillis / 1000).toDouble()
        val d = java.lang.Double.valueOf(time)
        return Integer.toString(d.toInt())
    }

    private fun buildOrderInfo() {
        // prepayId由服务器下单得到
        val nonceStr = createRandomStr()
        val timestamp = createTimestamp()
        mOrderInfo = "appid=" + mAppId +
                "&merch_code=" + mMerchantCode +
                "&nonce_str=" + nonceStr +
                "&prepay_id=" + mPrepayId +
                "&timestamp=" + timestamp
        mSign = SHA.getSHA256Str("$mOrderInfo&key=$mSignKey")!!
    }
    private fun buildOrderInfoWithApi(data : KBZPaymentVO) {
        // prepayId由服务器下单得到
        val nonceStr = createRandomStr()
        val timestamp = createTimestamp()
        mSignType = data.sign_type.toString()
        mOrderInfo = "appid=" + data.appid +
                "&merch_code=" + data.merch_code +
                "&nonce_str=" + data.nonce_str +
                "&prepay_id=" + data.prepay_id +
                "&timestamp=" + data.timestamp
        mSign = SHA.getSHA256Str("$mOrderInfo&key=${data.signKey}")!!
    }

    private fun startPay() {
        try {
//            buildOrderInfo()
            KBZPay.startPay(this, mOrderInfo, mSign, mSignType)
        } catch (ex: Exception) {

        }
    }

    private fun startPayTest() {
        try {
            buildOrderInfo()
            KBZPay.startPay(this, mOrderInfo, mSign, mSignType)
        } catch (ex: Exception) {
            Log.e("KBZ_ERROR",ex.toString())
        }
    }

    private fun showNotice(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        viewLayoutVisible(false)
        detailApiCall()
    }

    private fun checkSuccessData() {
        val intent = intent
        val result = intent.getIntExtra(KBZPay.EXTRA_RESULT, 0)
        if (result == KBZPay.COMPLETED) {
            showNotice("pay success!........")
        } else {
            val failMsg = intent.getStringExtra(KBZPay.EXTRA_FAIL_MSG)
            showNotice("pay fail, fail reason = $failMsg")
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.right_in, R.anim.right_out)
        finish()
    }

    fun downLoadFunction(url : String){
        Toast.makeText(this,"Start downloading...",Toast.LENGTH_SHORT).show()
        mManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        val uri: Uri =
            Uri.parse("https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf")
        val request = DownloadManager.Request(uri)
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        val reference: Long = mManager.enqueue(request)
    }

    fun downLoadFunctionwithFile(url : String){
        Toast.makeText(this,"Start downloading...",Toast.LENGTH_SHORT).show()

        mManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
//        val uri: Uri =
//            Uri.parse("https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf")

        val uri: Uri =
            Uri.parse(url)
        val request = DownloadManager.Request(uri)
            request.setAllowedNetworkTypes(
                DownloadManager.Request.NETWORK_WIFI or
                        DownloadManager.Request.NETWORK_MOBILE
            )
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        //file save
//        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "ABC")
        val reference: Long = mManager.enqueue(request)
    }

    override fun onStart() {
        super.onStart()
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    fun onMessageEvent(event: com.agb.customer.billing.events.PaymentStatusEvent?) {
        // Do something
        Log.e("SAMPLE_NOTI","Got it")
        if(event != null){
            dialogShow = false
            if(event.data.message.toString().isNotEmpty() && event.data.title.toString().isNotEmpty()){
                if(infoDialog != null){
                    infoDialog!!.dismiss()
                }
                if(paymentDialog == null) {
                    val mTitle = event.data.title.toString()
                    val mBody = event.data.message.toString()
                    paymentDialog = PaymentSuccessDialog.newInstance(mTitle, mBody)
                    paymentDialog!!.show(supportFragmentManager, "payment")
                    PreferenceUtils.setNotiData(PaymentNotificationVO())
                }
            }
        }
    }

    override fun onTapOk(code: String) {
        if (code==Constants.API_SUCCESS_CODE) {
            onBackPressed()
        }
    }



}