package com.example.tecnoguardapp.ui.screens

import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import com.example.tecnoguardapp.ui.components.mjpeg.MjpegInputStream
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

@Composable
fun MjpegStreamView(
    streamUrl: String,
    modifier: Modifier = Modifier,
    reconnectIntervalMs: Long = 2000L
) {
    var imageBitmap by remember { mutableStateOf<androidx.compose.ui.graphics.ImageBitmap?>(null) }
    var statusText by remember { mutableStateOf("Conectando...") }

    // Keep a stable OkHttp client
    val client = remember {
        OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .build()
    }

    // Start streaming in background and update imageBitmap
    LaunchedEffect(streamUrl) {
        withContext(Dispatchers.IO) {
            while (isActive) {
                var call: Call? = null
                var response: Response? = null
                try {
                    val request = Request.Builder()
                        .url(streamUrl)
                        .header("Connection", "keep-alive")
                        .build()
                    call = client.newCall(request)
                    response = call.execute()
                    if (!response.isSuccessful) {
                        withContext(Dispatchers.Main) {
                            statusText = "Error HTTP: ${response.code()}"
                        }
                        response.close()
                        delay(reconnectIntervalMs)
                        continue
                    }

                    val body = response.body()
                    if (body == null) {
                        withContext(Dispatchers.Main) {
                            statusText = "Sin cuerpo en la respuesta"
                        }
                        response.close()
                        delay(reconnectIntervalMs)
                        continue
                    }

                    withContext(Dispatchers.Main) {
                        statusText = "Recibiendo..."
                    }
                    val inputStream = body.byteStream()
                    val mjpeg = MjpegInputStream(inputStream)

                    // read frames until stream ends or coroutine cancelled
                    while (isActive) {
                        val frameBytes = mjpeg.readMjpegFrame() ?: break
                        val bmp = BitmapFactory.decodeByteArray(frameBytes, 0, frameBytes.size)
                        if (bmp != null) {
                            withContext(Dispatchers.Main) {
                                imageBitmap = bmp.asImageBitmap()
                            }
                        }
                    }

                    mjpeg.close()
                    response.close()
                    withContext(Dispatchers.Main) {
                        statusText = "Reconectando..."
                    }
                    delay(reconnectIntervalMs)
                } catch (e: Exception) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main) {
//                        statusText = "Error: ${e.localizedMessage ?: e::class.simpleName}"
                        Log.d("CAMARA", "Error: ${e.localizedMessage ?: e::class.simpleName}")
                        statusText = "No se puede mostrar la cámara en este momento. Por favor, verifica tu conexión o inténtalo más tarde."
                    }
                    try {
                        response?.close()
                    } catch (_: Exception) {
                    }
                    try {
                        call?.cancel()
                    } catch (_: Exception) {
                    }
                    delay(reconnectIntervalMs)
                }
            }
        }
    }

    Box(modifier = modifier) {
        if (imageBitmap != null) {
            Image(
                bitmap = imageBitmap!!,
                contentDescription = "Camera stream",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        } else {
            // placeholder while connecting / error message
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                Text(text = statusText, color = Color.White)
            }
        }
    }
}