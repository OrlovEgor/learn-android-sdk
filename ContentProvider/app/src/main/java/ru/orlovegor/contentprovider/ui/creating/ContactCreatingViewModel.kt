package ru.orlovegor.contentprovider.ui.creating

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.orlovegor.contentprovider.R
import ru.orlovegor.contentprovider.data.ContactListRepository
import ru.orlovegor.contentprovider.utils.SingleLiveEvent
import ru.orlovegor.contentprovider.utils.WrongEmailExceptions

class ContactCreatingViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = ContactListRepository(application)
    private val scope = viewModelScope
    private val saveSuccessLiveEvent = SingleLiveEvent<Unit>()
    private val toastEvent = SingleLiveEvent<Int>()

    val toast: LiveData<Int>
        get() = toastEvent

    val saveSuccessLiveData: LiveData<Unit>
        get() = saveSuccessLiveEvent

    fun saveContact(firstName: String, lastName: String, phone: String, email: String?) {
        scope.launch {
            try {
                withContext(Dispatchers.IO) {
                    repo.saveContact(firstName, lastName, phone, email)
                }
                withContext(Dispatchers.Main) {
                    toastEvent.postValue(R.string.save_contact_successful)
                    saveSuccessLiveEvent.postValue(Unit)
                }

            } catch (e: WrongEmailExceptions) {
                toastEvent.postValue(R.string.wrong_email_toast)
            } catch (t: Throwable) {
                toastEvent.postValue(R.string.save_contact_exception)
            }
        }
    }
}