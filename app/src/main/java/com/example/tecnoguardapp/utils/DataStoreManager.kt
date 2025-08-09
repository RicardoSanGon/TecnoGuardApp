package com.example.tecnoguardapp.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.tecnoguardapp.data.responses.User.UserData
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth_prefs")
    private val gson = Gson()

    companion object {
        val KEY_CODE_VERIFIER = stringPreferencesKey("code_verifier")
        val KEY_ACCESS_TOKEN = stringPreferencesKey("access_token")
        val KEY_USER_DATA = stringPreferencesKey("user_data")
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

    suspend fun saveUserData(userData: UserData?) {
        val json = gson.toJson(userData)
        context.dataStore.edit { prefs ->
            prefs[KEY_USER_DATA] = json
        }
    }

    suspend fun getUserData(): UserData? {
        val prefs = context.dataStore.data.first()
        val json = prefs[KEY_USER_DATA]
        return json?.let { gson.fromJson(it, UserData::class.java) }
    }
}
