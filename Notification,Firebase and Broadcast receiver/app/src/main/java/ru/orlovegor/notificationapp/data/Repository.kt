package ru.orlovegor.notificationapp.data

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository {

   suspend fun parseNotification(type: String, data: String): NotificationMessages? {
      return withContext(Dispatchers.IO) {
           val moshi = Moshi.Builder().build()
           val chatMessageAdapter: JsonAdapter<NotificationMessages.ChatMessage> =
               moshi.adapter(NotificationMessages.ChatMessage::class.java)
           val newPromotionsAdapter: JsonAdapter<NotificationMessages.NewPromotions> =
               moshi.adapter(NotificationMessages.NewPromotions::class.java)
           return@withContext try {
               when (type) {
                   NotificationType.MESSAGE.type ->
                       chatMessageAdapter.fromJson(data) as NotificationMessages
                   NotificationType.PROMOTION.type ->
                       newPromotionsAdapter.fromJson(data) as NotificationMessages
                   else -> null
               }
           } catch (t: Throwable) {
               return@withContext null
           }
       }
    }
}

