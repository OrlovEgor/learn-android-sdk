package com.skillbox.github.Network

import android.util.Log

object AuthToken {

    private lateinit var accessToken: String

    fun setToken(token: String) {
        accessToken = token
        Log.d("TOKEN", "SetToken = $token")
        Log.d("TOKEN", "NewToken = $accessToken")
    }

    fun getToken(): String {
        Log.d("TOKEN", "GetToken = $accessToken")
        return accessToken
    }
}