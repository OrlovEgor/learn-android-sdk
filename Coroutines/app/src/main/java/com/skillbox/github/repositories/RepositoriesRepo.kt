package com.skillbox.github.repositories

import android.util.Log
import com.skillbox.github.Network.Networking
import com.skillbox.github.data.RemoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.lang.RuntimeException

class RepositoriesRepo {

    suspend fun getRepositories(): List<RemoteRepository> {
        return withContext(Dispatchers.IO) {
            Networking.githubApi.getRepositories().execute().body().orEmpty()
        }
    }
}

