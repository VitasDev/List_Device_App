package com.app.list_device_app.data.repository

import android.content.Context
import com.app.list_device_app.R
import com.app.list_device_app.data.local.DeviceDao
import com.app.list_device_app.data.remote.ApiService
import com.app.list_device_app.data.remote.dto.DeviceBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiService: ApiService, private val deviceDao: DeviceDao) {

    val readAllDevices: Flow<List<DeviceBase>> = deviceDao.readAllDevices()

    suspend fun addDevices(devices: List<DeviceBase>) {
        deviceDao.addDevice(devices)
    }

    suspend fun deleteDevice(device: DeviceBase) {
        deviceDao.deleteDevice(device)
    }

    suspend fun deleteAllDevices() {
        deviceDao.deleteAllDevices()
    }

    fun getSortedDevices(context: Context): Flow<List<DeviceBase>> = flow {
        val sortedListDevice = apiService.getDevices().deviceBases.sortedBy {
            it.pkDevice
        }
        var numberOfItem = 1
        sortedListDevice.onEach { it.title = context.getString(R.string.home_number, numberOfItem++.toString()) }

        emit(sortedListDevice)
    }.flowOn(Dispatchers.IO)
}