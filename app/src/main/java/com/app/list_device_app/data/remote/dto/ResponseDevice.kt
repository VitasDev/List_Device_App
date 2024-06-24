package com.app.list_device_app.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ResponseDevice(
    @SerializedName("Devices")
    val deviceBases: List<DeviceBase>
)