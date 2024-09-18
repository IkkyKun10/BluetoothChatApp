package com.riezki.bluetoothchatapp.domain.util

/**
 * @author riezkymaisyar
 */

sealed interface ConnectionResult {
    data object ConnectionEstablished: ConnectionResult
    data class Error(val message: String): ConnectionResult
}