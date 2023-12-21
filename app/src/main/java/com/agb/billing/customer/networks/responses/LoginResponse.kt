package com.agb.billing.customer.networks.responses

import com.agb.billing.customer.modelVO.UserVO
import com.google.gson.annotations.SerializedName

class LoginResponse : BaseResponse() {
    @SerializedName("data")
    var data: UserVO ?= null
}