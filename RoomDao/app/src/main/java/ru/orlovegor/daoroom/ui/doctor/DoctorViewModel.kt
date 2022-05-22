package ru.orlovegor.daoroom.ui.doctor

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.orlovegor.daoroom.R
import ru.orlovegor.daoroom.ui.models.DoctorRW
import ru.orlovegor.daoroom.ui.repositories.DoctorRepo
import ru.orlovegor.daoroom.utils.SingleLiveEvent


class DoctorViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = DoctorRepo(application)
    private val toastEvent = SingleLiveEvent<Int>()

    private val doctorMutableLiveData = MutableLiveData<ArrayList<DoctorRW>>()

    val doctorLiveData: LiveData<ArrayList<DoctorRW>>
        get() = doctorMutableLiveData
    val toast: SingleLiveEvent<Int>
        get() = toastEvent



    fun deleteDoctor(doctorId: Long) {
        viewModelScope.launch {
            try {
                repository.deleteDoctor(doctorId)
                doctorMutableLiveData.postValue(repository.getDoctorsLocal())
            } catch (t: Throwable) {
                toastEvent.postValue(R.string.error_toast)
            }
        }
    }

    fun isFirsLaunch() {
        viewModelScope.launch {
            try {
                if (!repository.isFirstLaunch()) {
                    repository.addStandardValues()
                    repository.addFlagToFirsLaunch()
                }
            } catch (t: Throwable) {
                Log.d("TAG", "isFirstLaunch fun error")
            }
        }
    }

    fun getDoctor() {
        viewModelScope.launch {
            doctorMutableLiveData.postValue(repository.getDoctorsLocal())
        }
    }
}