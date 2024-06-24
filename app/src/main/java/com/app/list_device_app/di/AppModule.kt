package com.app.list_device_app.di

import android.app.Application
import android.content.Context
import com.app.list_device_app.common.Constants.BASE_URL
import com.app.list_device_app.data.local.DeviceDao
import com.app.list_device_app.data.local.DeviceDatabase
import com.app.list_device_app.data.remote.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesApiService(): ApiService =
        Retrofit.Builder()
            .run {
                baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }.create(ApiService::class.java)

    @Provides
    @Singleton
    fun providesDeviceDao(application: Application): DeviceDao =
        DeviceDatabase.getDatabase(application).deviceDao()

    @Provides
    @Singleton
    fun providesApplicationContext(application: Application): Context =
        application.applicationContext
}