package com.skillbox.github.repositories

import android.util.Log
import com.skillbox.github.Network.Networking
import com.skillbox.github.data.StatusCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class DetailInfoRepo {

    suspend fun checkStar(
        nameRepo: String,
        nameOwner: String
    ): Boolean {

        return suspendCancellableCoroutine { continuation ->
            Networking.githubApi.checkStar(nameOwner, nameRepo).enqueue(object : Callback<Unit> {
                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    if (continuation.isActive) {
                        when (response.code()) {
                            StatusCode.SUCCESSFUL.statusCode -> continuation.resume(true)
                            StatusCode.NOTFOUND.statusCode -> continuation.resume(false)
                            else -> continuation.resumeWithException(Throwable("Other exception"))
                        }
                    }
                }

                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    continuation.resumeWithException(Throwable("Other exception"))
                }
            }
            )
        }
    }

    suspend fun addStar(
        nameRepo: String,
        nameOwner: String
    ) {
        withContext(Dispatchers.IO) { Networking.githubApi.addStar(nameOwner, nameRepo) }
        logger("Start addStar fun on thread = ${Thread.currentThread().name}")
    }

    suspend fun deleteStar(
        nameRepo: String,
        nameOwner: String
    ) {
        withContext(Dispatchers.IO) { Networking.githubApi.deleteStar(nameOwner, nameRepo) }
        logger("Start deleteStar fun on thread = ${Thread.currentThread().name}")
    }

    private fun logger(message: String) {
        Log.d("DetailInfo", message)
    }
}
