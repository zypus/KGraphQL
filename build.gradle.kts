@file:Suppress("UNUSED_VARIABLE")

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

plugins {
    kotlin("multiplatform") version "1.4.10" apply false
    id("com.github.ben-manes.versions") version "0.28.0"
    id("com.jfrog.bintray") version "1.8.4"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.4.10"
    id("maven-publish")
    id("jacoco")
}

buildscript {
    repositories {
        jcenter()
    }
}

subprojects {
    apply(plugin = "kotlin-multiplatform")
    apply(plugin = "maven-publish")
    group = "com.apurebase"
    version = version

    repositories {
        mavenCentral()
        jcenter()
    }

    tasks {
        withType<Test> {
            useJUnitPlatform()
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
}
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

