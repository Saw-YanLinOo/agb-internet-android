package com.agb.billing.customer.modelVO

import com.google.gson.annotations.SerializedName

class ReceiptVO {
    @SerializedName("id")
    var id : Int ?= 0
    @SerializedName("invnumber")
    var invnumber : String ?= ""
    @SerializedName("specialcode")
    var specialcode : String ?= ""
    @SerializedName("expirationdate")
    var expirationdate : String ?= ""
    @SerializedName("totalcost")
    var totalcost : String ?= ""
    @SerializedName("paidDesc")
    var paidDesc : String ?= ""
    @SerializedName("paiddate")
    var paiddate : String ?= ""

}