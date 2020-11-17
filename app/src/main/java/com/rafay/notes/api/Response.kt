package com.rafay.notes.api

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Response<T>(val message: String?, val data: T?)