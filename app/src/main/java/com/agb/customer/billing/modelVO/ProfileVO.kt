package com.agb.customer.billing.modelVO

import com.google.gson.annotations.SerializedName

class ProfileVO {
    @SerializedName("customerId")
    var customerId : Int ?= 0
    @SerializedName("uid")
    var uid : String ?= ""
    @SerializedName("username")
    var username : String ?= ""
    @SerializedName("fullname")
    var fullname : String ?= ""
    @SerializedName("phone")
    var phone : String ?= ""
    @SerializedName("viberno")
    var viberno : String ?= ""
    @SerializedName("email")
    var email : String ?= ""
    @SerializedName("memberid")
    var memberid : String ?= ""
    @SerializedName("activationdate")
    var activationdate : String ?= ""
    @SerializedName("address")
    var address : String ?= ""
    @SerializedName("alter_phnumbers")
    var alter_phnumbers : String ?= ""
    @SerializedName("balance")
    var balance : MutableList<BalanceVO> ?= mutableListOf()

}