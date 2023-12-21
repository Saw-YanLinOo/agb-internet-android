package com.agb.billing.customer.networks.requests

import com.google.gson.annotations.SerializedName

class ResetPasswordRequest(
    @SerializedName("username")
    var username: String ?= "",
    @SerializedName("newPassword")
    var newPassword: String ?= "",
    @SerializedName("confirmPassword")
    var confirmPassword: String ?= "",
    @SerializedName("deviceToken")
    var deviceToken: String ?= ""
) {
}