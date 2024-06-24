package com.app.list_device_app.domain.model

import android.graphics.drawable.Drawable
import java.io.Serializable

data class DeviceDetails(
    val imgPlatform: Drawable,
    val title: String,
    val sn: String,
    val macAddress: String,
    val firmware: String,
    val model: String
) : Serializable
