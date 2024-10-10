package com.agb.customer.billing.modelVO

import com.google.gson.annotations.SerializedName

class InvoiceVO {
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
    @SerializedName("paid")
    var paid : Int ?= 0
    @SerializedName("paidDesc")
    var paidDesc : String ?= ""

    @SerializedName("enabled")
    var enabledPay : Int ?= 0
    @SerializedName("issuedate")
    var issueDate : String ?= ""

    var cbPayQRUrl : String ?= ""

    var referenceNo : String ?= ""

}