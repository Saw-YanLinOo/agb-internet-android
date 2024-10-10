package com.agb.customer.billing.modelVO

import com.google.gson.annotations.SerializedName

class PendingPlanVO {
    @SerializedName("id")
    var id : Int ?= 0
    @SerializedName("specialcode")
    var specialcode : String ?= ""
    @SerializedName("planname")
    var planname : String ?= ""
    @SerializedName("packagename")
    var packagename : String ?= ""
    @SerializedName("duration")
    var duration : String ?= ""
    @SerializedName("bandwidth")
    var bandwidth : String ?= ""
    @SerializedName("totalprice")
    var totalprice : String ?= ""
    @SerializedName("totalpriceDesc")
    var totalpriceDesc : String ?= ""
    @SerializedName("pendingrequestdate")
    var pendingrequestdate : String ?= ""
    @SerializedName("pendingplanid")
    var pendingplanid : Int ?= 0
}