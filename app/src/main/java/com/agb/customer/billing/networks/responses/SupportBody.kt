package com.agb.customer.billing.networks.responses

import com.google.gson.annotations.SerializedName

class SupportBody{
    @SerializedName("phoneNo")
    var phoneNo: String ?= ""
    @SerializedName("mail")
    var mail: String ?= ""
    @SerializedName("address")
    var address: String ?= ""

}