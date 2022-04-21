package ru.orlovegor.notificationapp.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.RingtoneManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat

object NotificationChannels {

    fun createChannels(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createHighPriorityChannel(context)
            createLowPriorityChannel(context)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createHighPriorityChannel(context: Context) {
        val name = "Messages"
        val channelDescription = "Urgent messages"
        val priority = NotificationManager.IMPORTANCE_HIGH

        val channel = NotificationChannel(HIGH_PRIORITY_ID, name,priority).apply {
            description = channelDescription
            setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION), null)
        }
        NotificationManagerCompat.from(context).createNotificationChannel(channel)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createLowPriorityChannel(context: Context) {
        val name = "Promotion"
        val channelDescription = "New promo"
        val priority = NotificationManager.IMPORTANCE_LOW

        val channel = NotificationChannel(LOW_PRIORITY_ID, name,priority).apply {
            description = channelDescription
        }
        NotificationManagerCompat.from(context).createNotificationChannel(channel)
    }

    private const val HIGH_PRIORITY_ID = "high"
    private const val LOW_PRIORITY_ID = "low"

    }

