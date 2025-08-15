package com.example.tecnoguardapp.ui.components.mjpeg

import java.io.BufferedInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream

class MjpegInputStream(input: InputStream) {
    private val bis = BufferedInputStream(input, 16 * 1024)

    /**
     * Reads the next JPEG frame as a byte array. Returns null on EOF.
     */
    fun readMjpegFrame(): ByteArray? {
        val buffer = ByteArray(8192)
        val baos = ByteArrayOutputStream()
        var started = false
        var prev = -1

        while (true) {
            val r = bis.read(buffer)
            if (r == -1) {
                // stream ended
                return null
            }
            for (i in 0 until r) {
                val b = buffer[i].toInt() and 0xFF
                if (!started) {
                    // look for SOI 0xFF 0xD8
                    if (prev == 0xFF && b == 0xD8) {
                        // write SOI bytes
                        baos.write(0xFF)
                        baos.write(0xD8)
                        started = true
                    }
                } else {
                    baos.write(b)
                    // look for EOI 0xFF 0xD9
                    if (prev == 0xFF && b == 0xD9) {
                        return baos.toByteArray()
                    }
                }
                prev = b
            }
        }
    }

    fun close() {
        try {
            bis.close()
        } catch (_: Exception) {
        }
    }
}