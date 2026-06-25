package com.remka.server

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpServer
import java.net.InetSocketAddress
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.exists
import kotlin.io.path.name
import kotlin.io.path.readText
import kotlin.io.path.writeText

private const val ENCRYPTED_PREFIX = "REMKA_ENCRYPTED_V1"

fun main() {
    val port = System.getenv("REMKA_PORT")?.toIntOrNull() ?: 8080
    val dataDir = Path.of(System.getenv("REMKA_DATA_DIR") ?: "remka-server-data").apply {
        createDirectories()
    }
    val token = System.getenv("REMKA_SERVER_TOKEN").orEmpty()
    val server = HttpServer.create(InetSocketAddress("0.0.0.0", port), 0)

    server.createContext("/health") { exchange ->
        exchange.respond(status = 200, body = "ok")
    }

    server.createContext("/sync") { exchange ->
        val accountId = exchange.requestURI.path
            .removePrefix("/sync/")
            .takeIf { path -> path.isNotBlank() && !path.contains("/") }

        if (accountId == null) {
            exchange.respond(status = 404, body = "not found")
            return@createContext
        }
        if (!exchange.isAuthorized(token)) {
            exchange.respond(status = 401, body = "unauthorized")
            return@createContext
        }

        val file = dataDir.resolve(accountId.safeFileName() + ".blob")
        when (exchange.requestMethod.uppercase()) {
            "GET" -> {
                if (!file.exists()) {
                    exchange.respond(status = 404, body = "not found")
                } else {
                    exchange.respond(status = 200, body = file.readText())
                }
            }

            "PUT" -> {
                val body = exchange.requestBody.readBytes().toString(StandardCharsets.UTF_8).trim()
                if (!body.startsWith(ENCRYPTED_PREFIX)) {
                    exchange.respond(status = 400, body = "payload must be encrypted")
                    return@createContext
                }

                file.writeText(body)
                exchange.respond(status = 200, body = "saved ${file.name}")
            }

            else -> exchange.respond(status = 405, body = "method not allowed")
        }
    }

    server.executor = null
    server.start()
    println("Remka sync server started on port $port, data dir: $dataDir")
}

private fun HttpExchange.isAuthorized(token: String): Boolean {
    if (token.isBlank()) {
        return true
    }

    val authHeader = requestHeaders.getFirst("Authorization").orEmpty()
    return authHeader == "Bearer $token"
}

private fun HttpExchange.respond(status: Int, body: String) {
    val bytes = body.toByteArray(StandardCharsets.UTF_8)
    sendResponseHeaders(status, bytes.size.toLong())
    responseBody.use { output ->
        output.write(bytes)
    }
}

private fun String.safeFileName(): String =
    filter { char -> char.isLetterOrDigit() || char == '-' || char == '_' || char == '.' }
        .ifBlank { "anonymous" }
