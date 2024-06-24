package com.app.list_device_app.data.remote

import com.app.list_device_app.data.remote.dto.ResponseDevice
import retrofit2.http.GET

interface ApiService {

    @GET("/test_android/items.test")
    suspend fun getDevices(): ResponseDevice
}