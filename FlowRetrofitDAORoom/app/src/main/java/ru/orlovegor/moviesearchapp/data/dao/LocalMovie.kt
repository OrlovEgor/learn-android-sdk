package ru.orlovegor.moviesearchapp.data.dao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.orlovegor.moviesearchapp.data.dao.contract.MovieContract


@Entity(tableName = MovieContract.TABLE_NAME)
data class LocalMovie
    (
    @PrimaryKey
    @ColumnInfo(name = MovieContract.Columns.ID)
    val id: String,
    @ColumnInfo(name = MovieContract.Columns.TITTLE)
    val title: String,
    @ColumnInfo(name = MovieContract.Columns.IMAGE_URL)
    val imageUrl: String?
)