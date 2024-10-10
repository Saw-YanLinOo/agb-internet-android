package com.agb.customer.billing.networks.responses

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class BaseError : Serializable {
    @SerializedName("fieldErrorCode")
    var fieldCode: String? = null

    @SerializedName("errorMessage")
    var errorMessage: String? = null

    @SerializedName("errorTitle")
    var errorTitle: String? = null

    @SerializedName("storeUrl")
    var storeUrl: String? = null
}