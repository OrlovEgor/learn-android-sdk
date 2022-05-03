package ru.orlovegor.notificationapp

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
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
        when (message.data["type"]) {
            NotificationType.MESSAGE.type -> message.data["data"]?.let {
                ParseFCMessage.Base().parseDate(it)
            }?.let { showMessageNotification(it) }

            NotificationType.PROMOTION.type ->
                message.data["data"]?.let {
                    ParseFCPromotion.Base().parseDate(it)
                        ?.let { showPromotionNotification(it) }
                }
        }

    }

    private fun showMessageNotification(message: ChatMessage) {
        val notification = NotificationCompat.Builder(this, NotificationChannels.CHAT_MESSAGE_ID)
            .setContentTitle(getString(R.string.content_tittle_message) + message.userName)
            .setContentText(getString(R.string.content_text_message) + message.text)
            .setSmallIcon(R.drawable.ic_message)
            .build()
        NotificationManagerCompat.from(this)
            .notify(message.userID.toInt(), notification)
    }

    private fun showPromotionNotification(promotion: NewPromotions) {
        val notification = NotificationCompat.Builder(this, NotificationChannels.PROMOTION_ID)
            .setContentTitle(promotion.tittle)
            .setContentText(promotion.description)
            .setSmallIcon(R.drawable.ic_emotions_24)
            .setLargeIcon(loadBitmapWithGlide(promotion.imageUrl?: ""))
            .build()
        NotificationManagerCompat.from(this)
            .notify(PROMO_NOTIFICATION_ID, notification)
    }

    private fun loadBitmapWithGlide(url: String): Bitmap? {
        var bitmap:Bitmap? = null
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

    companion object{
       private const val PROMO_NOTIFICATION_ID = 1
    }
}

