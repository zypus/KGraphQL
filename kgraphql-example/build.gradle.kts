plugins {
    id("application")
}

val ktorVersion: String by project
val logbackVersion: String by project
val exposedVersion: String by project
val h2Version: String by project
val hikariVersion: String by project

application {
    mainClassName = "io.ktor.server.netty.EngineMain"
}

kotlin {
    jvm {
        compilations.all { kotlinOptions.jvmTarget = "11" }
    }

    sourceSets {
        val jvmMain by getting {
            dependencies {
                api(project(":kgraphql-ktor"))
                implementation("io.ktor:ktor-server-netty:$ktorVersion")
                implementation("io.ktor:ktor-auth:$ktorVersion")
                implementation("ch.qos.logback:logback-classic:$logbackVersion")
                implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
                implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
                implementation("org.jetbrains.exposed:exposed-java-time:$exposedVersion")
                implementation("com.h2database:h2:$h2Version")
                implementation("com.zaxxer:HikariCP:$hikariVersion")
            }
        }
    }
}

