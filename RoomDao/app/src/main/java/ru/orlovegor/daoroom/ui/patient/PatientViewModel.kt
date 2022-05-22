package ru.orlovegor.daoroom.ui.patient

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.orlovegor.daoroom.R
import ru.orlovegor.daoroom.database.contract.models.relationship.DoctorWithPatients
import ru.orlovegor.daoroom.ui.repositories.PatientRepository
import ru.orlovegor.daoroom.utils.SingleLiveEvent

class PatientViewModel(private val doctorId: Long) : ViewModel() {

    private val repo = PatientRepository()
    private val toastEvent = SingleLiveEvent<Int>()
    private val doctorsToastEvent = SingleLiveEvent<String>()
    val patients: LiveData<DoctorWithPatients> = liveData {
        try {
            val patients = repo.getPatientsByDoctorLocal(doctorId).asLiveData()
            emitSource(patients)
        } catch (t: Throwable) {
            toastEvent.postValue(R.string.error_toast)
        }
    }
    val doctorsToast: LiveData<String>
        get() = doctorsToastEvent
    val toast: LiveData<Int>
        get() = toastEvent

    fun deletePatient(patientId: Long) {
        viewModelScope.launch {
            try {
                repo.deletePatient(patientId)
            } catch (t: Throwable) {
                toastEvent.postValue(R.string.error_toast)
            }
        }
    }

    fun getDoctorsByPatient(patientId: Long) {
        viewModelScope.launch {
            try {
                val doctors = repo.getDoctorsByPatientLocal(patientId)
                withContext(Dispatchers.Main) {
                    doctorsToastEvent.postValue(doctors)
                }
            } catch (t: Throwable) {
                toastEvent.postValue(R.string.error_toast)
            }
        }
    }
}