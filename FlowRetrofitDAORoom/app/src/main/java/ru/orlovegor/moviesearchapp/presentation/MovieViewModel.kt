package ru.orlovegor.moviesearchapp.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import ru.orlovegor.moviesearchapp.R
import ru.orlovegor.moviesearchapp.data.*
import ru.orlovegor.moviesearchapp.data.models.Movie
import ru.orlovegor.moviesearchapp.data.models.MovieTypes
import ru.orlovegor.moviesearchapp.data.models.mapToLocalMovie
import ru.orlovegor.moviesearchapp.data.models.mapToMovie
import ru.orlovegor.moviesearchapp.utils.ResultWrapper
import java.io.IOException

class MovieViewModel : ViewModel() {
    private var scope: CoroutineScope? = null
    private val repository = Repository()

    private val _toast = MutableLiveData<Int>()
    private val _listMovie = MutableLiveData<List<Movie>>()

    val toast: LiveData<Int>
        get() = _toast
    val listMovie: LiveData<List<Movie>>
        get() = _listMovie


    fun bind(queryFlow: Flow<String>, movieTypeFlow: Flow<MovieTypes>) {

        scope = CoroutineScope(Dispatchers.Main) + SupervisorJob()
        combine(
            queryFlow,
            movieTypeFlow
        )
        { query, type -> query to type }
            .debounce(3000)
            .onEach { Log.d("TAG", "${it.first}, ${it.second.name}") }

            .catch {
                Log.d("TAG", "error Bind 1 = $it}")
                _toast.postValue(R.string.error)
            }

            .mapLatest { it ->
                //_listMovie.postValue(repository.loadMovie(it.first, it.second.name.lowercase()))
                val data = repository.testApiRequest(it.first, it.second.name.lowercase())
                when (data) {
                    is ResultWrapper.Success -> {
                        Log.d("TAG", " ${data.value.toString()}")
                        _listMovie.postValue(data.value.map { it.mapToMovie() })
                        repository.saveMovie(data.value.map { it.mapToLocalMovie() })
                    }
                    is ResultWrapper.NetworkError -> {
                        _listMovie.postValue(
                            repository.getLocalMovie(it.first, it.second).map { it.mapToMovie() })
                        Log.d("TAG", "NETWORK ERROR")
                    }
                    is ResultWrapper.Error -> {
                        Log.d("TAG", "ERROR -> ${data.exception?.message}")
                        _toast.postValue(R.string.input_error)
                    }
                }
            }
            .flowOn(Dispatchers.IO)
            .catch {
                Log.d("TAG", "error Bind 3 = $it}")
                _toast.postValue(R.string.error)
            }
            .launchIn(scope!!)
    }


    fun cancelJob() {
        scope?.cancel()
    }
}