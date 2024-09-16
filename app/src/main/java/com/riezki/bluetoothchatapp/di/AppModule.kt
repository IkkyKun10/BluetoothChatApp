package com.riezki.bluetoothchatapp.di

import com.riezki.bluetoothchatapp.core.chat.AndroidBluetoothController
import com.riezki.bluetoothchatapp.domain.abstraction.BluetoothController
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author riezky maisyar
 */

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun provideBluetoothController(bluetoothController: AndroidBluetoothController): BluetoothController
}