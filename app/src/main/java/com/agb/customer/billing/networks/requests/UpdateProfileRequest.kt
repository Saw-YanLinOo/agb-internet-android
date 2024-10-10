package com.agb.customer.billing.networks.requests

import com.google.gson.annotations.SerializedName

class UpdateProfileRequest(
    @SerializedName("phone")
    var phone: String ?= "",
    @SerializedName("email")
    var email: String ?= "",
    @SerializedName("viberno")
    var viberno: String ?= "",
    @SerializedName("alterPhNos")
    var alterPhNos: String ?= ""

) {
}