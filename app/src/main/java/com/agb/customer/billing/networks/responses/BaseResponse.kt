package com.agb.customer.billing.networks.responses

import com.google.gson.annotations.SerializedName

open class BaseResponse{

    @SerializedName("responseCode")
    var responseCode: String? = null

    @SerializedName("responseMessage")
    var responseMessage: String? = null

    @SerializedName("error")
    var error: MutableList<BaseError>? = mutableListOf()

}