package com.agb.customer.billing.modelVO

import com.google.gson.annotations.SerializedName

class TicketVO {
    @SerializedName("ticketId")
    var ticketId: Int? = 0

    @SerializedName("customerId")
    var customerId: Int? = 0

    @SerializedName("customerUid")
    var customerUid: String? = ""

    @SerializedName("userName")
    var userName: String? = ""

    @SerializedName("serviceId")
    var serviceId: String? = ""
}