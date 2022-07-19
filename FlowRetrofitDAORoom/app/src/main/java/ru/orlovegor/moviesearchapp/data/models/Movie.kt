package ru.orlovegor.moviesearchapp.data.models

data class Movie(
    val id: String,
    val title: String,
    val movieType: MovieTypes,
    val imageUrl: String?
)

