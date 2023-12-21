package com.agb.billing.customer.networks.responses

import com.agb.billing.customer.modelVO.BannerVO
import com.agb.billing.customer.modelVO.InvoiceVO
import com.google.gson.annotations.SerializedName

class HomeBody : BaseResponse() {
    @SerializedName("invoiceList")
    var invoiceList: MutableList<InvoiceVO> ?= mutableListOf()
    @SerializedName("bannerImageList")
    var bannerImageList: MutableList<BannerVO> ?= mutableListOf()

    @SerializedName("returnUrl")
    var ticketUrl : String ?= ""
}