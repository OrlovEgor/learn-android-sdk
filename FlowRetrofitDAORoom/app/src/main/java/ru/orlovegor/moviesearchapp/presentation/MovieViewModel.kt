package ru.orlovegor.moviesearchapp.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import ru.orlovegor.moviesearchapp.R
import ru.orlovegor.moviesearchapp.data.*
import ru.orlovegor.moviesearchapp.data.models.Movie
import ru.orlovegor.moviesearchapp.data.models.MovieTypes
import ru.orlovegor.moviesearchapp.data.models.mapToLocalMovie
import ru.orlovegor.moviesearchapp.data.models.mapToMovie
import ru.orlovegor.moviesearchapp.utils.ResultWrapper

class MovieViewModel : ViewModel() {
    private var scope: CoroutineScope? = null
    private val repository = Repository()

    private val _toast = MutableSharedFlow<Int>()
    private val _listMovie: MutableStateFlow<List<Movie>> = MutableStateFlow(listOf())
    private val _isProgress = MutableStateFlow(false)

    val toast = _toast.asSharedFlow()
    val listMovie = _listMovie.asStateFlow()
    val isProgress = _isProgress.asStateFlow()

    @FlowPreview
    @ExperimentalCoroutinesApi
    fun bind(queryFlow: Flow<String>, movieTypeFlow: Flow<MovieTypes>) {

        scope = CoroutineScope(Dispatchers.Main) + SupervisorJob()
        combine(
            queryFlow,
            movieTypeFlow
        )
        { query, type -> query to type }
            .onEach { _isProgress.value = true }
            .debounce(1000)
            .mapLatest { it ->
                when (val data = repository.fetchMovie(it.first, it.second.name.lowercase())) {
                    is ResultWrapper.Success -> {
                        Log.d("TAG", " ${data.value}")
                        _listMovie.emit((data.value.map { it.mapToMovie() }))
                        repository.saveMovie(data.value.map { it.mapToLocalMovie() })
                    }
                    is ResultWrapper.NetworkError -> {
                        _listMovie.emit((repository.getLocalMovie(it.first, it.second)
                            .map { it.mapToMovie() }))
                        _toast.emit(R.string.connect_error)
                        Log.d("TAG", "NETWORK ERROR")
                    }
                    is ResultWrapper.Error -> {
                        Log.d("TAG", "ERROR -> ${data.exception?.message}")
                        _toast.emit(R.string.input_error)
                    }
                }
            }
            .onEach { _isProgress.value = false }
            .flowOn(Dispatchers.IO)
            .launchIn(scope!!)
    }

    fun cancelJob() {
        scope?.cancel()
    }
}