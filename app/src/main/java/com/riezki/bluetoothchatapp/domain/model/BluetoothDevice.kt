package com.riezki.bluetoothchatapp.domain.model

data class BluetoothDevice(
    val name: String? = null,
    val address: String,
    val rssi: Int? = null,
    val bondState: Int? = null
)

typealias BluetoothDeviceDomain = BluetoothDevice
