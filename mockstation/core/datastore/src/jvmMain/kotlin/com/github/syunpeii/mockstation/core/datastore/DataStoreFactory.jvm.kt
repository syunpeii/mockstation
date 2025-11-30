package com.github.syunpeii.mockstation.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath

actual object DataStoreFactory {

    private const val DATASTORE_FILE_NAME = "mockstation.preferences_pb"

    actual fun createDataStore(): DataStore<Preferences> {
        val userHome = System.getProperty("user.home")
        val appDir = "$userHome/.mockstation"

        return PreferenceDataStoreFactory.createWithPath(
            produceFile = { "$appDir/$DATASTORE_FILE_NAME".toPath() },
        )
    }
}
