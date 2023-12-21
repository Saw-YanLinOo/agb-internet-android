package com.agb.billing.customer.modelVO

import com.google.gson.annotations.SerializedName

class PlanListVO {
    @SerializedName("id")
    var id : Int ?= 0
    @SerializedName("planname")
    var planname : String ?= ""
    @SerializedName("duration")
    var duration : String ?= ""
    @SerializedName("bandwidth")
    var bandwidth : String ?= ""
    @SerializedName("totalprice")
    var totalprice : Int ?= 0
    @SerializedName("totalpriceDesc")
    var totalpriceDesc : String ?= ""
    @SerializedName("enddate")
    var enddate : String ?= ""
}