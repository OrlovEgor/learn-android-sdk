package ru.orlovegor.moviesearchapp.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteMovie(
    @Json(name = "imdbID" )
    val id: String,
    @Json(name = "Title")
    val title: String,
    @Json(name = "Poster")
    val image: String?
    )





