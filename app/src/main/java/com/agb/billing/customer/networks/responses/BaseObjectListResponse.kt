package com.agb.billing.customer.networks.responses

import com.google.gson.annotations.SerializedName

class BaseObjectListResponse<T> : BaseResponse() {
    @SerializedName("data")
    val data: MutableList<T> = mutableListOf()
}