package com.app.list_device_app.presentation.device_list

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.list_device_app.data.remote.dto.DeviceBase
import com.app.list_device_app.data.repository.MainRepository
import com.app.list_device_app.presentation.DeviceUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepository: MainRepository, private val applicationContext: Context) : ViewModel() {

    private val _deviceUiState = MutableStateFlow<DeviceUiState>(DeviceUiState.Empty)
    val deviceUiState: StateFlow<DeviceUiState> = _deviceUiState
    private var launchInitial: Boolean = true

    init {
        readAllDevicesFromDb()
    }

    private fun fetchDevicesFromApi() =
        viewModelScope.launch {
            mainRepository.getSortedDevices(applicationContext).onStart {
                _deviceUiState.value = DeviceUiState.Loading
            }.catch {
                _deviceUiState.value = DeviceUiState.Error(it)
            }.collect {
                _deviceUiState.value = DeviceUiState.Success(it)
                addDevicesToDb(it)
            }
        }


    private fun readAllDevicesFromDb() {
        viewModelScope.launch {
            _deviceUiState.value = DeviceUiState.Loading
            mainRepository.readAllDevices.collect {
                if (it.isNotEmpty()) {
                    _deviceUiState.value = DeviceUiState.Success(it)
                } else if (launchInitial) {
                    launchInitial = false
                    fetchDevicesFromApi()
                } else {
                    _deviceUiState.value = DeviceUiState.Empty
                }
            }
        }
    }

    private fun addDevicesToDb(devices: List<DeviceBase>) {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepository.addDevices(devices)
        }
    }

    fun deleteDeviceFromDb(device: DeviceBase) {
        launchInitial = false
        viewModelScope.launch(Dispatchers.IO) {
            mainRepository.deleteDevice(device)
        }
    }

    private suspend fun deleteAllDevicesFromDb() {
        launchInitial = false
        mainRepository.deleteAllDevices()
    }

    fun updateDataDevices() {
        viewModelScope.launch(Dispatchers.IO) {
            deleteAllDevicesFromDb()
        }.invokeOnCompletion {
            fetchDevicesFromApi()
        }
    }
}