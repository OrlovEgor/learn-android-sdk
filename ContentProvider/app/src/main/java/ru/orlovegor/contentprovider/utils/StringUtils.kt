package ru.orlovegor.contentprovider.utils

import java.util.regex.Pattern

val phonePattern: Pattern = Pattern.compile("^\\+?[0-9]{3}-?[0-9]{1,12}\$")
val emailPattern: Pattern = Pattern.compile(
    "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
            "\\@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
            ")+"
)