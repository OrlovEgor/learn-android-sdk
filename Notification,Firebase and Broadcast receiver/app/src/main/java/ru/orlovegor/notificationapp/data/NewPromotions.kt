package ru.orlovegor.notificationapp.data


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NewPromotions(
    @Json(name = "tittle")
    val tittle: String,
    @Json(name = "description")
    val description: String,
    @Json(name = "imageUrl")
    val imageUrl: String?
)
