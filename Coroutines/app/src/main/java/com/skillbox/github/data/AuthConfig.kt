package com.skillbox.github.data

import net.openid.appauth.ResponseTypeValues

object AuthConfig {

    const val AUTH_URI = "https://github.com/login/oauth/authorize"
    const val TOKEN_URI = "https://github.com/login/oauth/access_token"
    const val RESPONSE_TYPE = ResponseTypeValues.CODE
    const val SCOPE = "user,repo"

    const val CLIENT_ID = "6a9a645d80d634d4fb23"
    const val CLIENT_SECRET = "139652dfea7ca964468675a5161c6bc0f5e6f6d6"
    const val CALLBACK_URL = "skillbox://skillbox.ru/callback"
}