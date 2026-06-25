package com.remka.mobile

import com.remka.data.RemkaSnapshot
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.nio.file.AtomicMoveNotSupportedException
import java.nio.file.Files
import java.nio.file.StandardCopyOption

class AndroidRemkaStore(
    private val file: File
) {
    private val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
    }

    fun load(): RemkaSnapshot? {
        if (!file.exists()) {
            return null
        }

        val content = file.readText()
        if (content.isBlank()) {
            return null
        }

        return json.decodeFromString<RemkaSnapshot>(content)
    }

    fun save(snapshot: RemkaSnapshot) {
        file.parentFile?.mkdirs()
        val tempFile = file.resolveSibling("${file.name}.tmp")
        tempFile.writeText(json.encodeToString(snapshot))

        try {
            Files.move(
                tempFile.toPath(),
                file.toPath(),
                StandardCopyOption.REPLACE_EXISTING,
                StandardCopyOption.ATOMIC_MOVE
            )
        } catch (_: AtomicMoveNotSupportedException) {
            Files.move(
                tempFile.toPath(),
                file.toPath(),
                StandardCopyOption.REPLACE_EXISTING
            )
        }
    }
}
