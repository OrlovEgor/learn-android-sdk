package com.skillbox.github.ui.repository_detail_info

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skillbox.github.data.StatusCode
import com.skillbox.github.repositories.DetailInfoRepo
import com.skillbox.github.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class RepositoryDetailInformationViewModel : ViewModel() {

    private val detailRepo = DetailInfoRepo()

    private val scope = viewModelScope

    private val showToastLiveData = SingleLiveEvent<Unit>()
    private val isStaredLiveData = MutableLiveData<Boolean>()

    val isStared: LiveData<Boolean>
        get() = isStaredLiveData
    val showToast: LiveData<Unit>
        get() = showToastLiveData

    fun checkStar(nameRepo: String, nameOwner: String) {
        scope.launch {
            try {
                val isStar = detailRepo.checkStar(nameRepo, nameOwner)
                isStaredLiveData.postValue(isStar)
            } catch (t: Throwable) {
                showToastLiveData.postValue(Unit)
            }
        }
    }

    fun addStar(nameRepo: String, nameOwner: String) {
        scope.launch {
            try {
                logger("start try block deleteStar")
                detailRepo.addStar(nameRepo, nameOwner)
                logger("response is successful")
                isStaredLiveData.postValue(true)
            } catch (t: Throwable) {
                logger("start catch block addStar exception = ${t.message}")
                showToastLiveData.postValue(Unit)
            }
        }
    }

    fun deleteStar(nameRepo: String, nameOwner: String) {
        scope.launch {
            try {
                logger("start try block deleteStar")
                detailRepo.deleteStar(nameRepo, nameOwner)
                logger("response is successful")
                isStaredLiveData.postValue(false)
            } catch (t: Throwable) {
                logger("start catch block deleteStar exception = ${t.localizedMessage}")
                showToastLiveData.postValue(Unit)
            }
        }
    }

    private fun logger(message: String) {
        Log.d("DetailInfo", message)
    }
}