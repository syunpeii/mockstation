package com.github.syunpeii.mockstation.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

class ConnectionSettings(
    private val dataStore: DataStore<Preferences>,
) {

    val connections: Flow<List<ServerConnection>> = dataStore.data.map { preferences ->
        val connectionsJson = preferences[CONNECTIONS_KEY] ?: "[]"
        try {
            Json.decodeFromString(connectionsJson)
        } catch (e: Exception) {
            emptyList()
        }
    }

    val selectedConnectionId: Flow<String?> = dataStore.data.map { preferences ->
        preferences[SELECTED_CONNECTION_ID_KEY]
    }

    suspend fun addConnection(connection: ServerConnection) {
        dataStore.edit { preferences ->
            val connectionsJson = preferences[CONNECTIONS_KEY] ?: "[]"
            val connections = try {
                Json.decodeFromString<MutableList<ServerConnection>>(connectionsJson)
            } catch (e: Exception) {
                mutableListOf()
            }
            connections.add(connection)
            preferences[CONNECTIONS_KEY] = Json.encodeToString(connections)
        }
    }

    suspend fun updateConnection(connection: ServerConnection) {
        dataStore.edit { preferences ->
            val connectionsJson = preferences[CONNECTIONS_KEY] ?: "[]"
            val connections = try {
                Json.decodeFromString<MutableList<ServerConnection>>(connectionsJson)
            } catch (e: Exception) {
                mutableListOf()
            }
            val index = connections.indexOfFirst { it.id == connection.id }
            if (index >= 0) {
                connections[index] = connection
            }
            preferences[CONNECTIONS_KEY] = Json.encodeToString(connections)
        }
    }

    suspend fun deleteConnection(id: String) {
        dataStore.edit { preferences ->
            val connectionsJson = preferences[CONNECTIONS_KEY] ?: "[]"
            val connections = try {
                Json.decodeFromString<MutableList<ServerConnection>>(connectionsJson)
            } catch (e: Exception) {
                mutableListOf()
            }
            connections.removeAll { it.id == id }
            preferences[CONNECTIONS_KEY] = Json.encodeToString(connections)

            val selectedId = preferences[SELECTED_CONNECTION_ID_KEY]
            if (selectedId == id) {
                preferences.remove(SELECTED_CONNECTION_ID_KEY)
            }
        }
    }

    suspend fun setSelectedConnection(id: String) {
        dataStore.edit { preferences ->
            preferences[SELECTED_CONNECTION_ID_KEY] = id
        }
    }

    companion object {
        private val CONNECTIONS_KEY = stringPreferencesKey("connections_list")
        private val SELECTED_CONNECTION_ID_KEY = stringPreferencesKey("selected_connection_id")
    }
}

@Serializable
data class ServerConnection(
    val id: String,
    val name: String,
    val url: String,
    val description: String,
)
