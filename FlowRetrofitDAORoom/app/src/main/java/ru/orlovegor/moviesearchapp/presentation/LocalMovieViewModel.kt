package ru.orlovegor.moviesearchapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import ru.orlovegor.moviesearchapp.data.Repository
import ru.orlovegor.moviesearchapp.data.models.mapToMovie

class LocalMovieViewModel : ViewModel() {

    private val repo = Repository()

    val listMovie = repo.getAllLocalMovie().map { list -> list.map { movie -> movie.mapToMovie() } }
        .shareIn(viewModelScope, started = SharingStarted.Lazily, replay = 1)
}