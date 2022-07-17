package ru.orlovegor.moviesearchapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.orlovegor.moviesearchapp.data.dao.contract.MovieContract
import ru.orlovegor.moviesearchapp.data.models.LocalMovie
import ru.orlovegor.moviesearchapp.data.models.MovieTypes

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(movie: List<LocalMovie>)

    @Query("SELECT * FROM ${MovieContract.TABLE_NAME} WHERE ${MovieContract.Columns.TITTLE} LIKE '%' || :tittle || '%' AND ${MovieContract.Columns.MOVIE_TYPE} = :type")
    fun getMovies(tittle: String, type: MovieTypes): List<LocalMovie>

    @Query("SELECT * FROM ${MovieContract.TABLE_NAME}")
    fun getAllMovies(): Flow<List<LocalMovie>>
}