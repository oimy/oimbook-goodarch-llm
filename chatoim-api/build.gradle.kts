plugins {
    kotlin("jvm") version "2.3.10"
    kotlin("kapt") version "2.3.10"
    kotlin("plugin.spring") version "2.3.10"
    kotlin("plugin.jpa") version "2.3.10"
    id("org.springframework.boot") version "4.0.2"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "oimbook"
version = "0.0.1-SNAPSHOT"
description = "Demo project for Spring Boot"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // spring
    implementation(group = "org.springframework.boot", name = "spring-boot-starter-web")
    implementation(group = "org.springframework.boot", name = "spring-boot-starter-validation")
    implementation(group = "org.springframework", name = "spring-webflux")
    implementation(group = "io.projectreactor.netty", name = "reactor-netty")

    // database
    implementation(group = "org.springframework.boot", name = "spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // kotlin
    implementation(group = "org.jetbrains.kotlin", name = "kotlin-reflect")
    implementation(group = "org.jetbrains.kotlinx", name = "kotlinx-coroutines-core")
    implementation(group = "org.jetbrains.kotlinx", name = "kotlinx-coroutines-reactor")

    // test
    runtimeOnly("com.h2database:h2")
    testImplementation(group = "org.springframework.boot", name = "spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict", "-Xannotation-default-target=param-property")
    }
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
