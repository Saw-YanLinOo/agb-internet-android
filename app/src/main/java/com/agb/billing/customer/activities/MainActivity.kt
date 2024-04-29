package com.agb.billing.customer.activities

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.agb.billing.customer.R
import com.agb.billing.customer.adapter.BannerVpAdapter
import com.agb.billing.customer.adapter.CategoryAdapter
import com.agb.billing.customer.databinding.ActivityMainBinding
import com.agb.billing.customer.delegate.*
import com.agb.billing.customer.dialog.AYAPaymentSuccessAndErrorDialog
import com.agb.billing.customer.dialog.LogOutDialog
import com.agb.billing.customer.dialog.PaymentSuccessDialog
import com.agb.billing.customer.dialog.PaymentTypeDialogV2
import com.agb.billing.customer.events.PaymentStatusEvent
import com.agb.billing.customer.kbzpayment.SHA
import com.agb.billing.customer.localizations.LocaleManager
import com.agb.billing.customer.modelVO.*
import com.agb.billing.customer.networks.EndPoints.BASE_TICKET_URL
import com.agb.billing.customer.networks.requests.InvoiceListRequest
import com.agb.billing.customer.networks.requests.KBZPaymentRequest
import com.agb.billing.customer.networks.requests.PaymentRequest
import com.agb.billing.customer.networks.responses.*
import com.agb.billing.customer.utils.Constants
import com.agb.billing.customer.utils.Constants.Companion.CURRENT_PLAN
import com.agb.billing.customer.utils.PreferenceUtils
import com.agb.billing.customer.utils.SampleData
import com.agb.billing.customer.viewmodels.HomeViewModel
import com.agb.billing.customer.views.HomeView
import com.google.gson.Gson
import com.kbzbank.payment.KBZPay
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MainActivity : BaseActivity(), BannerDelegate,
    CategoryDelegate, LogOutDelegate, HomeView, PaymentTypeDelegate, AYAPaymentInfoDelegate {

    lateinit var binding: ActivityMainBinding
    private var handler = Handler()
    private var delay = 3000L
    private var page = 0
    private var dialogShow = false
    private var paidStatus = 1//1 is paid ,2 is unpaid
    private var searchInvoiceNo = ""
    private var infoDialog: PaymentSuccessDialog? = null
    private var paymentDialog: PaymentSuccessDialog? = null

    lateinit var bannerAdapter: BannerVpAdapter
    private lateinit var mCategoryAdapter: CategoryAdapter
    lateinit var mViewModel: HomeViewModel
    var mList = mutableListOf<InvoiceVO>()
    var invoiceList = arrayListOf<String>()
    var pageNo = 1
    var mInvoiceVO: InvoiceVO? = null
    private var arrayAdapter: ArrayAdapter<String>? = null

    private var mSign = ""
    private var mSignType = ""
    private var mOrderInfo = ""
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    companion object {
        fun newInstance(mContext: Context): Intent {
            val intent = Intent(mContext, MainActivity::class.java)
            intent.flags =
                FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK
            return intent
        }
    }

    private var timerun = object : Runnable {
        override fun run() {
            if (bannerAdapter.count == page) {
                page = 0
            } else {
                page++
            }
            binding.vpBanner.setCurrentItem(page, true)
            handler.postDelayed(this, delay)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
//        LocaleManager(this).setLocale(this)
        setContentView(binding.root)

        initLayout()
        initViewModel()
        clickEvent()
        checkUser()
//        Handler().postDelayed(object : Runnable{
//            override fun run() {
//                viewLayoutVisible(true)
//            }
//
//        },2000)
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S_V2) {
            requestPermission()
        }
    }

    private fun requestPermission() {
        requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                if (it) {
                    Toast.makeText(this, "notification permission is allowed", Toast.LENGTH_SHORT)
                        .show()
                } else {

                    Toast.makeText(
                        this,
                        "Please grant Notification permission from App Settings",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS,
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // Toast.makeText(this, "notification permission granted",Toast.LENGTH_SHORT).show()
        } else {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    private fun checkUser() {
        val dataLogin = PreferenceUtils.getUser()
        if (dataLogin.sessionId.toString().isEmpty()) {
            gotoLoginActivity()
        } else {
            if (intent.hasExtra(Constants.FROM_NOTI)) {
                Log.e("NOTIFICATION", Gson().toJson(PreferenceUtils.getNotiData()))
                var notiDataVO = PreferenceUtils.getNotiData()
                // val notiType = intent.getStringExtra(Constants.NOTI_TYPE)
                // val notiData = intent.getStringExtra(Constants.NOTI_DATA)
                // Log.e("NOTIFICATION_INV", notiData.toString())
                when (notiDataVO.notificationType) {
                    Constants.NOTI_TYPE_1 -> {
                        if (notiDataVO.toString().isNotEmpty()) {
                            startActivity(InvoiceActivity.newInstance(this))
                            overridePendingTransition(R.anim.left_in, R.anim.left_out)
                            PreferenceUtils.setNotiData(PaymentNotificationVO())

                        }
                    }

                    Constants.NOTI_TYPE_2 -> {
                        if (notiDataVO.toString().isNotEmpty()) {
                            gotoInvoiceDetailScreen(notiDataVO.invNumber.toString())

                        }
                    }

                    Constants.NOTI_TYPE_3 -> {
                        if (notiDataVO.toString().isNotEmpty()) {
                            CURRENT_PLAN = 1
                            startActivity(MyPlanActivity.newInstance(this))
                            overridePendingTransition(R.anim.left_in, R.anim.left_out)
                            PreferenceUtils.setNotiData(PaymentNotificationVO())

                        }
                    }
                }
            }
        }
    }

    private fun trimInvNumber(invnumber: String): String {
        return invnumber.split("( ")[1].toString().split(" )")[0].toString()
    }

    private fun gotoInvoiceDetailScreen(invNumber: String) {
        Log.e("INVNUMBER", invNumber)
        val data = InvoiceVO()
        data.invnumber = invNumber
        startActivity(InvoiceDetailActivity.newInstance(this, data))
        overridePendingTransition(R.anim.left_in, R.anim.left_out)
        PreferenceUtils.setNotiData(PaymentNotificationVO())
    }

    private fun invoiceListApiCall() {
        val request = InvoiceListRequest()
        request.pageNo = pageNo
        mViewModel.getInvoiceList(request)
    }

    private fun initViewModel() {
        mViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        mViewModel.setView(this)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun clickEvent() {
        binding.apply {
            btnPay.setOnClickListener {
                //  hideKeyboard()
                if (etInvoiceNo.text.toString().isNotEmpty()) {
                    if (mInvoiceVO != null) {
                        searchInvoiceNo = mInvoiceVO?.invnumber.toString()
                        val dialog =
                            PaymentTypeDialogV2(this@MainActivity, mInvoiceVO?.invnumber.toString())
                        dialog.show(supportFragmentManager, "Payment")
                    }
                } else {
                    etInvoiceNo.error = getString(R.string.err_require_inovice_number)
                }
            }

            lyError.btnRetry.setOnClickListener {
                viewLayoutVisible(false)
//                invoiceListApiCall()
                homeApiCall()
            }

            val amountLabel = resources.getString(R.string.lbl_amount)
            var isFirst = true
            etInvoiceNo.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    mInvoiceVO = null
                    if (p0.toString().isNotEmpty()) {
                        etInvoiceNo.error = null
                        if (mList.size > 0) {
                            val list = mList.filter { it.invnumber!! == (p0.toString()) }

                            if (mList.find { it.invnumber!! == (p0.toString()) } != null) {
                                mInvoiceVO = mList.find { it.invnumber!! == (p0.toString()) }
                                tvInvoiceError.visibility = View.GONE
                                tvInvoiceAmount.visibility = View.VISIBLE
                                tvInvoiceAmount.text = "$amountLabel : ${mInvoiceVO?.totalcost}"
                                lyInvoiceNo.hintTextColor =
                                    ColorStateList.valueOf(resources.getColor(R.color.colorPrimary))
                                lyInvoiceNo.boxStrokeColor =
                                    resources.getColor(R.color.colorPrimary)

                                btnPay.isEnabled = mInvoiceVO?.enabledPay == 1


                            } else {
                                mInvoiceVO = null
                                tvInvoiceError.visibility = View.VISIBLE
                                tvInvoiceAmount.visibility = View.GONE
                                lyInvoiceNo.hintTextColor =
                                    ColorStateList.valueOf(resources.getColor(R.color.colorSecondary))
                                lyInvoiceNo.boxStrokeColor =
                                    resources.getColor(R.color.colorSecondary)
                            }
                        }
                    } else {
                        mInvoiceVO = null
                        tvInvoiceError.visibility = View.GONE
                        tvInvoiceAmount.visibility = View.GONE
                        lyInvoiceNo.hintTextColor =
                            ColorStateList.valueOf(resources.getColor(R.color.colorSecondary))
                        lyInvoiceNo.boxStrokeColor = resources.getColor(R.color.colorSecondary)

                        if (invoiceList.size > 0) {
                            etInvoiceNo.showDropDown()
                            etInvoiceNo.requestFocus()
                        }

                    }
                }

                override fun afterTextChanged(p0: Editable?) {

                }

            })

            etInvoiceNo.setOnFocusChangeListener { view, b ->
                if (b) {
                    mInvoiceVO = null
                    tvInvoiceError.visibility = View.GONE
                    lyInvoiceNo.hintTextColor =
                        ColorStateList.valueOf(resources.getColor(R.color.colorSecondary))
                    lyInvoiceNo.boxStrokeColor = resources.getColor(R.color.colorSecondary)
                }
            }

            etInvoiceNo.setOnTouchListener { view, motionEvent ->
                if (invoiceList.size > 0) {
                    if (etInvoiceNo.text.toString().isEmpty()) {
                        etInvoiceNo.showDropDown()
                        etInvoiceNo.requestFocus()
                    }
                }

                return@setOnTouchListener false
            }

            lyNotification.setOnClickListener {
                startActivity(NotificationActivity.newInstance(this@MainActivity))
                overridePendingTransition(R.anim.left_in, R.anim.left_out)
            }

            ivLanguage.setOnClickListener {
                startActivity(LanguageActivity.newInstance(this@MainActivity))
                overridePendingTransition(R.anim.left_in, R.anim.left_out)
            }


        }
    }

    private fun initLayout() {
        bannerAdapter = BannerVpAdapter(this, this)
        mCategoryAdapter = CategoryAdapter(this)

        binding.apply {
            vpBanner.adapter = bannerAdapter
//            dotsIndicator.setViewPager(vpBanner)
            indicator.setViewPager(vpBanner)
//            bannerAdapter.setItem(SampleData.bannerList())

            rvHomeCategory.layoutManager = GridLayoutManager(this@MainActivity, 4)
            rvHomeCategory.setHasFixedSize(true)
            rvHomeCategory.adapter = mCategoryAdapter
            mCategoryAdapter.setNewData(SampleData.categoryList(this@MainActivity))

            val lang = LocaleManager(this@MainActivity).getLanguage()


            val currentLangImg = if (lang == Constants.LANG_UNI)
                R.drawable.logo_myanmar
            else
                R.drawable.logo_united_states
            ivLanguage.setImageResource(currentLangImg)

        }
    }

    override fun onTapBanner(data: BannerVO) {
//        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(data.imagePath)))
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.agbcommunication.com/")))
        overridePendingTransition(R.anim.left_in, R.anim.left_out)
    }

    override fun onTapCategory(data: CategoryVO) {
        when (data.id) {
            1 -> {
                startActivity(MyPlanActivity.newInstance(this))
                overridePendingTransition(R.anim.left_in, R.anim.left_out)
            }

            2 -> {
                startActivity(InvoiceActivity.newInstance(this))
                overridePendingTransition(R.anim.left_in, R.anim.left_out)
            }

            3 -> {
                startActivity(ReceiptActivity.newInstance(this))
                overridePendingTransition(R.anim.left_in, R.anim.left_out)
            }

            4 -> {
                startActivity(ProfileActivity.newInstance(this))
                overridePendingTransition(R.anim.left_in, R.anim.left_out)
            }

            5 -> {
                startActivity(TermConditionActivity.newInstance(this))
                overridePendingTransition(R.anim.left_in, R.anim.left_out)
            }

            6 -> {
                startActivity(ContactUsActivity.newInstance(this))
                overridePendingTransition(R.anim.left_in, R.anim.left_out)
            }

            7 -> {
                if (BASE_TICKET_URL != "") {
                    startActivity(ComplainHistoryActivity.newInstance(this))
                    overridePendingTransition(R.anim.left_in, R.anim.left_out)
                } else
                    Toast.makeText(this, "Base Ticket Url is Empty!", Toast.LENGTH_SHORT).show()
            }

            else -> {
                val dialog = LogOutDialog(this)
                dialog.show(supportFragmentManager, "logout")
            }
        }
    }

    override fun onResume() {
        super.onResume()
//        invoiceListApiCall()
        viewLayoutVisible(false)
        homeApiCall()
        handler.postDelayed(timerun, delay)
        clearInvoiceText()
        checkSuccessData()
    }

    private fun checkSuccessData() {
        val intent = intent
        val result = intent.getIntExtra(KBZPay.EXTRA_RESULT, 0)
        if (result == KBZPay.COMPLETED) {
//            showNotice("pay success!")
            homeApiCall()
        } else {
            val failMsg = intent.getStringExtra(KBZPay.EXTRA_FAIL_MSG)
//            showNotice("pay fail, fail reason = $failMsg")
        }
    }

    private fun homeApiCall() {
        mViewModel.getHome()
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(timerun)
    }

    private fun clearInvoiceText() {
        binding.apply {
            binding.etInvoiceNo.setText("")
            binding.etInvoiceNo.clearFocus()
            tvInvoiceError.visibility = View.GONE
            lyInvoiceNo.hintTextColor =
                ColorStateList.valueOf(resources.getColor(R.color.colorPrimary))
            lyInvoiceNo.boxStrokeColor =
                resources.getColor(R.color.colorPrimary)
        }
    }

    override fun onTapLogout() {
        showProgress()
        mViewModel.getLogout()
    }

    private fun viewLayoutVisible(b: Boolean) {

        binding.apply {
            cvQuickPay.visibility = View.VISIBLE
            if (b) {
                shimmerLoading.stopShimmer()
                cvService.visibility = View.VISIBLE
                layoutWhatNew.visibility = View.VISIBLE
                cvQuickPay.visibility = View.VISIBLE
                shimmerLoading.visibility = View.GONE
                lyError.layoutError.visibility = View.GONE
            } else {
                shimmerLoading.startShimmer()
                cvService.visibility = View.GONE
                cvQuickPay.visibility = View.GONE
                layoutWhatNew.visibility = View.GONE
                shimmerLoading.visibility = View.VISIBLE
            }
        }
    }

    private fun paymentApiCall(paymentMethod: String) {
        showProgress()
        val request = KBZPaymentRequest()
        request.invnumber = mInvoiceVO?.invnumber.toString()
        request.paymentMethod = paymentMethod
        mViewModel.getKBZPayment(request)
    }

    fun languageChange(language: String) {
        LocaleManager(this).setNewLocale(this, language)
        reOpenApp()
    }

    private fun reOpenApp() {

        val refresh = Intent(this, MainActivity::class.java)
        startActivity(refresh)
        finish()
    }

    override fun setLogout(response: EmptyResponse) {
        dismissProgress()
        PreferenceUtils.setUser(UserVO())
        gotoLoginActivity()
    }


    override fun setData(response: InvoiceListResponse) {
        Log.e("INVOICE_RESPONSE", Gson().toJson(response))
        mInvoiceVO = null
        dismissProgress()
        viewLayoutVisible(true)
        if (response.data != null) {
//            if (response.data!!.size > 0) {
            mList = response.data!!

            if (invoiceList.size > 0) {
                invoiceList.clear()
            }

            for (i in 0 until mList.size) {
                invoiceList.add(mList[i].invnumber.toString())
            }

            arrayAdapter = ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1, invoiceList
            )
            binding.etInvoiceNo.setAdapter(arrayAdapter)
        }
//        }
    }

    override fun setHome(response: HomeResponse) {
        Log.e("HOME_RESPONSE", Gson().toJson(response))
        dismissProgress()
        viewLayoutVisible(true)
        if (response.data != null) {
            if (response.data!!.invoiceList!!.size > 0) {
                mList = response.data!!.invoiceList!!

                if (invoiceList.size > 0) {
                    invoiceList.clear()
                }

                for (i in 0 until mList.size) {
                    invoiceList.add(mList[i].invnumber.toString())
                }

                onBindInvoiceList()
            } else {
                if (invoiceList.size > 0) {
                    invoiceList.clear()
                    onBindInvoiceList()
                }

            }


            if (response.data!!.bannerImageList!!.size > 0) {
                bannerAdapter.setItem(response.data!!.bannerImageList!!)
            }

            if (searchInvoiceNo != "") {
                if (mList.find { it.invnumber!! == (searchInvoiceNo) } != null) {
                    paidStatus = 2
                } else {
                    paidStatus = 1
                }
            }

            if (dialogShow) {
                dialogShow = false
                var notiData = PreferenceUtils.getNotiData()
                if (notiData.notificationType.toString()
                        .isNotEmpty() && notiData.notificationType.toString() == "2"
                ) {
                    binding.etInvoiceNo.text.clear()
                    binding.etInvoiceNo.clearFocus()
                    if (paymentDialog == null) {
                        paymentDialog = PaymentSuccessDialog.newInstance(
                            notiData.title.toString(),
                            notiData.message.toString()
                        )
                        paymentDialog!!.show(supportFragmentManager, "payment")
                        PreferenceUtils.setNotiData(PaymentNotificationVO())
                    }
                } else {
                    if (paidStatus == 1) {
                        binding.etInvoiceNo.text.clear()
                        binding.etInvoiceNo.clearFocus()
                        infoDialog = PaymentSuccessDialog.newInstance(
                            "Info",
                            getString(R.string.payment_general_desc)
                        )
                        infoDialog!!.show(supportFragmentManager, "payment")
                    }
                }
//                infoDialog = PaymentSuccessDialog.newInstance("Info", getString(R.string.payment_general_desc))
//                infoDialog!!.show(supportFragmentManager, "payment")
            }
            BASE_TICKET_URL = response.data!!.ticketUrl.toString()

        }
    }

    private fun onBindInvoiceList() {
        arrayAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, invoiceList
        )
        binding.etInvoiceNo.setAdapter(arrayAdapter)
    }

    override fun setKBZPaymentData(response: KBZPaymentResponse) {
        Log.e("PAYMENT", Gson().toJson(response))
        dismissProgress()
        if (response.data != null) {
            dialogShow = true
            buildOrderInfoWithApi(response.data!!)
            startPay()
        }
    }


    override fun showError(message: String, code: String) {
        viewLayoutVisible(true)
        dismissProgress()

        if (code == Constants.FIELD_ERROR_CODE) {
            val paymentInfoDialog = AYAPaymentSuccessAndErrorDialog(
                this,
                getString(R.string.title_payment_info),
                message,
                code = Constants.FIELD_ERROR_CODE

            )
            paymentInfoDialog.show(supportFragmentManager, "paymentInfo")
        } else
            showErrorDialogWithEvent(getString(R.string.errorTitle), message)
    }

    override fun showInvalidSession(message: String, code: String) {
        viewLayoutVisible(true)
        dismissProgress()
        mInvalidSession(this, message)
    }

    override fun showNetworkFailed() {
        viewLayoutVisible(true)
        dismissProgress()
        showErrorDialogWithEvent(getString(R.string.errorTitle), Constants.CONNECTION_FAIL)
    }

    override fun showVersionUpdate(message: String, storeUrl: String) {
        viewLayoutVisible(true)
        dismissProgress()
        showVersionUpdateDialog(message, storeUrl)

    }

    private fun hideLoadingError() {
        binding.apply {
            shimmerLoading.stopShimmer()
            shimmerLoading.visibility = View.GONE
            lyError.layoutError.visibility = View.VISIBLE
            lyError.ivError.setImageResource(R.drawable.ic_component_invoices)
            lyError.tvError.text = resources.getString(R.string.error_invoice)
        }
    }

    private fun hideLoadingNetwork() {
        binding.apply {
            shimmerLoading.stopShimmer()
            shimmerLoading.visibility = View.GONE
            cvService.visibility = View.GONE
            cvQuickPay.visibility = View.GONE
            layoutWhatNew.visibility = View.GONE
            lyError.layoutError.visibility = View.VISIBLE
            lyError.btnRetry.visibility = View.VISIBLE
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

    fun gotoLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.right_in, R.anim.right_out)
        finish()
    }

    fun showErrorDialogWithEvent(title: String, message: String) {
        val builder = AlertDialog.Builder(this, R.style.MyAlertDialogStyle)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton(
            getString(R.string.str_retry)
        ) { dialogInterface, _ ->
//                invoiceListApiCall()
            homeApiCall()
            dialogInterface.dismiss()
        }
        val dialog = builder.create()
        dialog.window!!.attributes.windowAnimations = R.style.MyAlertDialogStyle
        dialog.show()
        dialog.setCancelable(true)

    }

    private fun buildOrderInfoWithApi(data: KBZPaymentVO) {
        // prepayId由服务器下单得到
//        val nonceStr = createRandomStr()
//        val timestamp = createTimestamp()
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
            com.kbzbank.payment.KBZPay.startPay(this, mOrderInfo, mSign, mSignType)
        } catch (ex: Exception) {

        }
    }


    override fun onStart() {
        super.onStart()
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        binding.btnPay.isEnabled = true
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    fun onMessageEvent(event: PaymentStatusEvent?) {
        // Do something
        Log.e("SAMPLE_NOTI", "Got it")
        if (event != null) {
            dialogShow = false
            if (event.data.message.toString().isNotEmpty() && event.data.title.toString()
                    .isNotEmpty()
            ) {
                if (infoDialog != null) {
                    infoDialog!!.dismiss()
                }
                binding.etInvoiceNo.text.clear()
                binding.etInvoiceNo.clearFocus()
                if (paymentDialog == null) {
                    val mTitle = event.data.title.toString()
                    val mBody = event.data.message.toString()
                    paymentDialog = PaymentSuccessDialog.newInstance(mTitle, mBody)
                    paymentDialog!!.show(supportFragmentManager, "payment")
                    PreferenceUtils.setNotiData(PaymentNotificationVO())
                }
            }
        }
    }

    override fun onTapPaymentType(paymentMethod: String, paymentTypeId: Int, phoneNo: String) {
        when (paymentTypeId) {
            1 -> {
//                createOrder()
                paymentApiCall(paymentMethod)
            }

            2 -> {
                ayaApiCall(paymentMethod, phoneNo)
                //testing kbz PWA payment
//                val dialog = PaymentIDBoxDialog()
//                dialog.show(supportFragmentManager, "payment")
            }

            3 -> {

                cbApiCall(paymentMethod)

            }
        }
    }

    override fun setAYAPaymentData(response: AYAPaymentResponse) {
        dismissProgress()
        if (response.data != null) {
            val paymentVO = response.data

            val paymentInfoDialog = AYAPaymentSuccessAndErrorDialog(
                this,
                paymentVO?.title.toString(),
                paymentVO?.desc.toString(),
                code = Constants.API_SUCCESS_CODE

            )
            paymentInfoDialog.show(supportFragmentManager, "paymentInfo")

        }

    }

    private fun ayaApiCall(paymentMethod: String, phoneNo: String) {
        showProgress()
        val request = PaymentRequest()
        request.invnumber = mInvoiceVO?.invnumber.toString()
        request.paymentMethod = paymentMethod
        request.phoneNo = phoneNo
        mViewModel.ayaPayment(request)

    }


    private fun cbApiCall(paymentMethod: String) {
        showProgress()
        val request = PaymentRequest()
        request.invnumber = mInvoiceVO?.invnumber.toString()
        request.paymentMethod = paymentMethod
        mViewModel.cbPayment(request)

    }

    override fun setCBPaymentData(response: CBPaymentResponse) {
        dismissProgress()
        if (response.data != null) {
            val paymentVO = response.data
            mInvoiceVO?.cbPayQRUrl = paymentVO!!.cbPayQRCode
            mInvoiceVO?.referenceNo = paymentVO.referenceNo
            startActivity(
                CBPayInformationActivity.newInstance(
                    this,
                    mInvoiceVO, isMainScreen = true
                )
            )
            overridePendingTransition(R.anim.left_in, R.anim.left_out)
        }
    }


    override fun onTapOk(code: String) {
        if (code == Constants.API_SUCCESS_CODE) {
            startActivity(InvoiceActivity.newInstance(this))
            overridePendingTransition(R.anim.left_in, R.anim.left_out)
        }

    }


}