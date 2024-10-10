package com.agb.customer.billing.networks.responses

import com.agb.customer.billing.modelVO.CBPaymentVO
import com.agb.customer.billing.modelVO.InvoiceDetailVO
import com.google.gson.annotations.SerializedName

class CBPaymentQRDownloadQRResponse : BaseResponse() {
    @SerializedName("data")
    var data: CBPaymentVO ?= null
}