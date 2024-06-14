package com.example.composemvvmhiltretrofit.di

import android.content.Context
import com.example.composemvvmhiltretrofit.data.dp.AppDao
import com.example.composemvvmhiltretrofit.data.dp.AppDatabase
import com.example.composemvvmhiltretrofit.data.remote.ApiService
import com.example.composemvvmhiltretrofit.data.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl("https://api.jsonbin.io").addConverterFactory(
            GsonConverterFactory.create()
        ).build()
    }

    @Singleton
    @Provides
    fun providesApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    fun provideRepository(
        apiService: ApiService,
        localDataSource: AppDao
    ) =
        MainRepository(apiService, localDataSource = localDataSource)

    @Singleton
    @Provides
    fun providesDatabase(@ApplicationContext context: Context) = AppDatabase.getDatabase(context)

    @Singleton
    @Provides
    fun provideDao(db: AppDatabase) = db.appDao()


}