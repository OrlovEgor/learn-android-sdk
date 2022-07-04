package ru.orlovegor.moviesearchapp.data.dao.contract

object MovieContract {
    const val TABLE_NAME  = "movie"

    object Columns{
        const val ID = "movie_id"
        const val TITTLE = "tittle"
        const val IMAGE_URL = "image_url"
    }
}