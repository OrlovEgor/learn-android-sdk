package ru.orlovegor.clientprovider

fun checkEmptyOrNumber(text: String) =
    if (text.isNotBlank()) {
        text.toIntOrNull() is Int
    } else false