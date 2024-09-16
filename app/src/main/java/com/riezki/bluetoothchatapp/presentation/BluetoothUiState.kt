package com.riezki.bluetoothchatapp.presentation

import com.riezki.bluetoothchatapp.domain.model.BluetoothDeviceDomain

data class BluetoothUiState(
    val scannedDevices: List<BluetoothDeviceDomain> = emptyList(),
    val pairedDevices: List<BluetoothDeviceDomain> = emptyList()
)
