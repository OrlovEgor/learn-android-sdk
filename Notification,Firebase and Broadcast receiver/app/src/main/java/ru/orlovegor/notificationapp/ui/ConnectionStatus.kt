package ru.orlovegor.notificationapp.ui

import androidx.lifecycle.LiveData

interface ConnectionStatus {
    fun getStatus(status:Boolean)

}