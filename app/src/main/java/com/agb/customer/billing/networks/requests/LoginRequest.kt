package com.agb.customer.billing.networks.requests

import com.google.gson.annotations.SerializedName

class LoginRequest(
    @SerializedName("username")
    var username: String ?= "",
    @SerializedName("password")
    var password: String ?= "",
    @SerializedName("deviceToken")
    var deviceToken: String ?= ""

) {
}