package ru.orlovegor.moviesearchapp.presentation

import androidx.lifecycle.ViewModel

import kotlinx.coroutines.flow.*
import ru.orlovegor.moviesearchapp.data.Repository
import ru.orlovegor.moviesearchapp.data.models.Movie
import ru.orlovegor.moviesearchapp.data.models.mapToMovie

class LocalMovieViewModel: ViewModel() {

    private val repo = Repository()

    val listMovie: Flow<List<Movie>> = repo.getAllLocalMovie().map { it.map{it.mapToMovie()} }



}