package ru.orlovegor.material.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ru.orlovegor.material.data.RemoteMovie
import ru.orlovegor.material.data.Repository

class FirstViewModel: ViewModel() {

    private val repo = Repository()
    private val _movies = MutableSharedFlow<List<RemoteMovie>>()
    val movies = _movies.asSharedFlow()

    fun getMovies(){
        viewModelScope.launch {
            _movies.emit(
            repo.fetchMovie("star")
            )
        }
    }
}