package com.agb.billing.customer.views

import com.agb.billing.customer.networks.responses.CBPaymentQRDownloadQRResponse
import com.agb.billing.customer.networks.responses.ProfileResponse
import com.agb.billing.customer.networks.responses.SuccessResponse

interface CBPayInformationView : BaseView {
    fun setPaymentQRResponse(response: CBPaymentQRDownloadQRResponse)
}