package com.agb.billing.customer.views

import com.agb.billing.customer.networks.responses.LoginResponse

interface LoginView : BaseView {
    fun setData(response: LoginResponse)
}