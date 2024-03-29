package ru.orlovegor.material.networking

import com.squareup.moshi.FromJson
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.orlovegor.material.data.MovieTypes
import ru.orlovegor.material.data.RemoteMovie

class MovieParseAdapter {

    @FromJson
    fun fromJson(search: Search): List<RemoteMovie> =
        search.listMovie.map { movie ->
            RemoteMovie(
                movie.id,
                movie.title,
                movie.movieType,
                movie.image
            )
        }

    @JsonClass(generateAdapter = true)
    data class Movie(
        @Json(name = "imdbID")
        val id: String,
        @Json(name = "Title")
        val title: String,
        @Json(name = "Type")
        val movieType: MovieTypes,
        @Json(name = "Poster")
        val image: String?
    )

    @JsonClass(generateAdapter = true)
    data class Search(
        @Json(name = "Search")
        val listMovie: List<Movie>
    )
}
