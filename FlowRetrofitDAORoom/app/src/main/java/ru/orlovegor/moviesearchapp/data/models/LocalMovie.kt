package ru.orlovegor.moviesearchapp.data.models

import androidx.room.*
import ru.orlovegor.moviesearchapp.data.dao.contract.MovieContract

@Entity(tableName = MovieContract.TABLE_NAME)
@TypeConverters(MovieTypesConverter::class)
data class LocalMovie
    (
    @PrimaryKey
    @ColumnInfo(name = MovieContract.Columns.ID)
    val id: String,
    @ColumnInfo(name = MovieContract.Columns.TITTLE)
    val title: String,
    @ColumnInfo(name = MovieContract.Columns.MOVIE_TYPE)
    val movieType: MovieTypes,
    @ColumnInfo(name = MovieContract.Columns.IMAGE_URL)
    val imageUrl: String?
)

fun LocalMovie.mapToMovie() =
    Movie(
        id = this.id,
        title = this.title,
        movieType = this.movieType,
        imageUrl = this.imageUrl
    )

