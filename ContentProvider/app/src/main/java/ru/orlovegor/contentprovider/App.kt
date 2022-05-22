package ru.orlovegor.contentprovider

import android.app.Application
import android.content.Context
import android.util.Log
import ru.orlovegor.contentprovider.utils.TAG_LOGGER

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        application = this
        Log.d(TAG_LOGGER, "app started")
    }
    companion object {
        var application: Application? = null
            private set
        val context: Context
            get() = application!!.applicationContext
    }
}