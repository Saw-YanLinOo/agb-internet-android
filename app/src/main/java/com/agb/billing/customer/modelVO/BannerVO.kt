package com.agb.billing.customer.modelVO

import com.google.gson.annotations.SerializedName

class BannerVO {
    @SerializedName("id")
    var id : Int ?= 0
    @SerializedName("imagePath")
    var imagePath : String ?= ""

}