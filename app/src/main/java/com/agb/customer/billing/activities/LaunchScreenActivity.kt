package com.agb.customer.billing.activities

import android.R.id.message
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import com.agb.customer.billing.activities.BaseActivity
import com.agb.customer.billing.R
import com.agb.customer.billing.databinding.ActivitySplashBinding
import com.agb.customer.billing.localizations.LocaleManager
import com.agb.customer.billing.modelVO.PaymentNotificationVO
import com.agb.customer.billing.networks.ApiServices
import com.agb.customer.billing.networks.EndPoints
import com.agb.customer.billing.networks.RestClient
import com.agb.customer.billing.networks.requests.LoginRequest
import com.agb.customer.billing.networks.responses.LoginResponse
import com.agb.customer.billing.utils.Constants
import com.agb.customer.billing.utils.DialogUtil
import com.agb.customer.billing.utils.PreferenceUtils
import com.agb.customer.billing.views.LaunchScreenView
import com.google.gson.GsonBuilder
import kotlinx.coroutines.delay
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.time.Duration

class LaunchScreenActivity : BaseActivity() {

    lateinit var binding: ActivitySplashBinding
    private var currentBaseUrlIndex = 0
    private val baseUrls = listOf(EndPoints.BASE_URL, EndPoints.BASE_URL_EXTRA)
    private var isCheckingSavedUrl = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        LocaleManager(this).setLocale(this)
        PreferenceUtils.setNotiData(PaymentNotificationVO()) //noti
        hideStatusBar()
        setContentView(binding.root)

//        PreferenceUtils.setBaseUrl("http://app.agbcommunication.com.mm/api/api/")

        checkBaseURLsAndProceed()

    }

    private fun checkBaseURLsAndProceed() {
        val savedBaseUrl = PreferenceUtils.getBaseUrl()
        Log.e("LAUNCH_SCREEN_ERROR", "Saved URL -----$savedBaseUrl")
        if (!savedBaseUrl.isNullOrEmpty() ) { // && baseUrls.contains(savedBaseUrl)
            isCheckingSavedUrl = true
            checkBaseURL(savedBaseUrl)
        } else{
            isCheckingSavedUrl = false
            currentBaseUrlIndex = 0
            checkBaseURL(baseUrls[currentBaseUrlIndex])
        }
    }

    private fun checkBaseURL(baseUrl: String) {
        val request = LoginRequest().apply {
            username = "AGB-EMP-00325"
            password = "2"
        }

        // Create a temporary RestClient instance with the current baseUrl
        val tempApiService = createTempApiService(baseUrl)

        tempApiService.getLogin(request)
            .enqueue(object : Callback<LoginResponse> {
                override fun onFailure(call: retrofit2.Call<LoginResponse>, t: Throwable) {
                    handleBaseUrlFailure()
                }

                override fun onResponse(
                    call: retrofit2.Call<LoginResponse>,
                    response: retrofit2.Response<LoginResponse>
                ) {
                    if (response.isSuccessful) {
                        // Base URL is working, save it and proceed
                        PreferenceUtils.setBaseUrl(baseUrl)
                        RestClient.updateBaseUrl(baseUrl)
                        proceedToNextActivity()
                    } else {
                        handleBaseUrlFailure()
                    }
                }
            })
    }

    private fun handleBaseUrlFailure() {

        if(isCheckingSavedUrl){
            isCheckingSavedUrl = false
            currentBaseUrlIndex = 0
            checkBaseURL(baseUrls[currentBaseUrlIndex])
        }
        else {
            currentBaseUrlIndex++

            if (currentBaseUrlIndex < baseUrls.size) {
                // Try the next base URL
                checkBaseURL(baseUrls[currentBaseUrlIndex])
            } else {
                // All base URLs failed, show error
                showError(
                    message = "Unable to connect to server. Please check your internet connection and try again.",
                    code = Constants.CONNECTION_FAIL)
            }
        }
    }

    private fun createTempApiService(baseUrl: String): ApiServices {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .addInterceptor(Interceptor { chain ->
                val original = chain.request()
                val request = original.newBuilder()
                    .header("Accept", "application/json")
                    .header("systemCode","2")
                    .build()
                chain.proceed(request)
            })
            .build()

        val gson = GsonBuilder()
            .setLenient()
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()

        return retrofit.create(ApiServices::class.java)
    }

    private fun proceedToNextActivity() {
        Handler().postDelayed({
            startActivity(LoginActivity.newInstance(this@LaunchScreenActivity))
            overridePendingTransition(R.anim.left_in, R.anim.left_out)
            finish()
        }, 1000)
    }

    fun showError(message: String, code: String) {
        Log.e("LAUNCH_SCREEN_ERROR", "$code-----$message")
        DialogUtil(this).showErrorDialog(getString(R.string.errorTitle), message)
    }
}