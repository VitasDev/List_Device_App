package com.app.list_device_app.data.remote.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "device_table")
data class DeviceBase(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @SerializedName("PK_Device")
    val pkDevice: Int,
    @SerializedName("MacAddress")
    val macAddress: String,
    @SerializedName("Firmware")
    val firmware: String,
    @SerializedName("Platform")
    val platform: String,
    var title: String
): Serializable
