package com.skillbox.homeworkokhttp.movie_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skillbox.homeworkokhttp.utils.SingleLiveEvent
import okhttp3.Call
import java.io.IOException
import java.lang.Exception
import java.time.Year

class MovieListViewModel : ViewModel() {

    private val repository = MovieRepository()
    private var currentCall: Call? = null

    private val movieListLiveData = MutableLiveData<ArrayList<RemoteMovie>>()
    private val isLoadingLiveData = MutableLiveData<Boolean>()
    private val isErrorLoadingLiveData = MutableLiveData<Boolean>()
    private val showToastLiveData = SingleLiveEvent<Unit>()

    val movieList: LiveData<ArrayList<RemoteMovie>>
        get() = movieListLiveData
    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData
    val isErrorLoading: LiveData<Boolean>
        get() = isErrorLoadingLiveData
    val showToast: LiveData<Unit>
    get() = showToastLiveData



    fun search(tittle: String, year: String, genre: String) {
        val isCorrectYear = repository.checkYear(year)
        if (isCorrectYear) {
            isLoadingLiveData.postValue(true)
            currentCall =
                repository.searchMovie(tittle, year, genre, { movies ->
                    isLoadingLiveData.postValue(false)
                    movieListLiveData.postValue(movies)
                    isErrorLoadingLiveData.postValue(false)
                    currentCall = null
                }) {
                    try {
                        throw (it)
                    } catch (e: IOException) {
                        isLoadingLiveData.postValue(false)
                        isErrorLoadingLiveData.postValue(true)
                    }
                }
        } else{
            showToastLiveData.postValue(Unit)
        }
    }

    override fun onCleared() {
        super.onCleared()
        currentCall?.cancel()
    }
}