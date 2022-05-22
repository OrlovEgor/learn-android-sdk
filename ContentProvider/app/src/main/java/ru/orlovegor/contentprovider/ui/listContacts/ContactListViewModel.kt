package ru.orlovegor.contentprovider.ui.listContacts

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.orlovegor.contentprovider.data.Contact
import ru.orlovegor.contentprovider.data.ContactListRepository


class ContactListViewModel(application: Application) : AndroidViewModel(application) {

    private val contactListRepository = ContactListRepository(application)

    private val contactsListMutableLiveData = MutableLiveData<List<Contact>>()

    private val scope = viewModelScope

    val contactsLiveData: LiveData<List<Contact>>
        get() = contactsListMutableLiveData

    fun loadList() {
        try {
            scope.launch {
                withContext(Dispatchers.IO) {
                    contactsListMutableLiveData.postValue(contactListRepository.getAllContacts())
                }
            }
        } catch (t: Throwable) {
            contactsListMutableLiveData.postValue(emptyList())
        }
    }
}