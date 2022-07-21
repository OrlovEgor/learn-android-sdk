package ru.orlovegor.workmanagerandservices.presenter

import androidx.lifecycle.AndroidViewModel
import ru.orlovegor.workmanagerandservices.Application
import ru.orlovegor.workmanagerandservices.data.Repository

class MainViewModel(application: Application): AndroidViewModel(application) {
    private val repo = Repository()


}