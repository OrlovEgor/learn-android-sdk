package ru.orlovegor.daoroom.ui.doctor

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import ru.orlovegor.daoroom.R
import ru.orlovegor.daoroom.ui.models.DoctorRW
import ru.orlovegor.daoroom.ui.repositories.DoctorRepo
import ru.orlovegor.daoroom.utils.SingleLiveEvent

class AddDoctorViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = DoctorRepo(application)
    private val scope = viewModelScope

    private val mutableLiveDataSpecializations = MutableLiveData<ArrayList<String>>(arrayListOf())
    private val mutableLiveDataCabinets = MutableLiveData<ArrayList<String>>()
    private val mutableLiveDataHospitalsName = MutableLiveData<ArrayList<String>>()
    private val mutableLiveDataWorksTimes = MutableLiveData<ArrayList<String>>()
    private val toastEvent = SingleLiveEvent<Int>()
    private val saveSuccessLiveEvent = SingleLiveEvent<Unit>()


    val liveDataSpecialization: LiveData<ArrayList<String>>
        get() = mutableLiveDataSpecializations
    val liveDataCabinets: LiveData<ArrayList<String>>
        get() = mutableLiveDataCabinets
    val liveDataHospitalsName: LiveData<ArrayList<String>>
        get() = mutableLiveDataHospitalsName
    val liveDataWorkTime: LiveData<ArrayList<String>>
        get() = mutableLiveDataWorksTimes
    val toast: LiveData<Int>
        get() = toastEvent
    val saveSuccessLiveData: LiveData<Unit>
        get() = saveSuccessLiveEvent

    fun insertDoctor(doctor: ArrayList<DoctorRW>) {
        scope.launch {
            try {
                repository.insertDoctorLocal(doctor)
                saveSuccessLiveEvent.postValue(Unit)
            } catch (t: Throwable) {
                toastEvent.postValue(R.string.error_toast_insert)
            }
        }
    }

    fun getWorkTimes() {
        scope.launch {
            try {
                val workTimes = repository.getWorkTimes()
                mutableLiveDataWorksTimes.postValue(workTimes as ArrayList<String>)
            } catch (t: Throwable) {
                toastEvent.postValue(R.string.error_toast)
            }
        }
    }

    fun getHospitalsName() {
        scope.launch {
            try {
                val hospitalsName = repository.getHospitalName()
                mutableLiveDataHospitalsName.postValue(hospitalsName as ArrayList<String>)
            } catch (t: Throwable) {
                toastEvent.postValue(R.string.error_toast)
            }
        }
    }

    fun getCabinetsNumber() {
        scope.launch {
            try {
                val cabinets = repository.getCabinetsNumber()
                mutableLiveDataCabinets.postValue(cabinets as ArrayList<String>)
            } catch (t: Throwable) {
                toastEvent.postValue(R.string.error_toast)
            }
        }
    }

    fun getSpecializations() {
        scope.launch {
            try {
                val specializationsName = repository.getSpecializationName()
                mutableLiveDataSpecializations.value = specializationsName as ArrayList<String>
            } catch (t: Throwable) {
                toastEvent.postValue(R.string.error_toast)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("Tag", "CLEARED")
    }

}