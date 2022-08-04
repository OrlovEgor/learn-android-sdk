package ru.orlovegor.workmanagerandservices.data
import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import retrofit2.HttpException


class DownloadWorker(
    context: Context,
    params: WorkerParameters,
) : CoroutineWorker(context, params) {
    private val repo = Repository(context)
    override suspend fun doWork(): Result {
        val uriToDownload = inputData.getString(DOWNLOAD_URL_KEY)
        return try {
            Log.d("TAG", "WORK START")
            if (uriToDownload != null) {
               repo.download(repo.createFile(uriToDownload), uriToDownload)
                Result.success()
            } else Result.failure()
        } catch (h: HttpException) {
            Log.d("TAG", "Error = HttpException")
            Result.retry()
        } catch (t: Throwable) {
            Result.failure()
        }
    }

    companion object {
        const val DOWNLOAD_URL_KEY = "download_url"
    }
}