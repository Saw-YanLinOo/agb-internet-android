package com.agb.billing.customer.views

import com.agb.billing.customer.networks.responses.LoginResponse

interface ResetPasswordView : BaseView {
    fun setData(response: LoginResponse)
}