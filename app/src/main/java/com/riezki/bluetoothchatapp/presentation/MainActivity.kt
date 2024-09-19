package com.riezki.bluetoothchatapp.presentation

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.riezki.bluetoothchatapp.domain.model.BluetoothDeviceDomain
import com.riezki.bluetoothchatapp.presentation.components.ChatScreen
import com.riezki.bluetoothchatapp.presentation.components.DeviceScreen
import com.riezki.bluetoothchatapp.presentation.ui.theme.BluetoothChatAppTheme
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalPermissionsApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val bluetoothManager by lazy {
        getSystemService(BluetoothManager::class.java)
    }

    private val bluetoothAdapter by lazy {
        bluetoothManager?.adapter
    }

    private val isBluetoothEnabled: Boolean
        get() = bluetoothAdapter?.isEnabled == true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val enableBluetoothLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {}

        println("isBluetoothEnabled: " + isBluetoothEnabled)

        val permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { perms ->
            val canEnableBluetooth = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                perms[Manifest.permission.BLUETOOTH_CONNECT] == true
            } else true

            println("canEnableBluetooth: " + canEnableBluetooth)

            if (canEnableBluetooth && !isBluetoothEnabled) {
                enableBluetoothLauncher.launch(
                    Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                )
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            permissionLauncher.launch(
                arrayOf(
                    Manifest.permission.BLUETOOTH_SCAN,
                    Manifest.permission.BLUETOOTH_CONNECT,
                )
            )
        } else {
            permissionLauncher.launch(
                arrayOf(
                    Manifest.permission.BLUETOOTH,
                    Manifest.permission.BLUETOOTH_ADMIN,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                )
            )
        }

        setContent {
            val bluetoothPermissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                rememberMultiplePermissionsState(
                    permissions = listOf(
                        Manifest.permission.BLUETOOTH_CONNECT,
                        Manifest.permission.BLUETOOTH_SCAN
                    ),
                    onPermissionsResult = { perms ->
                        val canEnableBluetooth = perms[Manifest.permission.BLUETOOTH_CONNECT] == true
                        if (canEnableBluetooth && !isBluetoothEnabled) {
                            enableBluetoothLauncher.launch(
                                Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                            )
                        }
                    }
                )
            } else {
                rememberMultiplePermissionsState(permissions = listOf())
            }

            BluetoothChatAppTheme {
                val viewModel = hiltViewModel<BluetoothDeviceVM>()
                val state by viewModel.state.collectAsState()

                println("stateScanned: ${state.scannedDevices}")
                println("statePaired: ${state.pairedDevices}")

                LaunchedEffect(key1 = state.errorMessage) {
                    state.errorMessage?.let {
                        Toast.makeText(
                            this@MainActivity,
                            it,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                LaunchedEffect(key1 = state.isConnected) {
                    if (state.isConnected) {
                        Toast.makeText(
                            this@MainActivity,
                            "You're connected!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                Surface(
                    color = MaterialTheme.colorScheme.background,
                ) {
                    when {
                        state.isConnecting -> {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                CircularProgressIndicator()
                                Text(text = "Connecting...")
                            }
                        }

                        state.isConnected -> {
                            ChatScreen(
                                state = state,
                                onDisconnect = viewModel::disconnectFromDevice,
                                onSendMessage = viewModel::sendMessage
                            )
                        }

                        else -> {
                            DeviceScreen(
                                state = state,
                                onStartScan = viewModel::startScan,
                                onStopScan = viewModel::stopScan,
                                onDeviceClick = viewModel::connectToDevice,
                                onStartServer = viewModel::waitForIncomingPairConnections
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DeviceScreenPrev() {
    BluetoothChatAppTheme {
        val scannedList = (1..10).map {
            BluetoothDeviceDomain(name = "Device $it", address = "Address $it")
        }
        val pairedList = (1..10).map {
            BluetoothDeviceDomain(name = "Device $it", address = "Address $it")
        }

        DeviceScreen(
            state = BluetoothUiState(
                scannedDevices = scannedList,
                pairedDevices = pairedList
            ),
            onStartScan = {},
            onStopScan = {},
            onDeviceClick = {},
            onStartServer = {}
        )
    }
}