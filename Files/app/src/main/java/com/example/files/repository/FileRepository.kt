package com.example.files.repository

import android.content.Context
import android.os.Environment
import android.util.Log
import com.example.files.network.Networking
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.lang.RuntimeException

class FileRepository(context: Context) {

    private val sharedPrefs by lazy {
        context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
    }

    private val repoContext = context

    suspend fun createFile(url: String): File {
        return withContext(Dispatchers.IO) {
            if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) {
                throw RuntimeException()
            } else {
                val folder = repoContext.getExternalFilesDir("external storage/files/File folder")
                val fileName =
                    "${System.currentTimeMillis()}_${url.substringAfterLast("/", "")}"
                val file = File(folder, fileName)
                return@withContext (file)
            }
        }
    }

    suspend fun download(file: File, url: String) {
        return withContext(Dispatchers.IO) {
            if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) return@withContext
            file.outputStream().use { fileOutputStream ->
                Networking.downloadApi
                    .getFile(url)
                    .byteStream()
                    .use { inputStream ->
                        inputStream.copyTo(fileOutputStream)
                    }
            }
        }
    }

    suspend fun saveToSharedPrefs(file: File, urlToKEy: String) {
        withContext(Dispatchers.IO) {
            val fileNameToValue = file.name
            sharedPrefs.edit()
                .putString(urlToKEy, fileNameToValue)
                .commit()
        }
    }

    suspend fun deleteFile(file: File) {
        withContext(Dispatchers.IO) {
            if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) return@withContext
            val isDeleted = file.delete()
            Log.d("app", "isDeleted $isDeleted")
        }
    }

    suspend fun checkDownloadedFile(url: String): Boolean {
        return withContext(Dispatchers.IO) {
            return@withContext (sharedPrefs.contains(url))
        }
    }

    suspend fun addFlagToFirsLaunch() {
        withContext(Dispatchers.IO) {
            val key = FIRST_LAUNCH_KEY
            val value = FIRST_LAUNCH_VALUE
            sharedPrefs.edit()
                .putString(key, value)
                .commit()
        }
    }

    suspend fun isFirstLaunch(): Boolean {
        return withContext(Dispatchers.IO) {
            return@withContext (sharedPrefs.contains(FIRST_LAUNCH_KEY))
        }
    }

    suspend fun getUrlFromAssets(): String {
        return withContext(Dispatchers.IO) {
            val url = repoContext.resources.assets.open("Url-name.txt")
                .bufferedReader()
                .use {
                    it.readText()
                }
            Log.d("app", "url = $url")
            return@withContext (url)
        }
    }


    companion object {
        private const val SHARED_PREFS_NAME = "File_shared_prefs"
        private const val FIRST_LAUNCH_KEY = "Is_first_launch"
        private const val FIRST_LAUNCH_VALUE = "true"
    }
}