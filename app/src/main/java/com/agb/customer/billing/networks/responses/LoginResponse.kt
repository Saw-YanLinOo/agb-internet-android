package com.agb.customer.billing.networks.responses

import com.agb.customer.billing.modelVO.UserVO
import com.google.gson.annotations.SerializedName

class LoginResponse : BaseResponse() {
    @SerializedName("data")
    var data: UserVO ?= null
}