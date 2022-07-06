package ru.orlovegor.moviesearchapp.data.models

data class Movie(
    val id: String,
    val title: String,
    val movieType: MovieTypes,
    val imageUrl: String?
)
fun Movie.mapToLocalMovie() =
    LocalMovie(
        id = this.id,
        title = this.title,
        movieType = this.movieType,
        imageUrl = this.imageUrl
    )
