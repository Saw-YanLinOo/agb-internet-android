package com.agb.customer.billing.viewmodels

import com.agb.customer.billing.networks.RestClient
import com.agb.customer.billing.networks.RestClientTicket
import com.agb.customer.billing.networks.requests.ComplainListRequest
import com.agb.customer.billing.networks.requests.EmptyRequest
import com.agb.customer.billing.networks.requests.TicketCreateUpdateRequest
import com.agb.customer.billing.networks.responseJsonPraser.JsonCatchParser
import com.agb.customer.billing.networks.responses.ComplainListResponse
import com.agb.customer.billing.networks.responses.ComplainPreloadResponse
import com.agb.customer.billing.networks.responses.TicketResponse
import com.agb.customer.billing.utils.Constants
import com.agb.customer.billing.views.ComplainListView
import com.agb.customer.billing.views.ComplainView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ComplainViewModel : BaseViewModel() {
    //api call
    var myView: ComplainView? = null
    fun setView(mView: ComplainView) {
        this.myView = mView
    }

    fun getComplainPreLoad(request: EmptyRequest) {

        RestClient.getApiService().getComplainPreLoad(request)
            .enqueue(object : Callback<ComplainPreloadResponse> {

                override fun onFailure(call: Call<ComplainPreloadResponse>, t: Throwable) {
                    myView!!.showError(Constants.CONNECTION_FAIL, "500")
                }

                override fun onResponse(
                    call: Call<ComplainPreloadResponse>,
                    response: Response<ComplainPreloadResponse>,
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
                            Constants.API_VERSION_UPDATE_CODE -> {
                                try {
                                    if (mResponse.error != null) {
                                        myView!!.showVersionUpdate(
                                            mResponse.error!![0].errorMessage.toString(),
                                            mResponse.error!![0].storeUrl.toString()
                                        )
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

    fun uploadComplain(request: TicketCreateUpdateRequest) {

        RestClientTicket.getApiService().uploadComplain(request)
            .enqueue(object : Callback<TicketResponse> {

                override fun onFailure(call: Call<TicketResponse>, t: Throwable) {
                    myView!!.showError(Constants.CONNECTION_FAIL, "500")
                }

                override fun onResponse(
                    call: Call<TicketResponse>,
                    response: Response<TicketResponse>,
                ) {
                    if (response.isSuccessful) {

                        val mResponse = response.body()!!
                        when (mResponse.responseCode) {

                            Constants.API_SUCCESS_CODE -> myView!!.setUploadResponseData(mResponse)
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