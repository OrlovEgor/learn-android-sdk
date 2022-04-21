package ru.orlovegor.notificationapp

import android.app.Application
import ru.orlovegor.notificationapp.ui.NotificationChannels

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        NotificationChannels.createChannels(this)
    }
}