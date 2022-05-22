package ru.orlovegor.contentprovider.custom_content_provider

import android.content.*
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import android.util.Log
import com.squareup.moshi.Moshi
import ru.orlovegor.contentprovider.BuildConfig
import ru.orlovegor.contentprovider.data.Course
import ru.orlovegor.contentprovider.utils.TAG_LOGGER

class CoursesContentProvider : ContentProvider() {

    private lateinit var coursesPrefs: SharedPreferences
    private val courseAdapter = Moshi.Builder().build().adapter(Course::class.java)
    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        addURI(AUTHORITIES, PATH_COURSES, TYPE_COURSES)
        addURI(AUTHORITIES, "$PATH_COURSES/#", TYPE_COURSES_ID)
    }

    override fun onCreate(): Boolean {
        coursesPrefs =
            context?.getSharedPreferences("course_shared_prefs", Context.MODE_PRIVATE)
                ?: error("APP Context Error")
        Log.d(TAG_LOGGER, "${Thread.currentThread().name},provider started ")
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        Log.d(TAG_LOGGER, "${Thread.currentThread().name},query ")
        return when (uriMatcher.match(uri)) {
            TYPE_COURSES -> getAllCoursesCursor()
            TYPE_COURSES_ID -> getCourse(uri)
            else -> null
        }
    }

    private fun getAllCoursesCursor(): Cursor {
        Log.d(TAG_LOGGER, "${Thread.currentThread().name},get all courses cursor ")
        val allCourses = coursesPrefs.all.mapNotNull {
            val coursesJsonString = it.value as String
            courseAdapter.fromJson(coursesJsonString)
        }
        val cursor = MatrixCursor(arrayOf(COLUMN_COURSES_ID, COLUMN_COURSES_TITTLE))
        allCourses.forEach {
            cursor.newRow()
                .add(it.id)
                .add(it.title)
        }
        return cursor
    }

    private fun getCourse(uri: Uri): Cursor {
        Log.d(TAG_LOGGER, "${Thread.currentThread().name},get course ")
        Log.d(TAG_LOGGER, Thread.currentThread().name)
        val courses = coursesPrefs.all.mapNotNull {
            val courseJsonString = it.value as String
            courseAdapter.fromJson(courseJsonString)
        }
        val cursor = MatrixCursor(arrayOf(COLUMN_COURSES_ID, COLUMN_COURSES_TITTLE))
        courses.forEach {
            if (it.id.toString() == uri.lastPathSegment?.toLongOrNull().toString())
                cursor.newRow()
                    .add(it.id)
                    .add(it.title)
        }
        return cursor
    }

    override fun getType(p0: Uri): String? {
        return null
    }

    override fun insert(p0: Uri, p1: ContentValues?): Uri? {
        Log.d(TAG_LOGGER, "${Thread.currentThread().name},insert ")
        p1 ?: return null
        return when (uriMatcher.match(p0)) {
            TYPE_COURSES -> addCourse(p1)
            else -> null
        }
    }

    private fun addCourse(contentValues: ContentValues): Uri? {
        Log.d(TAG_LOGGER, "${Thread.currentThread().name},add course ")
        val id = contentValues.getAsLong(COLUMN_COURSES_ID) ?: return null
        val tittle = contentValues.getAsString(COLUMN_COURSES_TITTLE) ?: return null
        val course = Course(id, tittle)
        coursesPrefs.edit()
            .putString(id.toString(), courseAdapter.toJson(course))
            .apply()
        return Uri.parse("content://$AUTHORITIES/$PATH_COURSES/$id")
    }

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
        Log.d(TAG_LOGGER, "${Thread.currentThread().name},delete ")
        return when (uriMatcher.match(p0)) {
            TYPE_COURSES_ID -> deleteCourse(p0)
            TYPE_COURSES -> deleteAllCourses()
            else -> 0
        }
    }

    private fun deleteCourse(uri: Uri): Int {
        Log.d(TAG_LOGGER, "${Thread.currentThread().name},delete course ")
        val courseID = uri.lastPathSegment?.toLongOrNull().toString()
        return if (coursesPrefs.contains(courseID)) {
            coursesPrefs.edit()
                .remove(courseID)
                .apply()
            1
        } else {
            0
        }
    }

    private fun deleteAllCourses(): Int {
        val countItems = coursesPrefs.all.size
        coursesPrefs.edit()
            .clear()
            .apply()
        return countItems
    }

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        Log.d(TAG_LOGGER, "${Thread.currentThread().name},update ")
        p1 ?: return 0
        return when (uriMatcher.match(p0)) {
            TYPE_COURSES_ID -> updateCourse(p0, p1)
            else -> 0
        }
    }

    private fun updateCourse(uri: Uri, contentValue: ContentValues): Int {
        Log.d(TAG_LOGGER, "${Thread.currentThread().name},update course ")
        val courseID = uri.lastPathSegment?.toLongOrNull().toString()
        return if (coursesPrefs.contains(courseID)) {
            addCourse(contentValue)
            1
        } else {
            0
        }
    }

    companion object {
        private const val AUTHORITIES = "${BuildConfig.APPLICATION_ID}.provider"
        private const val PATH_COURSES = "courses"
        private const val TYPE_COURSES = 1
        private const val TYPE_COURSES_ID = 2
        private const val COLUMN_COURSES_ID = "id"
        private const val COLUMN_COURSES_TITTLE = "tittle"
    }
}