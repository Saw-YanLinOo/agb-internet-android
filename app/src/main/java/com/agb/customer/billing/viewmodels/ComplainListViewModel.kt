package com.agb.customer.billing.viewmodels

import com.agb.customer.billing.networks.RestClientTicket
import com.agb.customer.billing.networks.requests.ComplainListRequest
import com.agb.customer.billing.networks.responseJsonPraser.JsonCatchParser
import com.agb.customer.billing.networks.responses.ComplainListResponse
import com.agb.customer.billing.utils.Constants
import com.agb.customer.billing.views.ComplainListView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ComplainListViewModel : BaseViewModel() {
    //api call
    var myView: ComplainListView? = null
    fun setView(mView: ComplainListView) {
        this.myView = mView
    }

    fun getComplainList(request: ComplainListRequest) {

        RestClientTicket.getApiService().getComplainList(request)
            .enqueue(object : Callback<ComplainListResponse> {

                override fun onFailure(call: Call<ComplainListResponse>, t: Throwable) {
                    myView!!.showError(Constants.CONNECTION_FAIL, "500")
                }

                override fun onResponse(
                    call: Call<ComplainListResponse>,
                    response: Response<ComplainListResponse>,
                ) {
                    if (response.isSuccessful) {

                        val mResponse = response.body()!!
                        when (mResponse.responseCode) {

                            Constants.API_SUCCESS_CODE -> myView!!.setData(mResponse)
                            Constants.INVALID_SESSION_CODE -> myView!!.showInvalidSession(mResponse.responseMessage!!,
                                mResponse.responseCode.toString())
                            Constants.API_FAILED_CODE -> myView!!.showError(mResponse.responseMessage!!,
                                mResponse.responseCode.toString())
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
                                ), "500")
                        }
                    }
                }

            })

    }
}