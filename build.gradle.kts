import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

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

liquibase {
    activities.register("main") {
        this.arguments = mapOf(
                "logLevel" to "info",
                "changeLogFile" to "src/main/resources/db/generated/generated_changelog.postgres.sql",
                "url" to "jdbc:postgresql://localhost:5432/hh",
                "username" to "postgres",
                "password" to "0",
                "driver" to "org.postgresql.Driver"
        )
    }
    runList = "main"
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
    /**KOTLIN*/
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    /**SECURITY*/
    implementation("org.springframework.boot:spring-boot-starter-security")
    /**DATA-BASE*/
    implementation( "org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("org.postgresql:postgresql")
    implementation("org.hibernate:hibernate-jcache:5.4.32.Final")
    runtimeOnly("org.ehcache:ehcache:3.10.8")
    /**LIQUIBASE*/
    implementation("org.liquibase:liquibase-core:4.16.1")
    /**LIQUIBASE-PLUGIN*/
    liquibaseRuntime("org.liquibase:liquibase-core:4.16.1")
    liquibaseRuntime("org.liquibase:liquibase-groovy-dsl:3.0.0")
    liquibaseRuntime("info.picocli:picocli:4.6.1")
    liquibaseRuntime("org.postgresql:postgresql")
    /**TEST*/
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
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
