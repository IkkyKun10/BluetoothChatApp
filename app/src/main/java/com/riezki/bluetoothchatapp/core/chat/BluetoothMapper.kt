package com.riezki.bluetoothchatapp.core.chat

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import com.riezki.bluetoothchatapp.domain.model.BluetoothDeviceDomain

/**
 * @author riezky maisyar
 */

@SuppressLint("MissingPermission")
fun BluetoothDevice.toBluetoothDeviceDomain() : BluetoothDeviceDomain {
    return BluetoothDeviceDomain(
        name = name,
        address = address,
        bondState = bondState,
    )
}