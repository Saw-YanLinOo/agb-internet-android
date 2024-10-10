package com.agb.customer.billing.viewmodels

import com.agb.customer.billing.networks.RestClient
import com.agb.customer.billing.networks.requests.UpdateProfileRequest
import com.agb.customer.billing.networks.responseJsonPraser.JsonCatchParser
import com.agb.customer.billing.networks.responses.ProfileResponse
import com.agb.customer.billing.networks.responses.SuccessResponse
import com.agb.customer.billing.utils.Constants
import com.agb.customer.billing.views.ProfileView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel : BaseViewModel() {

    //api call
    var myView: ProfileView?=null

    fun setView(mView: ProfileView){
        this.myView = mView
    }

    fun getProfile(){

        RestClient.getApiService().getProfile()
            .enqueue(object : Callback<ProfileResponse> {

                override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                    myView!!.showError(Constants.CONNECTION_FAIL,"500")
                }

                override fun onResponse(
                    call: Call<ProfileResponse>,
                    response: Response<ProfileResponse>
                ) {
                    if (response.isSuccessful){

                        val  mResponse = response.body()!!
                        when (mResponse.responseCode) {

                            Constants.API_SUCCESS_CODE -> myView!!.setData(mResponse)
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

    fun getProfileUpdate(request : UpdateProfileRequest){

        RestClient.getApiService().getProfileUpdate(request)
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

                            Constants.API_SUCCESS_CODE -> myView!!.setUpdateSuccess(mResponse)
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