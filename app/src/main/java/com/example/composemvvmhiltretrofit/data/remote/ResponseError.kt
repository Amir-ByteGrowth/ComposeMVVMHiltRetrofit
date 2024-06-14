package com.example.composemvvmhiltretrofit.data.remote

data class ResponseError(
    val message: String?,
    val code: Int?,
    val accessToken: String?
)
