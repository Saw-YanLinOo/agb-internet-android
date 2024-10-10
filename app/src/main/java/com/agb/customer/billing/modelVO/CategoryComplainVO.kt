package com.agb.customer.billing.modelVO

import com.google.gson.annotations.SerializedName

class CategoryComplainVO {
    @SerializedName("id")
    var id: Int? = 0

    @SerializedName("categoryName")
    var categoryName: String? = ""

    @SerializedName("status")
    var status: Int? = 0

}