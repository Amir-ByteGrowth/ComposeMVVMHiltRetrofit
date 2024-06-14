package com.example.composemvvmhiltretrofit.data.remote.repository

import com.example.composemvvmhiltretrofit.data.remote.ApiService
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getMotivationList() = apiService.getMotivationList()
}