package ru.orlovegor.daoroom.ui.records

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.orlovegor.daoroom.R
import ru.orlovegor.daoroom.database.contract.models.data.Record
import ru.orlovegor.daoroom.ui.repositories.RecordsRepository
import ru.orlovegor.daoroom.utils.SingleLiveEvent

class RecordsViewModel(private val patientId: Long) : ViewModel() {

    private val repo = RecordsRepository()
    private val errorToastEvent = SingleLiveEvent<Int>()
    private val successLiveEvent = SingleLiveEvent<Unit>()

    val records: LiveData<List<Record>> = liveData {
        try {
            val records = repo.getRecordsByPatient(patientId).asLiveData()
            emitSource(records)
        } catch (t: Throwable) {
            errorToastEvent.postValue(R.string.error_toast)
        }
    }
    val errorToast: LiveData<Int>
        get() = errorToastEvent
    val successEvent: LiveData<Unit>
        get() = successLiveEvent

    fun addRecord(patientId: Long, diagnosis: String,therapy: String) {
        viewModelScope.launch {
            try {
                repo.insertRecord(patientId, diagnosis,therapy)
                withContext(Dispatchers.IO) {
                    successLiveEvent.postValue(Unit)
                }
            } catch (t: Throwable) {
                errorToastEvent.postValue(R.string.error_toast)
            }
        }
    }
}