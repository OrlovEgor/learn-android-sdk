package ru.orlovegor.clientprovider

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ClientRepository(private val context: Context) {

    fun updateCourseFromId(id: Int, tittle: String) {
        val uri = Uri.parse(CONTENT_PROVIDER_COURSE + PATH_COURSES + id.toString())
        Log.d(TAG, "thread = ${Thread.currentThread().name}")
        val contentValues = ContentValues().apply {
            put(COLUMN_COURSES_ID, id.toString())
            put(COLUMN_COURSES_TITTLE, tittle)
        }
        context.contentResolver.insert(uri, contentValues)
        context.contentResolver.update(uri, contentValues, "", arrayOf())
    }

    fun getAllCourses(): List<Course> =

        context.contentResolver.query(
            Uri.parse(CONTENT_PROVIDER_COURSE + PATH_COURSES),
            null,
            null,
            null,
            null
        )?.use {
            getCoursesFromCursor(it)
        }.orEmpty()

    fun addCourse(id: Int, tittle: String) {
        Log.d(TAG, "thread = ${Thread.currentThread().name}")
        val contentValues = ContentValues().apply {
            put(COLUMN_COURSES_ID, id.toString())
            put(COLUMN_COURSES_TITTLE, tittle)
        }
        context.contentResolver.insert(
            Uri.parse(CONTENT_PROVIDER_COURSE + PATH_COURSES),
            contentValues
        )
    }


    private fun getCoursesFromCursor(cursor: Cursor): List<Course> {
        Log.d(TAG, "thread = ${Thread.currentThread().name}")
        if (cursor.moveToFirst().not()) return emptyList()
        val list = mutableListOf<Course>()
        do {
            val tittleIndex =
                cursor.getColumnIndex(COLUMN_COURSES_TITTLE)
            val tittle = cursor.getString(tittleIndex).orEmpty()
            val idIndex = cursor.getColumnIndex(COLUMN_COURSES_ID)
            val id = cursor.getLong(idIndex)
            list.add(
                Course(
                    id = id,
                    title = tittle
                )
            )
            Log.d("TAG", "CURSOR = id= $id tittle = $tittle")
        } while (cursor.moveToNext())
        return list
    }

    fun deleteCourseFromId(id: Int) {
        val uri = Uri.parse(CONTENT_PROVIDER_COURSE + PATH_COURSES + id.toString())
        Log.d(TAG, " Path = $uri")
        Log.d(TAG, "thread = ${Thread.currentThread().name}")
        context.contentResolver.delete(uri, "", arrayOf(""))
    }

    fun deleteAllCourses() {
        Log.d(TAG, "thread = ${Thread.currentThread().name}")
        context.contentResolver.delete(
            Uri.parse(CONTENT_PROVIDER_COURSE + PATH_COURSES),
            "",
            arrayOf()
        )
    }

    fun getCourseFromID(id: Int): List<Course> =
        context.contentResolver.query(
            Uri.parse(CONTENT_PROVIDER_COURSE + PATH_COURSES + id.toString()),
            arrayOf(COLUMN_COURSES_ID, COLUMN_COURSES_TITTLE),
            "id = $id",
            null,
            null
        )?.use { getCoursesFromCursor(it) }.orEmpty()


    companion object {
        private const val COLUMN_COURSES_ID = "id"
        private const val COLUMN_COURSES_TITTLE = "tittle"
        private const val PATH_COURSES = "courses/"
        private const val CONTENT_PROVIDER_COURSE =
            "content://ru.orlovegor.contentprovider.provider/"
        private const val TAG = "TAG"
    }
}
