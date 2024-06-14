package com.example.composemvvmhiltretrofit.data.repository

import com.example.composemvvmhiltretrofit.data.dp.AppDao
import com.example.composemvvmhiltretrofit.data.models.MotivationDataEntity
import com.example.composemvvmhiltretrofit.data.remote.ApiService
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiService: ApiService,
    private val localDataSource: AppDao
) {

    suspend fun getMotivationList() = apiService.getMotivationList()

    fun getAllMotivation() = localDataSource.getAllMotivation()

    suspend fun insertMotivation(motivationDataEntity: MotivationDataEntity) =
        localDataSource.insertQrCode(motivationDataEntity = motivationDataEntity)
}