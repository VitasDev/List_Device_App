package com.app.list_device_app.presentation

import com.app.list_device_app.data.remote.dto.DeviceBase

sealed class DeviceUiState {
    data class Success(val deviceBases: List<DeviceBase>) : DeviceUiState()
    data class Error(val exception: Throwable) : DeviceUiState()
    data object Loading : DeviceUiState()
    data object Empty : DeviceUiState()
}