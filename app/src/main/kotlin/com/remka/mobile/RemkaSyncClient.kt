package com.remka.mobile

import java.net.HttpURLConnection
import java.net.URL

class RemkaSyncClient(
    private val baseUrl: String,
    private val token: String
) {
    fun isConfigured(): Boolean =
        baseUrl.isNotBlank()

    fun uploadEncryptedSnapshot(accountId: String, encryptedPayload: String): Boolean {
        if (!isConfigured() || accountId.isBlank()) {
            return false
        }

        val endpoint = URL("${baseUrl.trimEnd('/')}/sync/${accountId.encodePathSegment()}")
        val connection = (endpoint.openConnection() as HttpURLConnection).apply {
            requestMethod = "PUT"
            connectTimeout = 7_000
            readTimeout = 7_000
            doOutput = true
            setRequestProperty("Content-Type", "text/plain; charset=utf-8")
            if (token.isNotBlank()) {
                setRequestProperty("Authorization", "Bearer $token")
            }
        }

        return try {
            connection.outputStream.use { output ->
                output.write(encryptedPayload.encodeToByteArray())
            }
            connection.responseCode in 200..299
        } catch (_: Exception) {
            false
        } finally {
            connection.disconnect()
        }
    }
}

private fun String.encodePathSegment(): String =
    buildString {
        this@encodePathSegment.forEach { char ->
            when {
                char.isLetterOrDigit() || char == '-' || char == '_' || char == '.' -> append(char)
                else -> append('_')
            }
        }
    }.ifBlank { "anonymous" }
