package ru.orlovegor.material.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteMovie(
    @Json(name = "imdbID")
    val id: String,
    @Json(name = "Title")
    val title: String = "",
    @Json(name = "Type")
    val movieType: MovieTypes,
    @Json(name = "Poster")
    val imageUrl: String?
)
