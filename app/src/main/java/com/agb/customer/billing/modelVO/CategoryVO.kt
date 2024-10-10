package com.agb.customer.billing.modelVO

import com.agb.customer.billing.R
import com.google.gson.annotations.SerializedName

class CategoryVO {
    @SerializedName("id")
    var id : Int ?= 0
    @SerializedName("image")
    var image : Int = R.drawable.sample_banner
    @SerializedName("title")
    var title : String ?= ""

}