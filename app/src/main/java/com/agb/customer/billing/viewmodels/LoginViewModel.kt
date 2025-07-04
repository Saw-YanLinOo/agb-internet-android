package com.agb.customer.billing.viewmodels

import android.util.Log
import com.agb.customer.billing.networks.RestClient
import com.agb.customer.billing.networks.requests.LoginRequest
import com.agb.customer.billing.networks.responseJsonPraser.JsonCatchParser
import com.agb.customer.billing.networks.responses.LoginResponse
import com.agb.customer.billing.utils.Constants
import com.agb.customer.billing.views.LoginView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : BaseViewModel() {

    //api call
    var myView: LoginView? = null

    fun setView(mView: LoginView) {
        this.myView = mView
    }

    fun getLogin(request: LoginRequest) {

        RestClient.getApiService().getLogin(request)
            .enqueue(object : Callback<LoginResponse> {

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    myView!!.showError(Constants.CONNECTION_FAIL, "500")
                }

                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if (response.isSuccessful) {

                        val mResponse = response.body()!!
                        when (mResponse.responseCode) {

                            Constants.API_SUCCESS_CODE -> myView!!.setData(mResponse)
                            Constants.INVALID_SESSION_CODE -> myView!!.showInvalidSession(
                                mResponse.responseMessage!!,
                                mResponse.responseCode.toString()
                            )

                            Constants.API_FAILED_CODE -> myView!!.showError(
                                mResponse.responseMessage!!,
                                mResponse.responseCode.toString()
                            )

                            Constants.API_MULTI_ERROR_CODE -> {

                                try {
                                    if (mResponse.error != null) {

                                        if (mResponse.error!!.size > 0) {
                                            Log.e(
                                                "ERROR_RESPONSE1",
                                                "${mResponse.error!![0].fieldCode.toString()}"
                                            )
                                            myView!!.showError(
                                                mResponse.error!![0].errorMessage.toString(),
                                                mResponse.error!![0].fieldCode.toString()
                                            )

                                        } else {
                                            Log.e("ERROR_RESPONSE4", "gg")
                                            myView!!.showError(
                                                mResponse.responseMessage.toString(),
                                                "500"
                                            )
                                        }

                                    }
                                } catch (ex: Exception) {

                                }

                            }

                            else -> {
                                Log.e("ERROR_RESPONSE2", "gg")
                                myView!!.showError(response.message(), "500")
                            }

                        }

                    }

                    else if (response.code() == 500) {
                        Log.e("ERROR_RESPONSE6", "gg")
                        myView!!.showError(Constants.CONNECTION_FAIL, "500")

                    } else {
                        if (response.errorBody() != null) {
                            Log.e("ERROR_RESPONSE3", "gg")
                            myView!!.showError(
                                JsonCatchParser.getApiErrorJsonData(
                                    response.errorBody()!!.string()
                                ), "500"
                            )
                        }

                    }
                }

            })

    }
}