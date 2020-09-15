@file:Suppress("UNUSED_VARIABLE")

plugins {
    kotlin("multiplatform") version "1.3.71"
    id("com.github.ben-manes.versions") version "0.28.0"
    id("com.jfrog.bintray") version "1.8.4"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.3.71"
    id("maven-publish")
    id("jacoco")
}

val kgraphqlVersion: String by project
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

allprojects {
    group = "com.apurebase"
    version = kgraphqlVersion

    kotlin {
        jvm {
            repositories {
                jcenter()
            }
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
                    implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:$serializationVersion") // JVM dependency
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
}



//    task sourcesJar(type: Jar, dependsOn: classes) {
//        archiveClassifier.set 'sources'
//        from sourceSets.main.allSource
//    }
//
//    artifacts {
//        archives sourcesJar
//    }
//
//    task sourceJar(type: Jar) {
//        from sourceSets.main.allSource
//    }
//
//    bintray {
//        user = System.getenv('BINTRAY_USER')
//        key = System.getenv('BINTRAY_KEY')
//        publish = true
//        publications = ['MyPublication']
//        configurations = ['archives']
//        pkg {
//            repo = 'apurebase'
//            name = project.name
//            licenses = ['MIT']
//            vcsUrl = 'https://github.com/aPureBase/KGraphQL'
//            websiteUrl = 'https://kgraphql.in'
//            issueTrackerUrl = 'https://github.com/aPureBase/KGraphQL/issues'
//            version {
//                name = project.version
//                released = new Date()
//            }
//        }
//
//        publishing {
//            publications {
//                MyPublication(MavenPublication) {
//                    from components.java
//                    groupId project.group
//                    artifactId project.name
//                    artifact sourcesJar
//                    version version
//                }
//            }
//        }
//    }




//    test {
//        useJUnitPlatform()
//    }

