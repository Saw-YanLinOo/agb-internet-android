package com.agb.customer.billing.modelVO

import com.google.gson.annotations.SerializedName

data class PaymentVO(
    @SerializedName("paymentTypeId")
    var id: Int = 0,


    @SerializedName("name")
    var name: String? = null,

    @SerializedName("code")
    var code: String? = null,

    @SerializedName("iconPath")
    var iconImage: String? = null,

    var isChecked: Boolean = false,

    )
