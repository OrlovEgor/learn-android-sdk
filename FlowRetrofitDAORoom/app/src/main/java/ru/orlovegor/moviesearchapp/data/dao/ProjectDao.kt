package ru.orlovegor.moviesearchapp.data.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.orlovegor.moviesearchapp.data.dao.ProjectDao.Companion.DB_VERSION

@Database(
    entities = [
        LocalMovie::class
    ],
    version = DB_VERSION
)

abstract class ProjectDao : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {
        const val DB_VERSION = 2
        const val DB_NAME = "Project_Database"
    }
}
