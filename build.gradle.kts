group = "me.jakejmattson"
version = "1.0-SNAPSHOT"

plugins {
    kotlin("jvm") version "1.4.30"

    id("com.github.johnrengelman.shadow") version "6.1.0"
    id("com.github.ben-manes.versions") version "0.36.0"
}

repositories {
    mavenCentral()
    jcenter()
    maven("https://jitpack.io")
    maven("https://schlaubi.jfrog.io/artifactory/lavakord")
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
}

dependencies {
    implementation("me.jakejmattson:DiscordKt:0.22.0-SNAPSHOT")
    //implementation("dev.schlaubi.lavakord", "kord", "1.0.0-SNAPSHOT")
    implementation("dev.schlaubi.lavakord", "core-jvm", "1.0.0-20210211.162050-2")
    implementation("dev.schlaubi.lavakord", "kord-jvm", "1.0.0-20210211.162050-2")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }

    shadowJar {
        archiveFileName.set("Echo.jar")
        manifest {
            attributes(
                "Main-Class" to "me.jakejmattson.echo.MainAppKt"
            )
        }
    }
}