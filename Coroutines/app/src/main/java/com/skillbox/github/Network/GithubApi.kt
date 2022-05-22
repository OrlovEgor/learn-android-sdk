package com.skillbox.github.Network

import com.skillbox.github.data.Follows
import com.skillbox.github.data.RemoteRepository
import com.skillbox.github.data.RemoteUser
import com.squareup.moshi.Json
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface GithubApi {

    @GET("user")
    suspend fun showCurrentUserInfo(): RemoteUser

    @GET("user/following")
    suspend fun getUserFollows(): List<Follows>

    @GET("repositories")
    fun getRepositories(): Call<List<RemoteRepository>>

    @GET("/user/starred/{owner}/{repo}")
    fun checkStar(
        @Path("owner") ownerName: String,
        @Path("repo") repository: String
    ): Call<Unit>

    @PUT("/user/starred/{owner}/{repo}")
    suspend fun addStar(
        @Path("owner") ownerName: String,
        @Path("repo") repository: String
    )

    @DELETE("/user/starred/{owner}/{repo}")
    suspend fun deleteStar(
        @Path("owner") ownerName: String,
        @Path("repo") repository: String
    )
}