package com.pberrueco.pmdmu3actividad

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.Flow

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "com.pberrueco.pmdmu3actividad")

object DataStoreManager {


    suspend fun saveUser(context: Context, userName: String, password: String) {
        val userNameKey = stringPreferencesKey("user_name")
        val passwordKey = stringPreferencesKey("password")

        context.dataStore.edit { editor ->
            editor[userNameKey] = userName
            editor[passwordKey] = password
        }
    }

    suspend fun getUser(context: Context): Flow<String> {
        val userNameKey = stringPreferencesKey("user_name")
        return context.dataStore.data.map { editor ->
            editor[userNameKey] ?: "Es nulo"
        }
    }

    suspend fun getPassword(context: Context): Flow<String> {
        val passwordKey = stringPreferencesKey("password")
        return context.dataStore.data.map { editor ->
            editor[passwordKey] ?: "Es nula la contraseña"
        }
    }

}