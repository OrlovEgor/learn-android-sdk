package ru.orlovegor.moviesearchapp

import android.app.Application
import ru.orlovegor.moviesearchapp.data.dao.Database

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        Database.init(this)
    }
}