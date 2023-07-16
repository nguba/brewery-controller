/*
 * This file was generated by the Gradle 'init' task.
 */

plugins {
    id("brewery.controller.java-application-conventions")
    id("org.springframework.boot") version "3.1.0"
}

apply(plugin = "io.spring.dependency-management")

val testContainersVersion = "1.18.3"

dependencies {
    implementation(project(":application"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.ghgande:j2mod:3.1.1")
    implementation("com.influxdb:influxdb-client-java:6.9.0")
    implementation("com.google.guava:guava:32.1.1-jre")

    testImplementation("org.testcontainers:testcontainers:${testContainersVersion}")
    testImplementation("org.testcontainers:influxdb:${testContainersVersion}")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

application {
    // Define the main class for the application.
    mainClass.set("adapter.inputs.server.BreweryServer")
}
