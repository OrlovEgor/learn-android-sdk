package ru.orlovegor.moviesearchapp.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import ru.orlovegor.moviesearchapp.R
import ru.orlovegor.moviesearchapp.data.MovieTypes
import ru.orlovegor.moviesearchapp.data.RemoteMovie
import ru.orlovegor.moviesearchapp.data.Repository

class MovieViewModel : ViewModel() {
    private val repository = Repository()
    private var scope: CoroutineScope? = null

    private val _toast = MutableLiveData<Int>()
    private val _listMovie = MutableLiveData<List<RemoteMovie>>()

    val toast: LiveData<Int>
        get() = _toast
    val listMovie: LiveData<List<RemoteMovie>>
        get() = _listMovie


        fun bind(queryFlow: Flow<String>, movieTypeFlow: Flow<MovieTypes>) {
        scope = CoroutineScope(Dispatchers.IO )
        combine(
            queryFlow,
            movieTypeFlow
        )
        { query, type -> query to type }
            .debounce(2000)
            .onEach { Log.d("TAG", "${it.first}, ${it.second.name}") }
            .flowOn(Dispatchers.IO)
            .mapLatest {
                _listMovie.postValue(repository.getMovie(it.first, it.second.name.lowercase())) }
            .catch {
                Log.d("TAG", "errorBind2 = $it}")
                _toast.postValue(R.string.input_error)
            }
            .launchIn(scope!!)

    }

    fun cancelJob() {
        scope?.cancel()
    }
}