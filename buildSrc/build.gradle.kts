/*
 * This file was generated by the Gradle 'init' task.
 */

plugins {
    // Support convention plugins written in Kotlin. Convention plugins are build scripts in 'src/main' that automatically become available as plugins in the main build.
    `kotlin-dsl`
}

repositories {
    // Use the plugin portal to apply community plugins in convention plugins.
    gradlePluginPortal()
}

// be explicit on the Java Version when multiple instances of the JKD are present
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}