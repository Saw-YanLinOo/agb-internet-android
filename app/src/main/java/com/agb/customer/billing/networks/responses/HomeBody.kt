package com.agb.customer.billing.networks.responses

import com.agb.customer.billing.modelVO.BannerVO
import com.agb.customer.billing.modelVO.InvoiceVO
import com.google.gson.annotations.SerializedName

class HomeBody : BaseResponse() {
    @SerializedName("invoiceList")
    var invoiceList: MutableList<InvoiceVO> ?= mutableListOf()
    @SerializedName("bannerImageList")
    var bannerImageList: MutableList<BannerVO> ?= mutableListOf()

    @SerializedName("returnUrl")
    var ticketUrl : String ?= ""
}