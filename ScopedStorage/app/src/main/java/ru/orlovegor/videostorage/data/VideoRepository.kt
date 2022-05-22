package ru.orlovegor.videostorage.data

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.database.ContentObserver
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.orlovegor.videostorage.network.Networking
import ru.orlovegor.videostorage.utils.getMIMEType
import ru.orlovegor.videostorage.utils.haveQ
import java.io.IOException


class VideoRepository(
    private val context: Context
) {

    private var observer: ContentObserver? = null

    fun observeVideo(onChange: () -> Unit) {
        observer = object : ContentObserver(Handler(Looper.getMainLooper())) {
            override fun onChange(selfChange: Boolean) {
                super.onChange(selfChange)
                onChange()
            }
        }

        context.contentResolver.registerContentObserver(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            true,
            observer ?: error("Observer = null")
        )
    }

    fun unregisterObserver() {
        observer?.let { context.contentResolver.unregisterContentObserver(it) }
    }

    @SuppressLint("Range")
    suspend fun getVideo(): List<Video> {
        val videos = mutableListOf<Video>()
        Log.d("Tag", "repo get video Started")
        withContext(Dispatchers.IO) {
            context.contentResolver.query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                null,
                null,
                null,
                null
            )?.use { cursor ->
                while (cursor.moveToNext()) {
                    val id = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media._ID))
                    val name =
                        cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME))
                    val size = cursor.getInt(cursor.getColumnIndex(MediaStore.Video.Media.SIZE))
                    val uri =
                        ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id)
                    videos += Video(id, name, size, uri)
                }
            }
        }
        return videos
    }

    suspend fun saveVideo(uri: Uri?, name: String, url: String) {
        withContext(Dispatchers.IO) {
            if (uri == null) {
                val videoUri = createEmptyVideoFile(name, url)
                try {
                    downloadVideo(url, videoUri)
                    makeVideoVisible(videoUri)
                } catch (t:Throwable){
                    deleteVideo(ContentUris.parseId(videoUri))
                    throw t.cause ?: IOException()
                }
            } else {
                try {
                    downloadVideo(url, uri)
                } catch (t: Throwable) {
                    deleteVideo(ContentUris.parseId(uri))
                    throw t.cause ?: IOException()
                }
            }
        }
    }

    private fun createEmptyVideoFile(name: String, url: String): Uri {
        val volume =
            if (haveQ()) MediaStore.VOLUME_EXTERNAL_PRIMARY
            else MediaStore.VOLUME_EXTERNAL
        val mType = getMIMEType(url)
        val videoCollectionUri = MediaStore.Video.Media.getContentUri(volume)
        val videoDetails = ContentValues().apply {
            put(MediaStore.Video.Media.DISPLAY_NAME, name)
            put(MediaStore.Video.Media.MIME_TYPE, mType)
            put(MediaStore.Video.Media.RELATIVE_PATH, "Movies")
            if (haveQ()) put(MediaStore.Video.Media.IS_PENDING, 1)
            Log.d("Tag", "video name :$name")
        }
        return context.contentResolver.insert(videoCollectionUri, videoDetails)!!
    }

    private suspend fun downloadVideo(url: String, uri: Uri) {
        context.contentResolver.openOutputStream(uri)?.use { outputStream ->
            Networking.downloadApi
                .getFile(url)
                .byteStream()
                .use { inputStream ->
                    inputStream.copyTo(outputStream)
                }
        }
    }

    private fun makeVideoVisible(videoUri: Uri) {
        if (haveQ().not()) return
        val videoDetails = ContentValues().apply {
            put(MediaStore.Video.Media.IS_PENDING, 0)
        }
        context.contentResolver.update(videoUri, videoDetails, null)
    }

    suspend fun deleteVideo(id: Long) {
        withContext(Dispatchers.IO) {
            Log.d("Tag", "delete video id: $id")
            val uri = ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id)
            context.contentResolver.delete(uri, null, null)
        }
    }
}