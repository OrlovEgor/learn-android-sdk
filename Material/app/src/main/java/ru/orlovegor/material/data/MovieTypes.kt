package ru.orlovegor.material.data

import com.squareup.moshi.Json

enum class MovieTypes {
    @Json(name = "movie")
    MOVIE ,
    @Json(name = "series")
    SERIES,
    @Json(name = "episode")
    EPISODE
}