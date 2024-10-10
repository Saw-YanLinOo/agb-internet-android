package com.agb.customer.billing.modelVO

import com.google.gson.annotations.SerializedName

class InvoiceDetailVO {
    @SerializedName("id")
    var id : Int ?= 0
    @SerializedName("invnumber")
    var invnumber : String ?= ""
    @SerializedName("specialcode")
    var specialcode : String ?= ""
    @SerializedName("startdate")
    var startdate : String ?= ""
    @SerializedName("enddate")
    var enddate : String ?= ""
    @SerializedName("totalcost")
    var totalcost : String ?= ""
    @SerializedName("paidDesc")
    var paidDesc : String ?= ""
    @SerializedName("paiddate")
    var paiddate : String ?= ""
    @SerializedName("payment_provider")
    var payment_provider : String ?= ""
    @SerializedName("receiptid")
    var receiptid : String ?= ""
    @SerializedName("transaction_id")
    var transaction_id : String ?= ""
    @SerializedName("duedate")
    var duedate : String ?= ""
    @SerializedName("descriptions")
    var description : MutableList<ChargesVO> ?= mutableListOf()
    @SerializedName("termsAndConditionsDesc")
    var termsAndConditionsDesc : String ?= ""
    @SerializedName("pdfUrl")
    var pdfUrl : String ?= ""

    @SerializedName("enabled")
    var enabledPay : Int ?= null
}