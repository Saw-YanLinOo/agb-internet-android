package com.agb.billing.customer.viewmodels

import com.agb.billing.customer.networks.RestClient
import com.agb.billing.customer.networks.responseJsonPraser.JsonCatchParser
import com.agb.billing.customer.networks.responses.TNCResponse
import com.agb.billing.customer.utils.Constants
import com.agb.billing.customer.views.TNCView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TNCViewModel : BaseViewModel() {

    //api call
    var myView: TNCView?=null

    fun setView(mView: TNCView){
        this.myView = mView
    }

    fun getTNC(){

        RestClient.getApiService().getTNC()
            .enqueue(object : Callback<TNCResponse> {

                override fun onFailure(call: Call<TNCResponse>, t: Throwable) {
                    myView!!.showError(Constants.CONNECTION_FAIL,"500")
                }

                override fun onResponse(
                    call: Call<TNCResponse>,
                    response: Response<TNCResponse>
                ) {
                    if (response.isSuccessful){

                        val  mResponse = response.body()!!
                        when (mResponse.responseCode) {

                            Constants.API_SUCCESS_CODE -> myView!!.setTNC(mResponse)
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