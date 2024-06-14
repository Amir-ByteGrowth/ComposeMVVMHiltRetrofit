package com.example.composemvvmhiltretrofit.di

import com.example.composemvvmhiltretrofit.data.remote.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    fun providesApiService(retrofit: Retrofit) : ApiService = retrofit.create(ApiService::class.java)
}