package ru.orlovegor.notificationapp

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import ru.orlovegor.notificationapp.data.*
import ru.orlovegor.notificationapp.ui.NotificationChannels

class MessagingService : FirebaseMessagingService() {

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val result =
            message.data["type"]?.let { type ->
                message.data["data"]?.let { data ->
                    NotificationParser.Base().parseNotification(type, data)
                }
            }
        when (result) {
            is NotificationMessages.ChatMessage -> showMessageNotification(result)
            is NotificationMessages.NewPromotions -> showPromotionNotification(result)
            null -> return
        }
    }


    private fun showMessageNotification(message: NotificationMessages.ChatMessage) {
        val notification = NotificationCompat.Builder(this, NotificationChannels.CHAT_MESSAGE_ID)
            .setContentTitle(getString(R.string.content_tittle_message) + message.userName)
            .setContentText(getString(R.string.content_text_message) + message.text)
            .setSmallIcon(R.drawable.ic_message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setContentIntent(createPendingIntent())
            .build()
        NotificationManagerCompat.from(this)
            .notify(message.userID.toInt(), notification)
    }

    private fun showPromotionNotification(promotion: NotificationMessages.NewPromotions) {
        val notification = NotificationCompat.Builder(this, NotificationChannels.PROMOTION_ID)
            .setContentTitle(promotion.tittle)
            .setContentText(promotion.description)
            .setSmallIcon(R.drawable.ic_emotions_24)
            .setLargeIcon(loadBitmapWithGlide(promotion.imageUrl ?: ""))
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setContentIntent(createPendingIntent())
            .build()
        NotificationManagerCompat.from(this)
            .notify(PROMO_NOTIFICATION_ID, notification)
    }

    private fun createPendingIntent() = PendingIntent.getActivity(this, 123, Intent(this, MainActivity::class.java), 0)

    @SuppressLint("CheckResult")
    private fun loadBitmapWithGlide(url: String): Bitmap? {
        var bitmap: Bitmap? = null
        Glide.with(this)
            .asBitmap()
            .placeholder(R.drawable.ic_error_24)
            .error(R.drawable.ic_error_24)
            .load(url)
            .listener(object : RequestListener<Bitmap> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Bitmap?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    bitmap = resource
                    return false
                }
            })
        return bitmap
    }

    companion object {
        private const val PROMO_NOTIFICATION_ID = 1
    }
}

