package ru.orlovegor.moviesearchapp.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteMovie(
    @Json(name = "imdbID")
    val id: String,
    @Json(name = "Title")
    val title: String,
    @Json(name = "Type")
    val movieType: MovieTypes,
    @Json(name = "Poster")
    val imageUrl: String?
)

fun RemoteMovie.mapToMovie() =
    Movie(
        id = this.id,
        title = this.title,
        movieType = this.movieType,
        imageUrl = this.imageUrl
    )

fun RemoteMovie.mapToLocalMovie() =
    LocalMovie(
        id = this.id,
        title = this.title,
        movieType = this.movieType,
        imageUrl = this.imageUrl
    )










