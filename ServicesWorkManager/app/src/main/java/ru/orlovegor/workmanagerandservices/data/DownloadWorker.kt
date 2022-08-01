package ru.orlovegor.workmanagerandservices.data
import android.content.Context
import android.os.Environment
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.lang.RuntimeException

class DownloadWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        val uriToDownload = inputData.getString(DOWNLOAD_URL_KEY)
        if (uriToDownload != null) {
            download(createFile(uriToDownload), uriToDownload)
        } else return Result.failure()
        Log.d("TAG", "WORK START")
        return Result.success()
    }

    private suspend fun createFile(url: String): File {
        return withContext(Dispatchers.IO) {
            if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) {
                throw RuntimeException()
            } else {
                val folder =
                    applicationContext.getExternalFilesDir("external storage/files/File folder")
                val fileName =
                    "${System.currentTimeMillis()}_${url.substringAfterLast("/", "")}"
                val file = File(folder, fileName)
                return@withContext (file)
            }
        }
    }

    private suspend fun download(file: File, url: String) {
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

    companion object {
        const val DOWNLOAD_URL_KEY = "download_url"
    }
}