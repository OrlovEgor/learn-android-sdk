package ru.orlovegor.workmanagerandservices.data

import android.content.Context
import android.os.Environment
import androidx.lifecycle.LiveData
import androidx.work.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.lang.RuntimeException
import java.util.concurrent.TimeUnit

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

    fun startDownload(url: String): LiveData<MutableList<WorkInfo>> {
        val workData = workDataOf(
            DownloadWorker.DOWNLOAD_URL_KEY to url
        )
        val workConstraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .build()

        val workRequest = OneTimeWorkRequestBuilder<DownloadWorker>()
            .setInputData(workData)
            .setConstraints(workConstraints)
            .setBackoffCriteria(BackoffPolicy.LINEAR, 20, TimeUnit.SECONDS)
            .build()

        WorkManager.getInstance(repoContext)
            .enqueueUniqueWork(WORKER_ID, ExistingWorkPolicy.REPLACE, workRequest)
        return WorkManager.getInstance(repoContext).getWorkInfosForUniqueWorkLiveData(WORKER_ID)
    }

    fun stopDownload() {
        WorkManager.getInstance(repoContext).cancelUniqueWork(WORKER_ID)
    }

    companion object {
        private const val WORKER_ID = "download_worker_1"
    }
}