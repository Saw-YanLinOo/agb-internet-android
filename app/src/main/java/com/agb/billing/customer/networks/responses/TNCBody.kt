package com.agb.billing.customer.networks.responses

import com.google.gson.annotations.SerializedName

class TNCBody{
    @SerializedName("description")
    var description: String ?= ""
}