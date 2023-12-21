package com.agb.billing.customer.modelVO

import com.google.gson.annotations.SerializedName

class ChargesVO {
    @SerializedName("invnumber")
    var invnumber : String ?= ""
    @SerializedName("price")
    var price : String ?= ""
    @SerializedName("priceDesc")
    var priceDesc : String ?= ""
    @SerializedName("description")
    var description : String ?= ""
}