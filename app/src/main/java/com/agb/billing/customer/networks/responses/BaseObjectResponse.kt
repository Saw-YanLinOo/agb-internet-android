package com.agb.billing.customer.networks.responses

import com.google.gson.annotations.SerializedName

open class BaseObjectResponse<T> : BaseResponse() {
    @SerializedName("data")
    val data: T? = null
}