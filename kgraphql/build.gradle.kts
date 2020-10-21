val version: String by project
val kotlinVersion: String by project
val coroutineVersion: String by project
val jacksonVersion: String by project
val nettyVersion: String by project
val hamcrestVersion: String by project
val kluentVersion: String by project
val junitVersion: String by project
val caffeineVersion: String by project
val kDataLoaderVersion: String by project
val serializationVersion: String by project

repositories {
    jcenter()
}

kotlin {
    jvm {
        withJava()
        compilations.all { kotlinOptions.jvmTarget = "11" }
    }

    sourceSets {
        val jvmMain by getting {
                dependencies {
                    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
                    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
                    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutineVersion")
                    implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")
                    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
                    implementation("com.github.ben-manes.caffeine:caffeine:$caffeineVersion")
                    implementation("de.nidomiro:KDataLoader:$kDataLoaderVersion")
                    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
                    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")
//                    implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:$serializationVersion") // JVM dependency
                }
            }
            val jvmTest by getting {
                dependencies {
                    implementation("io.netty:netty-all:$nettyVersion")
                    implementation("org.hamcrest:hamcrest:$hamcrestVersion")
                    implementation("org.amshove.kluent:kluent:$kluentVersion")
                    implementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
                    implementation("org.junit.jupiter:junit-jupiter-params:$junitVersion")
                    runtimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
                    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-debug:$coroutineVersion")
                    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutineVersion")
                }
            }
    }
}
