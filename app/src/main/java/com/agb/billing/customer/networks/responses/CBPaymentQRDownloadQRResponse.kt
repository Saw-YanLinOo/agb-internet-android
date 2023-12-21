package com.agb.billing.customer.networks.responses

import com.agb.billing.customer.modelVO.CBPaymentVO
import com.agb.billing.customer.modelVO.InvoiceDetailVO
import com.google.gson.annotations.SerializedName

class CBPaymentQRDownloadQRResponse : BaseResponse() {
    @SerializedName("data")
    var data: CBPaymentVO ?= null
}