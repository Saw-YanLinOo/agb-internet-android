package com.agb.customer.billing.modelVO

import com.google.gson.annotations.SerializedName

class ComplainVO(
    @SerializedName("ticketId")
    var ticketId: String? = "",

    @SerializedName("ticketNumber")
    var ticketNumber: String? = "",

    @SerializedName("serviceId")
    var serviceId: String? = "",

    @SerializedName("complainDate")
    var complainDate: String? = "",

    @SerializedName("ticketStatus")
    var ticketStatus: String? = "",

    @SerializedName("ticketStatusDesc")
    var ticketStatusDesc: String? = "",

    @SerializedName("complainMessage")
    var complainMessage: String? = "",

    @SerializedName("activePlanId")
    var activePlanId: String? = "",

    @SerializedName("activePlanText")
    var activePlanText: String? = "",

    @SerializedName("complainCategoryId")
    var complainCategoryId: String? = "",

    @SerializedName("complainCategoryText")
    var complainCategoryText: String? = "",

    @SerializedName("complainPhoto")
    var complainPhoto: String? = "",

    @SerializedName("callCenterAnswer")
    var callCenterAnswer: String? = "",

    @SerializedName("customerId")
    var customerId: Int? = 0,

    @SerializedName("customerUid")
    var customerUid: String? = "",

    @SerializedName("userName")
    var userName: String? = "",


    )