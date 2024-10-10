package com.agb.customer.billing.views

import com.agb.customer.billing.networks.responses.LoginResponse

interface ResetPasswordView : BaseView {
    fun setData(response: LoginResponse)
}