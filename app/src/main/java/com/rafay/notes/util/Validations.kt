package com.rafay.notes.util

fun String.isEmail() = Regex("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}\$").matches(this)
