package ru.orlovegor.daoroom

import android.app.Application
import ru.orlovegor.daoroom.database.contract.Database

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Database.init(this)
    }
}