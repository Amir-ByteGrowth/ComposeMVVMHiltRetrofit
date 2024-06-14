package com.example.composemvvmhiltretrofit.utils

import com.example.composemvvmhiltretrofit.data.remote.ResponseError
import com.google.gson.Gson

val gsonObj = Gson()

fun extractErrorMessage(errorMessagesJson: String): ResponseError {

    gsonObj.serializeNulls()
    val errorObj = gsonObj.fromJson(errorMessagesJson, ResponseError::class.java)
    val msg = errorObj.message

    return errorObj
}