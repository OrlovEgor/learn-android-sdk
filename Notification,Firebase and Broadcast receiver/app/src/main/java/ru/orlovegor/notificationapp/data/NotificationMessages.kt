package ru.orlovegor.notificationapp.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

 sealed class NotificationMessages {
    @JsonClass(generateAdapter = true)
    data class ChatMessage(
        @Json(name = "userId")
        val userID: Long,
        @Json(name = "userName")
        val userName: String,
        @Json(name = "created_at")
        val created_at: Long,
        @Json(name = "text")
        val text: String
    ) :NotificationMessages()

    @JsonClass(generateAdapter = true)
    data class NewPromotions(
        @Json(name = "tittle")
        val tittle: String,
        @Json(name = "description")
        val description: String,
        @Json(name = "imageUrl")
        val imageUrl: String?
    ):NotificationMessages()

}
