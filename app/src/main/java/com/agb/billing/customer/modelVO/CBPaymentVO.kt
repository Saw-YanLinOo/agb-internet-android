package com.agb.billing.customer.modelVO

import com.google.gson.annotations.SerializedName

class CBPaymentVO {
    @SerializedName("result")
    var title : String ?= ""
    @SerializedName("msg")
    var desc : String ?= ""
    @SerializedName("cbPayQRCode")
    var cbPayQRCode : String ?= ""
    @SerializedName("referenceNo")
    var referenceNo : String ?= ""

    @SerializedName("pdfUrl")
    var pdfUrl : String ?= ""



}