package com.agb.billing.customer.viewmodels

import com.agb.billing.customer.networks.RestClient
import com.agb.billing.customer.networks.requests.InvoiceListRequest
import com.agb.billing.customer.networks.responseJsonPraser.JsonCatchParser
import com.agb.billing.customer.networks.responses.InvoiceListResponse
import com.agb.billing.customer.utils.Constants
import com.agb.billing.customer.views.InvoiceListView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InvoiceListViewModel : BaseViewModel() {

    //api call
    var myView: InvoiceListView?=null

    fun setView(mView: InvoiceListView){
        this.myView = mView
    }

    fun getInvoiceList(request : InvoiceListRequest){

        RestClient.getApiService().getInvoiceList(request)
            .enqueue(object : Callback<InvoiceListResponse> {

                override fun onFailure(call: Call<InvoiceListResponse>, t: Throwable) {
                    myView!!.showError(Constants.CONNECTION_FAIL,"500")
                }

                override fun onResponse(
                    call: Call<InvoiceListResponse>,
                    response: Response<InvoiceListResponse>
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
}