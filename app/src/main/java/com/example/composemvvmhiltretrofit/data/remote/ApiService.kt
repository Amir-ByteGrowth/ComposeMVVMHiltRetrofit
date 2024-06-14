package com.example.composemvvmhiltretrofit.data.remote

import com.example.composemvvmhiltretrofit.data.models.MotivationDataItem
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.HeaderMap
import retrofit2.http.POST
 interface ApiService {


    @GET("/v3/b/666ad9ace41b4d34e402b784?meta=false")
    suspend fun getMotivationList(
        @Header("X-MASTER-KEY") header: String ="\$2a\$10\$1h7.xxnkJQ59UDIiq2QlrOcOCw4ocKVl6Qfo3Q9W13PnY8DoFxWeq",
    ): Response<List<MotivationDataItem>>
}