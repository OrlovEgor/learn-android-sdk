package ru.orlovegor.notificationapp.ui

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import ru.orlovegor.notificationapp.MainActivity
import ru.orlovegor.notificationapp.R
import ru.orlovegor.notificationapp.data.NotificationMessages
import ru.orlovegor.notificationapp.utils.loadBitmapWithGlide

object Notifications {

    private const val PROMO_NOTIFICATION_ID = 1

     fun showMessageNotification(message: NotificationMessages.ChatMessage, context: Context) {
        val notification = NotificationCompat.Builder(context, NotificationChannels.CHAT_MESSAGE_ID)
            .setContentTitle(Resources.getSystem().getString(R.string.content_tittle_message) + message.userName)
            .setContentText(Resources.getSystem().getString(R.string.content_text_message) + message.text)
            .setSmallIcon(R.drawable.ic_message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setContentIntent(createPendingIntent(context))
            .build()
        NotificationManagerCompat.from(context)
            .notify(message.userID.toInt(), notification)
    }

     fun showPromotionNotification(promotion: NotificationMessages.NewPromotions, context: Context) {
        val notification = NotificationCompat.Builder(context, NotificationChannels.PROMOTION_ID)
            .setContentTitle(promotion.tittle)
            .setContentText(promotion.description)
            .setSmallIcon(R.drawable.ic_emotions_24)
            .setLargeIcon(loadBitmapWithGlide(promotion.imageUrl ?: "", context))
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setContentIntent(createPendingIntent(context))
            .build()
        NotificationManagerCompat.from(context)
            .notify(PROMO_NOTIFICATION_ID, notification)
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun createPendingIntent(context: Context) = PendingIntent.getActivity(
        context, 123, Intent(context, MainActivity::class.java), 0
    )
}