import org.jetbrains.kotlin.gradle.dsl.Coroutines
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val logback_version: String by project
val ktor_version: String by project
val kotlin_version: String by project

plugins {
    application
    kotlin("jvm") version "1.4.0"
}

group = "com.gruzini"
version = "0.0.1-SNAPSHOT"

application {
    mainClassName = "com.gruzini.ApplicationKt"
}

repositories {
    mavenLocal()
    jcenter()
}

dependencies {
    //align versions of all kotlin components
    implementation("org.jetbrains.kotlin:kotlin-bom")

    //use the kotlin jdk8 standard library
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    //use ktor engine with netty embedded server
    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")

    //use lockback for logging
    implementation("ch.qos.logback:logback-classic:$logback_version")

    //use jackson as json
    implementation("io.ktor:ktor-jackson:$ktor_version")


    //use these test libraries - ktor for server request tests, junit for running tests and assertions
    testImplementation("io.ktor:ktor-server-tests:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}

kotlin.sourceSets["main"].kotlin.srcDirs("src")
kotlin.sourceSets["test"].kotlin.srcDirs("test")

sourceSets["main"].resources.srcDirs("resources")
sourceSets["test"].resources.srcDirs("testresources")
