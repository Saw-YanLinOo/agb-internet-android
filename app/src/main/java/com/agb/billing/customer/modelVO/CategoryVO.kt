package com.agb.billing.customer.modelVO

import com.agb.billing.customer.R
import com.google.gson.annotations.SerializedName

class CategoryVO {
    @SerializedName("id")
    var id : Int ?= 0
    @SerializedName("image")
    var image : Int = R.drawable.sample_banner
    @SerializedName("title")
    var title : String ?= ""

}