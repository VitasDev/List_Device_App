package com.app.list_device_app.data.local

import androidx.room.*
import com.app.list_device_app.data.remote.dto.DeviceBase
import kotlinx.coroutines.flow.Flow

@Dao
interface DeviceDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addDevice(devices: List<DeviceBase>)

    @Query("SELECT * FROM device_table")
    fun readAllDevices(): Flow<List<DeviceBase>>

    @Delete
    suspend fun deleteDevice(device: DeviceBase)

    @Query("DELETE FROM device_table")
    suspend fun deleteAllDevices()
}