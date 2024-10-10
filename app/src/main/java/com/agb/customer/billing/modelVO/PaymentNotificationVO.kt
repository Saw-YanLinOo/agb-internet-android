package com.agb.customer.billing.modelVO

import com.google.gson.annotations.SerializedName

class PaymentNotificationVO {
    @SerializedName("id")
    var id : Int ?= 0
    @SerializedName("title")
    var title : String ?= ""
    @SerializedName("message")
    var message : String ?= ""
    @SerializedName("body")
    var body : String ?= ""
    @SerializedName("invNumber")
    var invNumber : String ?= ""
    @SerializedName("notificationType")
    var notificationType : String ?= ""
    @SerializedName("paymentStatus")
    var paymentStatus : String ?= ""
    @SerializedName("paymentStatusDesc")
    var paymentStatusDesc : String ?= ""

//    Notification Type
//    NEW_INVOICE(1, "New Invoice"), PAYMENT_STATUS(2, "Payment Status"), CHANGE_PLAN(3, "Change Plan"),ANNOUNCEMENT(4, "Announcement");
//    Payment Status
//    UNPAID(0, "UNPAID"), PAID(1, "PAID");
}