package com.agb.customer.billing.networks.requests

import com.google.gson.annotations.SerializedName

class TicketCreateUpdateRequest(

    @SerializedName("ticketId")
    var ticketId: String? = "", //list data

    @SerializedName("customerId")
    var customerId: String? = "",//login

    @SerializedName("customerUid")
    var customerUid: String? = "", //login

    @SerializedName("userName")
    var userName: String? = "", //login

    @SerializedName("serviceId")
    var serviceId: String? = "",//list data

    @SerializedName("activePlanId")
    var activePlanId: String? = "",//current sc

    @SerializedName("activePlanText")
    var activePlanText: String? = "",//current sc

    @SerializedName("complainCategoryId")
    var complainCategoryId: String? = "",//current sc

    @SerializedName("complainCategoryText")
    var complainCategoryText: String? = "",//current sc

    @SerializedName("complainMessage")
    var complainMessage: String? = "",//current sc

    @SerializedName("complainPhoto")
    var complainPhoto: String? = "",//current sc

    @SerializedName("sessionId")
    var sessionId: String? = "",


    )