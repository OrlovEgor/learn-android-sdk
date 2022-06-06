package ru.orlovegor.notificationapp.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.RingtoneManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat

object NotificationChannels {

    const val CHAT_MESSAGE_ID = "chat"
    const val PROMOTION_ID = "promotion"

    fun createChannels(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChatMessageChannel(context)
            createPromotionChannel(context)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChatMessageChannel(context: Context) {
        val name = "Messages"
        val channelDescription = "Urgent messages"
        val priority = NotificationManager.IMPORTANCE_HIGH

        val channel = NotificationChannel(CHAT_MESSAGE_ID, name, priority).apply {
            description = channelDescription
            setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION), null)
        }
        NotificationManagerCompat.from(context).createNotificationChannel(channel)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createPromotionChannel(context: Context) {
        val name = "Promotion"
        val channelDescription = "New promo"
        val priority = NotificationManager.IMPORTANCE_LOW
        val channel = NotificationChannel(PROMOTION_ID, name, priority).apply {
            description = channelDescription
        }
        NotificationManagerCompat.from(context).createNotificationChannel(channel)
    }

}

