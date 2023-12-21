package com.agb.billing.customer.modelVO

import com.google.gson.annotations.SerializedName

class UserVO {
    @SerializedName("customerId")
    var customerId : Int ?= 0
    @SerializedName("createdTime")
    var createdTime : String ?= ""
    @SerializedName("updatedTime")
    var updatedTime : String ?= ""
    @SerializedName("uid")
    var uid : String ?= ""
    @SerializedName("username")
    var username : String ?= ""
    @SerializedName("status")
    var status : Boolean ?= false
    @SerializedName("statusDesc")
    var statusDesc : String ?= ""
    @SerializedName("reset")
    var reset : String ?= ""
    @SerializedName("sessionId")
    var sessionId : String ?= ""
    @SerializedName("deviceType")
    var deviceType : Int ?= 0
    @SerializedName("deviceTypeDesc")
    var deviceTypeDesc : String ?= ""
    @SerializedName("appVersion")
    var appVersion : String ?= ""

}