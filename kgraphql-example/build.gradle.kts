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

dependencies {
    compile(project(":kgraphql-ktor"))
    compile("io.ktor:ktor-server-netty:$ktorVersion")
    compile("io.ktor:ktor-auth:$ktorVersion")
    compile("ch.qos.logback:logback-classic:$logbackVersion")
    compile("org.jetbrains.exposed:exposed-core:$exposedVersion")
    compile("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    compile("org.jetbrains.exposed:exposed-java-time:$exposedVersion")
    compile("com.h2database:h2:$h2Version")
    compile("com.zaxxer:HikariCP:$hikariVersion")
}
