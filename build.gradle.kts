import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    java
    kotlin("jvm") version "1.3.72"
    id("com.github.johnrengelman.shadow") version "5.2.0"
}

group = "ktor-boot"
version = "1.0"

repositories {
    mavenCentral()
    jcenter()
    maven("https://dl.bintray.com/kotlin/ktor")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    val ktorVersion = "1.3.2"
    implementation("io.ktor", "ktor-server-netty", ktorVersion)
    implementation("io.ktor", "ktor-jackson", ktorVersion)
    implementation("io.ktor", "ktor-auth", ktorVersion)
    implementation("io.ktor", "ktor-auth-jwt", ktorVersion)

    // JavaTime serialization
    implementation("com.fasterxml.jackson.datatype", "jackson-datatype-jsr310", "2.10.2")

    // Test
    testImplementation("io.ktor", "ktor-server-test-host", ktorVersion)
    implementation("org.koin", "koin-ktor", "2.1.6")

    // Db
    implementation("org.postgresql", "postgresql", "42.2.14")
    implementation("com.zaxxer", "HikariCP", "3.4.5")
    implementation("com.vladsch.kotlin-jdbc", "kotlin-jdbc", "0.5.2")
}

application {
    mainClassName = "MainKt"
}

tasks.shadowJar {
    exclude("application.conf", "logback.xml")
    minimize()
    archiveBaseName.set("app")
    archiveClassifier.set("")
    archiveVersion.set("")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

sourceSets["main"].java { srcDir("src") }
sourceSets["main"].resources { srcDir("resources") }
sourceSets["test"].java { srcDir("test") }
sourceSets["test"].resources { srcDir("resources-test") }

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions { jvmTarget = "1.8" }
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions { jvmTarget = "1.8" }

tasks.withType<Test> {
    testLogging {
        exceptionFormat = TestExceptionFormat.FULL
        events = setOf(TestLogEvent.STARTED, TestLogEvent.SKIPPED, TestLogEvent.PASSED, TestLogEvent.FAILED)
        showStandardStreams = true
    }
}

