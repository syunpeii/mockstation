package com.github.syunpeii.mockstation.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

expect object DataStoreFactory {

    /**
     * Create a DataStore instance for the current platform
     *
     * @return DataStore instance configured for the platform
     */
    fun createDataStore(): DataStore<Preferences>
}
