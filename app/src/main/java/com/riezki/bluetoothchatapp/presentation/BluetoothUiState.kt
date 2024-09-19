package com.riezki.bluetoothchatapp.presentation

import com.riezki.bluetoothchatapp.domain.model.BluetoothDeviceDomain
import com.riezki.bluetoothchatapp.domain.model.BluetoothMessage

data class BluetoothUiState(
    val scannedDevices: List<BluetoothDeviceDomain> = emptyList(),
    val pairedDevices: List<BluetoothDeviceDomain> = emptyList(),
    val isConnected: Boolean = false,
    val isConnecting: Boolean = false,
    val errorMessage: String? = "",
    val messages: List<BluetoothMessage> = emptyList()
)
