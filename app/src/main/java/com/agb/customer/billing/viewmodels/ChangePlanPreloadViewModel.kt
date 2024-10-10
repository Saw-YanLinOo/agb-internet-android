package com.agb.customer.billing.viewmodels

import com.agb.customer.billing.networks.RestClient
import com.agb.customer.billing.networks.requests.*
import com.agb.customer.billing.networks.responseJsonPraser.JsonCatchParser
import com.agb.customer.billing.networks.responses.ChangePlanPreloadResponse
import com.agb.customer.billing.networks.responses.PlanByBandWidthResponse
import com.agb.customer.billing.networks.responses.SuccessResponse
import com.agb.customer.billing.utils.Constants
import com.agb.customer.billing.views.ChangePlanPreloadView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePlanPreloadViewModel : BaseViewModel() {

    //api call
    var myView: ChangePlanPreloadView?=null

    fun setView(mView: ChangePlanPreloadView){
        this.myView = mView
    }

    fun getChangePlanPreload(requrest : ChangePlanPreloadRequest){

        RestClient.getApiService().getChangePlanPreload(requrest)
            .enqueue(object : Callback<ChangePlanPreloadResponse> {

                override fun onFailure(call: Call<ChangePlanPreloadResponse>, t: Throwable) {
                    myView!!.showError(Constants.CONNECTION_FAIL,"500")
                }

                override fun onResponse(
                    call: Call<ChangePlanPreloadResponse>,
                    response: Response<ChangePlanPreloadResponse>
                ) {
                    if (response.isSuccessful){

                        val  mResponse = response.body()!!
                        when (mResponse.responseCode) {

                            Constants.API_SUCCESS_CODE -> myView!!.setChangePlanPreload(mResponse)
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

    fun getPlanByBandWidth(request : PlanByBandWidthRequest){

        RestClient.getApiService().getPlanByBandWidth(request)
            .enqueue(object : Callback<PlanByBandWidthResponse> {

                override fun onFailure(call: Call<PlanByBandWidthResponse>, t: Throwable) {
                    myView!!.showError(Constants.CONNECTION_FAIL,"500")
                }

                override fun onResponse(
                    call: Call<PlanByBandWidthResponse>,
                    response: Response<PlanByBandWidthResponse>
                ) {
                    if (response.isSuccessful){

                        val  mResponse = response.body()!!
                        when (mResponse.responseCode) {

                            Constants.API_SUCCESS_CODE -> myView!!.setPlanByBandWidth(mResponse)
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

    fun getChangePlan(request : ChangePlanRequest){

        RestClient.getApiService().getChangePlan(request)
            .enqueue(object : Callback<SuccessResponse> {

                override fun onFailure(call: Call<SuccessResponse>, t: Throwable) {
                    myView!!.showError(Constants.CONNECTION_FAIL,"500")
                }

                override fun onResponse(
                    call: Call<SuccessResponse>,
                    response: Response<SuccessResponse>
                ) {
                    if (response.isSuccessful){

                        val  mResponse = response.body()!!
                        when (mResponse.responseCode) {

                            Constants.API_SUCCESS_CODE -> myView!!.setChangePlan(mResponse)
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