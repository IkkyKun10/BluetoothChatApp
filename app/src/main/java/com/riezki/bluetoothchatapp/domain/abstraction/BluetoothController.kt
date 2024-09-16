package com.riezki.bluetoothchatapp.domain.abstraction

import com.riezki.bluetoothchatapp.domain.model.BluetoothDeviceDomain
import kotlinx.coroutines.flow.StateFlow

/**
 * @author riezky maisyar
 */

interface BluetoothController {

    val scannedDevices: StateFlow<List<BluetoothDeviceDomain>>
    val pairedDevices: StateFlow<List<BluetoothDeviceDomain>>

    fun startDiscovery()
    fun stopDiscovery()
    fun release()
}