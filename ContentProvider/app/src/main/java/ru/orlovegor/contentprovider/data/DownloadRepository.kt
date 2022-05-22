package ru.orlovegor.contentprovider.data

import android.content.Context
import android.content.Intent
import android.os.Environment
import androidx.core.content.FileProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.orlovegor.contentprovider.BuildConfig
import ru.orlovegor.contentprovider.network.Networking
import java.io.File
import java.lang.RuntimeException

class DownloadRepository(context: Context) {

    private val repoContext = context
    private val urlFile = "https://github.com/Kotlin/kotlinx.coroutines/raw/master/README.md"

    suspend fun createFile(): File {
        return withContext(Dispatchers.IO) {
            if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) {
                throw RuntimeException()
            } else {
                val folder = repoContext.getExternalFilesDir("external storage/files/File folder")
                val fileName = "Shared file.txt"
                val file = File(folder, fileName)
                return@withContext (file)
            }
        }
    }

    suspend fun download(file: File) {
        return withContext(Dispatchers.IO) {
            if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) return@withContext
            file.outputStream().use { fileOutputStream ->
                Networking.downloadApi
                    .getFile(urlFile)
                    .byteStream()
                    .use { inputStream ->
                        inputStream.copyTo(fileOutputStream)
                    }
            }
        }
    }

    suspend fun shareFile() {
        withContext(Dispatchers.IO) {
            if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) return@withContext
            val dir = repoContext.getExternalFilesDir("external storage/files/File folder")
            val file = File(dir, "Shared file.txt")
            if (file.exists().not()) return@withContext
            val uri = FileProvider.getUriForFile(
                repoContext,
                "${BuildConfig.APPLICATION_ID}.file_provider",
                file
            )
            val intent = Intent(Intent.ACTION_SEND).apply {
                putExtra(Intent.ACTION_SEND, uri)
                type = repoContext.contentResolver.getType(uri)
                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION

            }
            val shareIntent = Intent.createChooser(intent, null).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                //TODO наверно не лучшая пратика закрывать вызов активности флагом,но по другому не разобрался
            }
            repoContext.startActivity(shareIntent)
        }
    }
}