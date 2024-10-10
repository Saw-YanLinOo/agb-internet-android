package com.agb.customer.billing.networks.responses

import com.agb.customer.billing.modelVO.ProfileVO
import com.google.gson.annotations.SerializedName

class ProfileResponse : BaseResponse() {
    @SerializedName("data")
    var data: ProfileVO ?= null
}