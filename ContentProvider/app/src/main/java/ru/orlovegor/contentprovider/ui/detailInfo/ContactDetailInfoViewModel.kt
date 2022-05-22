package ru.orlovegor.contentprovider.ui.detailInfo

import android.app.Application
import android.content.OperationApplicationException
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.orlovegor.contentprovider.R
import ru.orlovegor.contentprovider.data.ContactListRepository
import ru.orlovegor.contentprovider.utils.SingleLiveEvent

class ContactDetailInfoViewModel(application: Application) : AndroidViewModel(application) {

    private val contactListRepository = ContactListRepository(application)

    private val showToastMutableLiveData = SingleLiveEvent<Int>()

    private val successLiveEvent = SingleLiveEvent<Unit>()

    val showToastLiveData: LiveData<Int>
        get() = showToastMutableLiveData

    val successLiveData: LiveData<Unit>
        get() = successLiveEvent

    fun deleteContact(id: Long) {
        try {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    contactListRepository.deleteContact(id)
                    withContext(Dispatchers.Main) {
                        successLiveEvent.postValue(Unit)
                        showToastMutableLiveData.postValue(R.string.delete_contact_successful)
                    }
                }
            }
        } catch (e: OperationApplicationException) {
            showToastMutableLiveData.postValue(R.string.delete_contact_failed)
            e.printStackTrace()
        }
    }
}