package com.agb.billing.customer.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.Intent.*
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.core.app.NotificationCompat
import com.agb.billing.customer.R
import com.agb.billing.customer.activities.MainActivity
import com.agb.billing.customer.events.PaymentStatusEvent
import com.agb.billing.customer.modelVO.PaymentNotificationVO
import com.agb.billing.customer.utils.Constants
import com.agb.billing.customer.utils.PreferenceUtils
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import org.greenrobot.eventbus.EventBus
import java.util.*

class PushNotificationService : FirebaseMessagingService() {

    var invoiceNumber: String? = ""

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        Log.d("NEW_TOKEN", p0)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        if (remoteMessage.data.isNotEmpty()) {
            val id = 1

            val notiId = remoteMessage.data["id"]
            val title = remoteMessage.data["title"]
            val messageBody = remoteMessage.data["message"]
            val messageBodyOnly = remoteMessage.data["body"]
            val notificationType = remoteMessage.data["notificationType"]
            val paymentStatus = remoteMessage.data["paymentStatus"]
            val paymentStatusDesc = remoteMessage.data["paymentStatusDesc"]
            invoiceNumber = remoteMessage.data["invNumber"]

            val mData = PaymentNotificationVO()
            mData.id = notiId?.toInt()
            mData.title = title
            mData.message = messageBody.toString()
            mData.body = messageBodyOnly
            mData.notificationType = notificationType
            mData.paymentStatus = paymentStatus
            mData.invNumber = invoiceNumber
            mData.paymentStatusDesc = paymentStatusDesc
            Log.e("SAMPLE_NOTI", Gson().toJson(mData))
            when (notificationType) {
                "1" -> {//New Invoice
                    PreferenceUtils.setNotiData(mData)
                }
                "2" -> {//PaymentStatus
                    Log.e("SAMPLE_NOTI", "2")
                    PreferenceUtils.setNotiData(mData)
                    EventBus.getDefault().post(PaymentStatusEvent(mData))
                }
                "3" -> {//Change Plan
                    PreferenceUtils.setNotiData(mData)
                }
                "4" -> {//Announcement

                }
            }
            sendNotification(id, title.toString(), messageBody.toString(), notificationType)

        }

    }

    private fun sendNotification(
        id: Int,
        messageTitle: String? = "",
        messageBody: String? = "",
        notiType: String? = null,
    ) {

        val intent = Intent(this, MainActivity::class.java)

        when (notiType) {
            "2" -> {//paymentStatus
                intent.putExtra(Constants.NOTI_TYPE, Constants.NOTI_TYPE_2)
                intent.putExtra(Constants.NOTI_DATA, invoiceNumber)
            }
            "1" -> {
                intent.putExtra(Constants.NOTI_TYPE, Constants.NOTI_TYPE_1)
                intent.putExtra(Constants.NOTI_DATA, messageBody)
            }
        }
        intent.putExtra(Constants.FROM_NOTI, Constants.NOTIFICATION)

          intent.flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK

//        val pendingIntent = PendingIntent.getActivity(
//            this, id, intent,
//            PendingIntent.FLAG_UPDATE_CURRENT
//        )

        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getActivity(
                this,
                id,
                intent,
                PendingIntent.FLAG_MUTABLE
            )
        } else {
            PendingIntent.getActivity(
                this,
                id,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        }

        val channelId = getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(messageTitle)
            .setContentText(messageBody)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setBadgeIconType(NotificationCompat.BADGE_ICON_LARGE)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .setSummaryText(messageTitle)
                    .bigText(messageBody)
            )
            .setNumber(1)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setContentIntent(pendingIntent)
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(
            Random().nextInt() /* 0 ID of notification */,
            notificationBuilder.build()
        )


    }

}