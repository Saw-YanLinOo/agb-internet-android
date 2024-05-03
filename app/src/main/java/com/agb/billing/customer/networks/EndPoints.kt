package com.agb.billing.customer.networks

object EndPoints {

    //Payment
    const val PAYMENT_URL = "payment/make_payment"

    //API old Uat URL
//    const val BASE_URL = "https://uat6.advent-soft.com/agb_billing/api/"


    //api new Uat url
//    const val BASE_URL = "http://10.103.0.78/billing_api/api/"
//    var BASE_URL = "http://10.103.0.64:8080/AGBBilling_API/api/"
//    const val BASE_URL = "http://121.54.165.124:8080/billing_api/api/"

//    const val BASE_URL = "http://121.54.167.110/agb_billing/api/"

    //API Prod URL
    const val BASE_URL = "https://app.agbcommunication.com/api/api/"

    var BASE_TICKET_URL = ""

    const val LOGIN_URL = "customer-login"
    const val LOGOUT_URL = "customer-logout"
    const val INVOICE_LIST_URL = "customer/invoice"
    const val RECEIPT_LIST_URL = "customer/receipt"
    const val PROFILE_URL = "profile-info"
    const val RESET_PASSWORD = "reset-password"
    const val ACTIVE_PLAN_URL = "plan/active-plan"
    const val PENDING_PLAN_URL = "plan/pending-plan"
    const val CHANGE_PLAN_PRELOAD = "plan/change-plan-preload"
    const val PLAN_BY_BANDWIDTH = "plan/get-plan-by-bandwidth"
    const val CHANGE_PLAN = "plan/change-plan"
    const val CANCEL_PLAN = "plan/cancel-plan"
    const val INVOICE_DETAIL = "customer/invoice-detail"
    const val HOME_URL = "home"
    const val TNC = "tnc"
    const val SUPPORT = "support"
    const val PROFILE_UPDATE_URL = "customer/updateProfile"
    const val TICKET_LIST_URL = "ticket/list.json"
    const val COMPLAIN_PRELOAD_URL = "complain/preload-complain"
    const val TICKET_CREATE_URL = "ticket/createOrUpdate.json"
    const val PAYMENT_METHOD = "payment-type-list"
    const val CB_PAYMENT_DOWNLOAD_QR = "payment/downloadQR"
    const val NOTIFICATION_URL = "notification/getNotificationList"
}