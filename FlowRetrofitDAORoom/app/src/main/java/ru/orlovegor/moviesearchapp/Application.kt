package ru.orlovegor.moviesearchapp

import android.app.Application
import android.content.Context
import ru.orlovegor.moviesearchapp.data.dao.Database

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        Database.init(this)
        application = this
    }

    companion object {
        var application: Application? = null
            private set
        val context: Context
            get() = application!!.applicationContext
    }
}