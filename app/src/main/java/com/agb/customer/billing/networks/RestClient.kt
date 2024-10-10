package com.agb.customer.billing.networks

import android.util.Log
import com.agb.customer.billing.BuildConfig
import com.agb.customer.billing.utils.Constants
import com.agb.customer.billing.utils.PreferenceUtils
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RestClient {
    private val apiServices: ApiServices

    init {

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()

            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
        okHttpClient.addInterceptor(logging)
        okHttpClient.interceptors().add(Interceptor { chain ->
            val original = chain.request()

            val request = original.newBuilder()
                .header("Accept", "application/json")
                .build()

            chain.proceed(request)
        })

        val gson = GsonBuilder()
            .setLenient()
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl(EndPoints.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient.build())
            .build()

        apiServices = retrofit.create(ApiServices::class.java)

    }

    companion object {

        private fun getRetrofit(): Retrofit {

            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            val okHttpClient = OkHttpClient.Builder()
            okHttpClient.addInterceptor(HeaderInterceptor())
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
            okHttpClient.addInterceptor(logging)

            return Retrofit.Builder()
                .addConverterFactory(
                    GsonConverterFactory.create()
                )
                .client(okHttpClient.build())
                .baseUrl(EndPoints.BASE_URL)
                .build()
        }

        private fun getApiData(): Retrofit {
            return getRetrofit()
        }

        fun getApiService(): ApiServices {
            val retrofitCall = getApiData()
            return retrofitCall.create(ApiServices::class.java)
        }

    }

    class HeaderInterceptor : Interceptor {

        override fun intercept(chain: Interceptor.Chain): Response = chain.run {
            val user = PreferenceUtils.getUser()
            var mLangCode = "EN"
            val languageCode = PreferenceUtils.getAppLanguage()
            if(languageCode == Constants.LANG_EN){
                mLangCode = "EN"
            }else{
                mLangCode = "MM"
            }
            var mSessionId = user.sessionId.toString()
            var mCustomerId = user.customerId.toString()

            val systemCode = "2"
            val versionNo = BuildConfig.VERSION_NAME


            //need to add ( fontType 1 (eng ) , fontType 2 ( MM
//            if(languageCode == Constants.LANG_EN){
//                fontType = "1"
//            }else{
//                fontType = "2"
//            }
            Log.e("SESSION","$mCustomerId-----$mSessionId----${languageCode.toUpperCase()}")
            proceed(
                request()
                    .newBuilder()
                    .addHeader("customerId", mCustomerId)
                    .addHeader("sessionId", mSessionId)
                    .addHeader("LANG_CODE", mLangCode)
                    .addHeader("deviceType",Constants.DEVICE_TYPE)
                    .addHeader("systemCode",systemCode)
                    .addHeader("versionNo",versionNo)
                    .build()
            )
        }
    }

}