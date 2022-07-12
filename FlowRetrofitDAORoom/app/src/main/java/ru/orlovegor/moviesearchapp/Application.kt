package ru.orlovegor.moviesearchapp

import android.app.Application
import android.content.Context
import kotlinx.coroutines.DEBUG_PROPERTY_NAME
import kotlinx.coroutines.DEBUG_PROPERTY_VALUE_ON
import ru.orlovegor.moviesearchapp.data.dao.Database

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        Database.init(this)
        application = this
        System.setProperty(DEBUG_PROPERTY_NAME, DEBUG_PROPERTY_VALUE_ON)
    }

    companion object {
        var application: Application? = null
            private set
        val context: Context
            get() = application!!.applicationContext
    }
}