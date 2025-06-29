package com.example.tecnoguardapp.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.internal.Contexts
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreManager @Inject constructor(
    @ApplicationContext private val context: Context
){
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth_prefs")

    companion object{
        val KEY_CODE_VERIFIER = stringPreferencesKey("code_verifier")
        val KEY_ACCESS_TOKEN = stringPreferencesKey("access_token")
    }

    suspend fun saveCodeVerifier(value: String) {
        context.dataStore.edit { prefs ->
            prefs[KEY_CODE_VERIFIER] = value
        }
    }

    suspend fun getCodeVerifier(): String? {
        val prefs = context.dataStore.data.first()
        return prefs[KEY_CODE_VERIFIER]
    }

    suspend fun saveAccessToken(token: String) {
        context.dataStore.edit { prefs ->
            prefs[KEY_ACCESS_TOKEN] = token
        }
    }

    suspend fun getAccessToken(): String? {
        val prefs = context.dataStore.data.first()
        return prefs[KEY_ACCESS_TOKEN]
    }

    suspend fun clear() {
        context.dataStore.edit { it.clear() }
    }
}
