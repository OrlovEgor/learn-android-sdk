package ru.orlovegor.notificationapp.data

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import ru.orlovegor.notificationapp.ui.Notifications

class MessagingService : FirebaseMessagingService() {

    private val coroutineJob = SupervisorJob()

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d("Message", "send message")
        CoroutineScope(coroutineJob).launch {
            val result =
                message.data["type"]?.let { type ->
                    message.data["data"]?.let { data ->
                        Repository().parseNotification(type, data)
                    }
                }
            when (result) {
                is NotificationMessages.ChatMessage -> Notifications.showMessageNotification(
                    result,
                    this@MessagingService
                )
                is NotificationMessages.NewPromotions -> Notifications.showPromotionNotification(
                    result,
                    this@MessagingService
                )
                null -> return@launch
            }
        }
    }

    override fun onDestroy() {
        coroutineJob.cancel()
        super.onDestroy()
    }

}

