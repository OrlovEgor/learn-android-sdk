package ru.orlovegor.clientprovider

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Error
import java.lang.IllegalArgumentException

class ClientViewModel(application: Application) : AndroidViewModel(application) {
    private val repo = ClientRepository(application)

    private val coursesMutableLiveData = MutableLiveData<List<Course>>()
    private val showToastMutableLiveData = SingleLiveEvent<Int>()

    val courseLiveData: LiveData<List<Course>>
        get() = coursesMutableLiveData

    val showToastLiveData: LiveData<Int>
        get() = showToastMutableLiveData


    fun getAllCourses() {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) { coursesMutableLiveData.postValue(repo.getAllCourses()) }

            } catch (t: Throwable) {
                showToastMutableLiveData.postValue(R.string.toast_error)
            }
        }
    }

    fun getCourseById(id: Int) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    coursesMutableLiveData.postValue(repo.getCourseFromID(id))
                }
            } catch (t: Error) {
                withContext(Dispatchers.Main) {
                    showToastMutableLiveData.postValue(R.string.toast_error)
                }
            }
        }
    }

    fun addCourse(id: Int, tittle: String) {
        try {
            repo.addCourse(id, tittle)
        } catch (t: IllegalArgumentException) {
            showToastMutableLiveData.postValue(R.string.toast_error)
        }
    }

    fun deleteCourseById(id: Int) {
        try {
            repo.deleteCourseFromId(id)
        } catch (t: IllegalArgumentException) {
            showToastMutableLiveData.postValue(R.string.toast_error)
        }
    }

    fun deleteAllCourse() {
        try {
            repo.deleteAllCourses()
        } catch (t: IllegalArgumentException) {
            showToastMutableLiveData.postValue(R.string.toast_error)
        }
    }

    fun updateCourseById(id: Int, tittle: String) {
        try {
            repo.updateCourseFromId(id, tittle)
        } catch (t: IllegalArgumentException) {
            showToastMutableLiveData.postValue(R.string.toast_error)
        }
    }
}