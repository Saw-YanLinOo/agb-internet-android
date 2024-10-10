package com.agb.customer.billing.views

import com.agb.customer.billing.networks.responses.ProfileResponse
import com.agb.customer.billing.networks.responses.SuccessResponse

interface ProfileView : BaseView {
    fun setData(response: ProfileResponse)

    fun setUpdateSuccess(response : SuccessResponse)
}