package ru.orlovegor.daoroom.ui.records

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.orlovegor.daoroom.R
import ru.orlovegor.daoroom.ui.repositories.RecordsRepository
import ru.orlovegor.daoroom.utils.SingleLiveEvent

class UpdateRecordViewModel : ViewModel() {

    private val repository = RecordsRepository()
    private val successLiveEvent = SingleLiveEvent<Unit>()
    private val toastEvent = SingleLiveEvent<Int>()

    val successEvent: LiveData<Unit>
        get() = successLiveEvent
    val toast: LiveData<Int>
        get() = toastEvent

    fun updateDiagnosis(diagnosis: String, recordId: Long) {
        viewModelScope.launch {
            try {
                repository.updateDiagnosis(diagnosis, recordId)
                withContext(Dispatchers.IO) {
                    successLiveEvent.postValue(Unit)
                }
            } catch (t: Throwable) {
                toastEvent.postValue(R.string.error_toast)
            }
        }
    }
}