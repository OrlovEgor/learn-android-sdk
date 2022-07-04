package ru.orlovegor.moviesearchapp.data.dao

import android.content.Context
import androidx.room.Room

object Database {
    lateinit var instance: ProjectDao
        private set

    fun init(context: Context) {
        instance = Room.databaseBuilder(
            context,
            ProjectDao::class.java,
            ProjectDao.DB_NAME
        )
            .build()
    }
}