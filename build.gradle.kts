import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
//import java.io.File

plugins {
    id("org.springframework.boot") version "2.5.1"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("org.liquibase.gradle") version "2.0.3"
    kotlin("plugin.allopen") version "1.4.32"
    kotlin("plugin.spring") version "1.5.21"
    kotlin("jvm") version "1.5.21"
}

group = "ru.k2"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

//ext {
//    var database = org.gradle.internal.impldep.org.yaml.snakeyaml.Yaml()
//            .loadAll(File("application.yml").inputStream()).first()
//}

//driver, class path, URL, and user authentication information
liquibase {
    activities.register("main") {
        this.arguments = kotlin.collections.hashMapOf(
//                "logLevel" to "info",
                "changeLogFile" to "src/main/resources/db/changelog.yml",
                "url" to "jdbc:postgresql://localhost:5432/univer",
                "username" to "postgres",
                "password" to "0",
                "driver" to "org.postgresql.Driver"
        )
    }
}

repositories {
    mavenCentral()
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.Embeddable")
    annotation("javax.persistence.MappedSuperclass")
}

dependencies {
    /**WEB*/
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    /**FRONTend*/
//    implementation("org.webjars:jquery:3.5.1")
//    implementation("org.webjars:bootstrap:4.6.0")
//    implementation("org.webjars:popper.js:1.16.0")
    /**KOTLIN*/
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    /**SECURITY*/
    implementation("org.springframework.boot:spring-boot-starter-security")
    /**DATA-BASE*/
    implementation( "org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("org.postgresql:postgresql")
    /**LIQUIBASE*/
    liquibaseRuntime("org.liquibase:liquibase-core:4.16.1")
    liquibaseRuntime("org.liquibase:liquibase-groovy-dsl:3.0.0")
    liquibaseRuntime("info.picocli:picocli:4.6.1")
    liquibaseRuntime("org.postgresql:postgresql")

    /**TEST*/
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    /**MISC*/
    developmentOnly("org.springframework.boot:spring-boot-devtools")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
