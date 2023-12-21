package com.agb.billing.customer.networks.responses

import com.agb.billing.customer.modelVO.ProfileVO
import com.google.gson.annotations.SerializedName

class ProfileResponse : BaseResponse() {
    @SerializedName("data")
    var data: ProfileVO ?= null
}