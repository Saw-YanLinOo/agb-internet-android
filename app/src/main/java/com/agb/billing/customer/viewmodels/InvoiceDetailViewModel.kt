package com.agb.billing.customer.viewmodels

import android.util.Log
import com.agb.billing.customer.networks.RestClient
import com.agb.billing.customer.networks.requests.InvoiceDetailRequest
import com.agb.billing.customer.networks.requests.KBZPaymentRequest
import com.agb.billing.customer.networks.requests.PaymentRequest
import com.agb.billing.customer.networks.responseJsonPraser.JsonCatchParser
import com.agb.billing.customer.networks.responses.AYAPaymentResponse
import com.agb.billing.customer.networks.responses.CBPaymentResponse
import com.agb.billing.customer.networks.responses.InvoiceDetailResponse
import com.agb.billing.customer.networks.responses.KBZPaymentResponse
import com.agb.billing.customer.utils.Constants
import com.agb.billing.customer.views.InvoiceDetailView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InvoiceDetailViewModel : BaseViewModel() {

    //api call
    var myView: InvoiceDetailView?=null

    fun setView(mView: InvoiceDetailView){
        this.myView = mView
    }

    fun getKBZPayment(request : KBZPaymentRequest){

        RestClient.getApiService().getKBZPayment(request)
            .enqueue(object : Callback<KBZPaymentResponse> {

                override fun onFailure(call: Call<KBZPaymentResponse>, t: Throwable) {
                    myView!!.showError(Constants.CONNECTION_FAIL,"500")
                }

                override fun onResponse(
                    call: Call<KBZPaymentResponse>,
                    response: Response<KBZPaymentResponse>
                ) {
                    if (response.isSuccessful){
                        val  mResponse = response.body()!!
                        when (mResponse.responseCode) {

                            Constants.API_SUCCESS_CODE -> myView!!.setKBZPaymentData(mResponse)
                            Constants.INVALID_SESSION_CODE -> myView!!.showInvalidSession(mResponse.responseMessage!!,mResponse.responseCode.toString())
                            Constants.API_FAILED_CODE -> myView!!.showError(mResponse.responseMessage!!, mResponse.responseCode.toString())
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
                                myView!!.showError(response.message(),"500")
                            }

                        }

                    }
                    else{
                        if(response.errorBody() != null) {
                            myView!!.showError(
                                JsonCatchParser.getApiErrorJsonData(
                                    response.errorBody()!!.string()
                                ),"500")
                        }
                    }
                }

            })

    }

    fun getInvoiceDetail(request : InvoiceDetailRequest){

        RestClient.getApiService().getInvoiceDetail(request)
            .enqueue(object : Callback<InvoiceDetailResponse> {

                override fun onFailure(call: Call<InvoiceDetailResponse>, t: Throwable) {
                    Log.e("OnFailure",t.message.toString())
                    myView!!.showError(Constants.CONNECTION_FAIL,"500")
                }

                override fun onResponse(
                    call: Call<InvoiceDetailResponse>,
                    response: Response<InvoiceDetailResponse>
                ) {
                    if (response.isSuccessful){
                        val  mResponse = response.body()!!
                        when (mResponse.responseCode) {

                            Constants.API_SUCCESS_CODE -> myView!!.setInvoiceDetail(mResponse)
                            Constants.INVALID_SESSION_CODE -> myView!!.showInvalidSession(mResponse.responseMessage!!,mResponse.responseCode.toString())
                            Constants.API_FAILED_CODE -> myView!!.showError(mResponse.responseMessage!!, mResponse.responseCode.toString())
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
                                myView!!.showError(response.message(),"500")
                            }

                        }

                    }
                    else{
                        if(response.errorBody() != null) {
                            myView!!.showError(
                                JsonCatchParser.getApiErrorJsonData(
                                    response.errorBody()!!.string()
                                ),"500")
                        }
                    }
                }

            })

    }

    fun ayaPayment(request : PaymentRequest){

        RestClient.getApiService().ayaPayment(request)
            .enqueue(object : Callback<AYAPaymentResponse> {

                override fun onFailure(call: Call<AYAPaymentResponse>, t: Throwable) {
                    myView!!.showError(Constants.CONNECTION_FAIL,"500")
                }

                override fun onResponse(
                    call: Call<AYAPaymentResponse>,
                    response: Response<AYAPaymentResponse>
                ) {
                    if (response.isSuccessful){
                        val  mResponse = response.body()!!
                        when (mResponse.responseCode) {

                            Constants.API_SUCCESS_CODE -> myView!!.setAYAPaymentData(mResponse)
                            Constants.INVALID_SESSION_CODE -> myView!!.showInvalidSession(mResponse.responseMessage!!,mResponse.responseCode.toString())
                            Constants.API_FAILED_CODE -> myView!!.showError(mResponse.responseMessage!!, mResponse.responseCode.toString())
                            Constants.API_MULTI_ERROR_CODE -> {

                                try {
                                    if (mResponse.error != null) {

                                        if (mResponse.error!!.size > 0) {

                                            myView!!.showError(
                                                mResponse.error!![0].errorMessage.toString(),
                                                mResponse.error!![0].fieldCode.toString()
                                            )

                                        } else {
                                            myView!!.showError(
                                                mResponse.responseMessage.toString(),
                                                mResponse.error!![0].fieldCode.toString()
                                            )
                                        }

                                    }
                                } catch (ex: Exception) {

                                }

                            }

                            else -> {
                                myView!!.showError(response.message(),"500")
                            }

                        }

                    }
                    else{
                        if(response.errorBody() != null) {
                            myView!!.showError(
                                JsonCatchParser.getApiErrorJsonData(
                                    response.errorBody()!!.string()
                                ),"500")
                        }
                    }
                }

            })

    }

    fun cbPayment(request : PaymentRequest){

        RestClient.getApiService().cbPayment(request)
            .enqueue(object : Callback<CBPaymentResponse> {

                override fun onFailure(call: Call<CBPaymentResponse>, t: Throwable) {
                    myView!!.showError(Constants.CONNECTION_FAIL,"500")
                }

                override fun onResponse(
                    call: Call<CBPaymentResponse>,
                    response: Response<CBPaymentResponse>
                ) {
                    if (response.isSuccessful){
                        val  mResponse = response.body()!!
                        when (mResponse.responseCode) {

                            Constants.API_SUCCESS_CODE -> myView!!.setCBPaymentData(mResponse)
                            Constants.INVALID_SESSION_CODE -> myView!!.showInvalidSession(mResponse.responseMessage!!,mResponse.responseCode.toString())
                            Constants.API_FAILED_CODE -> myView!!.showError(mResponse.responseMessage!!, mResponse.responseCode.toString())
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
                                myView!!.showError(response.message(),"500")
                            }

                        }

                    }
                    else{
                        if(response.errorBody() != null) {
                            myView!!.showError(
                                JsonCatchParser.getApiErrorJsonData(
                                    response.errorBody()!!.string()
                                ),"500")
                        }
                    }
                }

            })

    }
}