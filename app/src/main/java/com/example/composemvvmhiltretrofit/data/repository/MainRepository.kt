package com.example.composemvvmhiltretrofit.data.repository

import com.example.composemvvmhiltretrofit.data.dp.AppDao
import com.example.composemvvmhiltretrofit.data.models.MotivationDataEntity
import com.example.composemvvmhiltretrofit.data.models.Suggestion
import com.example.composemvvmhiltretrofit.data.remote.ApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiService: ApiService,
    private val localDataSource: AppDao
) {

    suspend fun getMotivationList() = apiService.getMotivationList()

    fun getAllMotivation() = localDataSource.getAllMotivation()

    suspend fun insertMotivation(motivationDataEntity: MotivationDataEntity) =
        localDataSource.insertQrCode(motivationDataEntity = motivationDataEntity)


    suspend fun insertSuggestion(suggestion: Suggestion) {
        localDataSource.insertSuggestion(suggestion)
    }

    suspend fun getSuggestions(handle: String = "a"): List<Suggestion> {
        return localDataSource.getSuggestions(handle)
    }

     fun getSuggestionsList(handle: String = "a"): Flow<List<Suggestion>> {
        return localDataSource.getSuggestionsList()
    }
}