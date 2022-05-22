package com.skillbox.github.repositories

import com.skillbox.github.Network.Networking
import com.skillbox.github.data.Follows
import com.skillbox.github.data.RemoteUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CurrentUserRepo {
    suspend fun getUserData(): RemoteUser {
        return withContext(Dispatchers.IO) { Networking.githubApi.showCurrentUserInfo() }
    }

    suspend fun getFollows(): List<Follows> {
        return withContext(Dispatchers.IO) { Networking.githubApi.getUserFollows() }
    }
}