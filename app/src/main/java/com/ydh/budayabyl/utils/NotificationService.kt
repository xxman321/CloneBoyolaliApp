package com.ydh.budayabyl.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.ydh.budayabyl.R

class NotificationService : FirebaseMessagingService() {
    private val localSession by lazy { SharedPrefHelper(applicationContext) }


    override fun onNewToken(notifToken: String) {
        localSession.notifToken = notifToken
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        showNotification(remoteMessage.data)
    }

    private fun showNotification(data: MutableMap<String, String>) {

        Log.e("Notification", "showNotification: $data" )

        val title = data["title"] ?: ""
        val body = data["body"] ?: ""
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                Constant.NOTIFICATION_CHANNEL_ID,
                Constant.NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager?.createNotificationChannel(channel)
        }
        val notification = NotificationCompat.Builder(this, Constant.NOTIFICATION_CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(body)
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText(body))
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setColor(Color.WHITE)
            .setAutoCancel(true)
            .build()

        notificationManager?.notify(Constant.NOTIFICATION_ID, notification)
    }
}