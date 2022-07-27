package ru.orlovegor.workmanagerandservices.data

import android.content.Context
import android.os.Environment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.lang.RuntimeException

class Repository(context: Context) {
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
}