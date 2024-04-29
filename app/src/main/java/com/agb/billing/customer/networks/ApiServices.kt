package com.agb.billing.customer.networks

import com.agb.billing.customer.networks.EndPoints.ACTIVE_PLAN_URL
import com.agb.billing.customer.networks.EndPoints.CANCEL_PLAN
import com.agb.billing.customer.networks.EndPoints.CB_PAYMENT_DOWNLOAD_QR
import com.agb.billing.customer.networks.EndPoints.CHANGE_PLAN
import com.agb.billing.customer.networks.EndPoints.CHANGE_PLAN_PRELOAD
import com.agb.billing.customer.networks.EndPoints.COMPLAIN_PRELOAD_URL
import com.agb.billing.customer.networks.EndPoints.HOME_URL
import com.agb.billing.customer.networks.EndPoints.INVOICE_DETAIL
import com.agb.billing.customer.networks.EndPoints.INVOICE_LIST_URL
import com.agb.billing.customer.networks.EndPoints.PAYMENT_URL
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import com.agb.billing.customer.networks.EndPoints.LOGIN_URL
import com.agb.billing.customer.networks.EndPoints.LOGOUT_URL
import com.agb.billing.customer.networks.EndPoints.NOTIFICATION_URL
import com.agb.billing.customer.networks.EndPoints.PAYMENT_METHOD
import com.agb.billing.customer.networks.EndPoints.PENDING_PLAN_URL
import com.agb.billing.customer.networks.EndPoints.PLAN_BY_BANDWIDTH
import com.agb.billing.customer.networks.EndPoints.PROFILE_UPDATE_URL
import com.agb.billing.customer.networks.EndPoints.PROFILE_URL
import com.agb.billing.customer.networks.EndPoints.RECEIPT_LIST_URL
import com.agb.billing.customer.networks.EndPoints.RESET_PASSWORD
import com.agb.billing.customer.networks.EndPoints.SUPPORT
import com.agb.billing.customer.networks.EndPoints.TICKET_CREATE_URL
import com.agb.billing.customer.networks.EndPoints.TICKET_LIST_URL
import com.agb.billing.customer.networks.EndPoints.TNC
import com.agb.billing.customer.networks.requests.*
import com.agb.billing.customer.networks.responses.*

interface ApiServices {

    @POST(LOGIN_URL)
    fun getLogin(@Body req: LoginRequest): Call<LoginResponse>

    @POST(LOGOUT_URL)
    fun getLogout() : Call<EmptyResponse>

    @POST(INVOICE_LIST_URL)
    fun getInvoiceList(@Body req: InvoiceListRequest): Call<InvoiceListResponse>

    @POST(RECEIPT_LIST_URL)
    fun getReceiptList(@Body req: ReceiptListRequest): Call<ReceiptListResponse>

    @POST(PROFILE_URL)
    fun getProfile(): Call<ProfileResponse>

    @POST(RESET_PASSWORD)
    fun getResetPassword(@Body req: ResetPasswordRequest): Call<LoginResponse>

    @POST(PAYMENT_URL)
    fun getKBZPayment(@Body req: KBZPaymentRequest): Call<KBZPaymentResponse>

    @POST(ACTIVE_PLAN_URL)
    fun getActivePlanList(@Body req: PaginationRequest): Call<ActivePlanListResponse>

    @POST(PENDING_PLAN_URL)
    fun getPendingPlanList(@Body req: PaginationRequest): Call<PendingPlanListResponse>

    @POST(CHANGE_PLAN_PRELOAD)
    fun getChangePlanPreload(@Body req: ChangePlanPreloadRequest): Call<ChangePlanPreloadResponse>

    @POST(PLAN_BY_BANDWIDTH)
    fun getPlanByBandWidth(@Body req: PlanByBandWidthRequest): Call<PlanByBandWidthResponse>

    @POST(CHANGE_PLAN)
    fun getChangePlan(@Body req: ChangePlanRequest): Call<SuccessResponse>

    @POST(CANCEL_PLAN)
    fun getCancelPlan(@Body req: CancelPlanRequest): Call<SuccessResponse>

    @POST(INVOICE_DETAIL)
    fun getInvoiceDetail(@Body req: InvoiceDetailRequest): Call<InvoiceDetailResponse>

    @POST(INVOICE_DETAIL)
    fun getReceiptDetail(@Body req: ReceiptDetailRequest): Call<InvoiceDetailResponse>

    @POST(HOME_URL)
    fun getHome(): Call<HomeResponse>

    @POST(TNC)
    fun getTNC(): Call<TNCResponse>

    @POST(SUPPORT)
    fun getSupport(): Call<SupportResponse>

    @POST(PROFILE_UPDATE_URL)
    fun getProfileUpdate(@Body req: UpdateProfileRequest): Call<SuccessResponse>

    @POST(TICKET_LIST_URL)
    fun getComplainList(@Body req: ComplainListRequest): Call<ComplainListResponse>

    @POST(COMPLAIN_PRELOAD_URL)
    fun getComplainPreLoad(@Body req: EmptyRequest): Call<ComplainPreloadResponse>

    @POST(TICKET_CREATE_URL)
    fun uploadComplain(@Body req: TicketCreateUpdateRequest): Call<TicketResponse>

    @POST(PAYMENT_METHOD)
    fun getPaymentMethod(): Call<PaymentMethodResponse>

    @POST(PAYMENT_URL)
    fun ayaPayment(@Body req: PaymentRequest): Call<AYAPaymentResponse>

    @POST(PAYMENT_URL)
    fun cbPayment(@Body req: PaymentRequest): Call<CBPaymentResponse>

    @POST(CB_PAYMENT_DOWNLOAD_QR)
    fun cbPaymentQRDownload(@Body req: CBPaymentQRDownloadRequest): Call<CBPaymentQRDownloadQRResponse>

    @POST(NOTIFICATION_URL)
    fun getNotificationList(@Body req: NotificationRequest): Call<NotificationResponse>
}