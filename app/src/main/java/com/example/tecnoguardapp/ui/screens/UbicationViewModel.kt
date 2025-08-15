package com.example.tecnoguardapp.ui.screens

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.location.Location as AndroidLocation
import androidx.lifecycle.ViewModel
import com.example.tecnoguardapp.utils.DataStoreManager
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class UbicationViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    @SuppressLint("MissingPermission")
    suspend fun obtenerUbicacionActual(context: Context): AndroidLocation? {
        val fused = LocationServices.getFusedLocationProviderClient(context)
        val cts = CancellationTokenSource()
        return try {
            // Lectura “fresca”, luego fallback a cache
            fused.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, cts.token).await()
                ?: fused.lastLocation.await()
        } catch (e: Exception) {
            try { fused.lastLocation.await() } catch (_: Exception) { null }
        } finally {
            cts.cancel()
        }
    }

    suspend fun estaCerca(
        latActual: Double,
        lonActual: Double,
        distanciaMaxMetros: Float = 5f
    ): Boolean {
        Log.d("LATITUD ACTUAL", latActual.toString())
        Log.d("LONGITUD ACTUAL", lonActual.toString())
        val userData = dataStoreManager.getUserData()
        val entrada = userData?.family_group?.cerrada?.localidades_entradas?.firstOrNull()
        val latObjetivo = entrada?.latitud
        val lonObjetivo = entrada?.longitud
        if (latObjetivo == null || lonObjetivo == null) return false

        val actual = AndroidLocation("").apply {
            latitude = latActual
            longitude = lonActual
        }
        val objetivo = AndroidLocation("").apply {
            latitude = latObjetivo
            longitude = lonObjetivo
        }

        val distancia = actual.distanceTo(objetivo) // metros
        return distancia <= distanciaMaxMetros
    }
}
