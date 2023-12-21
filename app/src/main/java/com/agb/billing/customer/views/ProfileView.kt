package com.agb.billing.customer.views

import com.agb.billing.customer.networks.responses.ProfileResponse
import com.agb.billing.customer.networks.responses.SuccessResponse

interface ProfileView : BaseView {
    fun setData(response: ProfileResponse)

    fun setUpdateSuccess(response : SuccessResponse)
}