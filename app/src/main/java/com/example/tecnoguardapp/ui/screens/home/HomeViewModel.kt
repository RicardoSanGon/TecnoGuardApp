package com.example.tecnoguardapp.ui.screens.home

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tecnoguardapp.data.model.OpenDoor
import com.example.tecnoguardapp.data.network.BusinessApiClient
import com.example.tecnoguardapp.utils.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    @Named("business") private val businessApi: BusinessApiClient,
    private val dataStoreManager: DataStoreManager,
) : ViewModel() {

    private val _cerradaName = MutableLiveData<String?>()
    val cerradaName: LiveData<String?> = _cerradaName

    private val _areButtonsEnabled = MutableLiveData(true)
    val areButtonsEnabled: LiveData<Boolean> = _areButtonsEnabled


    fun disableButtonsForSeconds(seconds: Long) {
        _areButtonsEnabled.value = false
        viewModelScope.launch {
            delay(seconds * 1000)
            _areButtonsEnabled.value = true
        }
    }

    suspend fun getCerradaName(){
        try {
            _cerradaName.value = dataStoreManager.getUserData()?.family_group?.cerrada?.group_name
        }catch(e: Exception){
            Log.e("CERRADA", e.message!!)
            _cerradaName.value = null
        }
    }

    suspend fun openDoor(door: String) {
        val doorData = OpenDoor(door)
        val token = dataStoreManager.getAccessToken()
        val request = businessApi.abrirPuerta(doorData)
        if (request.isSuccessful) {
            Log.e("Puerta", "Body recibido: ${request.body()}")
            Toast.makeText(context, request.body()?.message, Toast.LENGTH_SHORT).show()
        } else {
            Log.e("Puerta", "Error body: ${request.errorBody()?.string()}")
        }
    }
}