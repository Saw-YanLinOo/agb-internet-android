package com.agb.billing.customer.viewmodels

import com.agb.billing.customer.networks.RestClient
import com.agb.billing.customer.networks.requests.EmptyRequest
import com.agb.billing.customer.networks.requests.NotificationRequest
import com.agb.billing.customer.networks.responseJsonPraser.JsonCatchParser
import com.agb.billing.customer.networks.responses.NotificationResponse
import com.agb.billing.customer.utils.Constants
import com.agb.billing.customer.views.NotificationView
import retrofit2.Call
import retrofit2.Response

class NotificationViewModel : BaseViewModel() {

    var myView: NotificationView? = null

    fun setVIew(mView: NotificationView) {
        this.myView = mView
    }

    fun getNotificationList(request: NotificationRequest) {
        RestClient.getApiService().getNotificationList(request)
            .enqueue(object : retrofit2.Callback<NotificationResponse> {
                override fun onResponse(
                    call: Call<NotificationResponse>,
                    response: Response<NotificationResponse>
                ) {
                    if (response.isSuccessful) {

                        val mResponse = response.body()!!
                        when (mResponse.responseCode) {

                            Constants.API_SUCCESS_CODE -> myView!!.responseNotificationData(
                                mResponse
                            )

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

                                            myView!!.showError(
                                                mResponse.error!![0].errorMessage.toString(),
                                                "500"
                                            )

                                        } else {
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
                                myView!!.showError(response.message(), "500")
                            }
                        }
                    } else {
                        if (response.errorBody() != null) {
                            myView!!.showError(
                                JsonCatchParser.getApiErrorJsonData(
                                    response.errorBody()!!.string()
                                ), "500"
                            )
                        }
                    }

                }

                override fun onFailure(call: Call<NotificationResponse>, t: Throwable) {
                    myView!!.showError(Constants.CONNECTION_FAIL, "500")
                }

            })
    }
}