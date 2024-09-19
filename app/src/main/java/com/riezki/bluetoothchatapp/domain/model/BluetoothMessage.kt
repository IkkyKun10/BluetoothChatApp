package com.riezki.bluetoothchatapp.domain.model

data class BluetoothMessage(
    val message: String,
    val senderName: String,
    val isFromLocal: Boolean,
)
