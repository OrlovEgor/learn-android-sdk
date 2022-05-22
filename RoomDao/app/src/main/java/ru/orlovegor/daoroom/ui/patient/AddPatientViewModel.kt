package ru.orlovegor.daoroom.ui.patient

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.orlovegor.daoroom.R
import ru.orlovegor.daoroom.ui.repositories.PatientRepository
import ru.orlovegor.daoroom.utils.SingleLiveEvent

class AddPatientViewModel : ViewModel() {

    private val repo = PatientRepository()
    private val eventSuccessfulLiveData = SingleLiveEvent<Unit>()
    private val toastEvent = SingleLiveEvent<Int>()

    val eventSuccessful: LiveData<Unit>
        get() = eventSuccessfulLiveData

    val toast: LiveData<Int>
        get() = toastEvent

    fun addPatient(doctorId: Long, firstName: String, lastName: String) {
        viewModelScope.launch {
            try {
                repo.insertDoctorAndPatientRelationship(doctorId, firstName, lastName)
                withContext(Dispatchers.Main) {
                    eventSuccessfulLiveData.postValue(Unit)
                }
            } catch (t: Throwable) {
                toastEvent.postValue(R.string.error_toast)
                Log.d("ERROR", "addPatient error = ${t.message}")
            }
        }
    }

}