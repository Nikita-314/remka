plugins {
    kotlin("jvm")
    application
}

application {
    mainClass.set("com.remka.server.RemkaServerKt")
}

kotlin {
    jvmToolchain(22)
}
