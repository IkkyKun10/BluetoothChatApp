package com.riezki.bluetoothchatapp.domain.util

import com.riezki.bluetoothchatapp.domain.model.BluetoothMessage

/**
 * @author riezkymaisyar
 */

sealed interface ConnectionResult {
    data object ConnectionEstablished: ConnectionResult
    data class TransferSucceeded(val message: BluetoothMessage) : ConnectionResult
    data class Error(val message: String): ConnectionResult
}