package com.agb.billing.customer.modelVO

import com.google.gson.annotations.SerializedName

class PayPerVO {
    @SerializedName("id")
    var id : Int ?= 0
    @SerializedName("name")
    var name : String ?= ""

}