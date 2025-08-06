package com.example.tecnoguardapp.ui.screens.home

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.tecnoguardapp.data.model.OpenDoor
import com.example.tecnoguardapp.data.network.BusinessApiClient
import com.example.tecnoguardapp.utils.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    @Named("business") private val businessApi: BusinessApiClient,
    private val dataStoreManager: DataStoreManager,
) : ViewModel() {

    suspend fun openCarDoor(door: String) {
        val doorData = OpenDoor(door)
        val token = dataStoreManager.getAccessToken()
        val request = businessApi.abrirPuerta("Bearer ${token}", doorData)
        if (request.isSuccessful) {
            Log.e("Puerta", "Body recibido: ${request.body()}")
            Toast.makeText(context, request.body()?.message, Toast.LENGTH_SHORT).show()
        } else {
            Log.e("Puerta", "Error body: ${request.errorBody()?.string()}")
        }
    }
}