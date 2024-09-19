package com.riezki.bluetoothchatapp.core.chat

import com.riezki.bluetoothchatapp.domain.model.BluetoothMessage

/**
 * @author riezkymaisyar
 */

fun String.toBluetoothMessage(isFromLocalUser: Boolean) : BluetoothMessage {
    val name = substringBeforeLast("#")
    val message = substringAfter("#")
    return BluetoothMessage(
        message = message,
        senderName = name,
        isFromLocal = isFromLocalUser
    )
}

fun BluetoothMessage.toByteArray() : ByteArray {
    return "$senderName#$message".encodeToByteArray()
}