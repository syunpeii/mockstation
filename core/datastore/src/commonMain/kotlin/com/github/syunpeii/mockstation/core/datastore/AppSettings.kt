package com.github.syunpeii.mockstation.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AppSettings(private val dataStore: DataStore<Preferences>) {

    val baseUrl: Flow<String> = dataStore.data.map { preferences ->
        preferences[BASE_URL] ?: DEFAULT_BASE_URL
    }

    val isLoggingEnabled: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[LOGGING_ENABLED] ?: true
    }

    suspend fun setBaseUrl(url: String) {
        dataStore.edit { preferences ->
            preferences[BASE_URL] = url
        }
    }

    suspend fun setLoggingEnabled(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[LOGGING_ENABLED] = enabled
        }
    }

    suspend fun clear() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    companion object {
        private val BASE_URL = stringPreferencesKey("base_url")
        private val LOGGING_ENABLED = booleanPreferencesKey("logging_enabled")
        private const val DEFAULT_BASE_URL = "http://localhost:8080"
    }
}
