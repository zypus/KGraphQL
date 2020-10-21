val ktorVersion: String by project
val kluentVersion: String by project
val junitVersion: String by project

kotlin {
    apply(plugin = "kotlinx-serialization")
    jvm {
        compilations.all { kotlinOptions.jvmTarget = "11" }
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                api(project(":kgraphql"))
                implementation("io.ktor:ktor-server-core:$ktorVersion")
                implementation("io.ktor:ktor-serialization:$ktorVersion")
                implementation("com.github.salomonbrys.kotson:kotson:2.5.0")
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation("me.lazmaid.kraph:kraph:0.6.1")
                implementation("io.ktor:ktor-server-test-host:$ktorVersion")
                implementation("io.ktor:ktor-auth:$ktorVersion")

                // TODO: Should not be copy/paste scenario here.
                implementation("org.amshove.kluent:kluent:$kluentVersion")
                implementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
                implementation("org.junit.jupiter:junit-jupiter-params:$junitVersion")
                runtimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
            }
        }
    }
}
