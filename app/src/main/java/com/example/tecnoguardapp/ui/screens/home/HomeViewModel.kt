package com.example.tecnoguardapp.ui.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.tecnoguardapp.data.model.OpenDoor
import com.example.tecnoguardapp.data.network.BusinessApiClient
import com.example.tecnoguardapp.utils.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class HomeViewModel @Inject constructor(
    @Named("business") private val businessApi: BusinessApiClient,
    private val dataStoreManager: DataStoreManager,
) : ViewModel() {

    suspend fun openCarDoor(){
        val doorData = OpenDoor("A")
        val token = dataStoreManager.getAccessToken()
        val request = businessApi.abrirPuerta("Bearer ${token}", doorData)
        if(request.isSuccessful){
            Log.e("Puerta", "Body recibido: ${request.body()}")
        }
        else {
            Log.e("Puerta", "Error body: ${request.errorBody()?.string()}")
        }
    }
}