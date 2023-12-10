plugins {
    id("java")
}

group = "co.crystaldev"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    //maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://lib.alpn.cloud/alpine-public/")
    maven("https://repo.aikar.co/content/groups/aikar/")
    maven("https://repo.codemc.org/repository/nms/")
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.19.3-R0.1-SNAPSHOT")
    compileOnly("co.crystaldev:alpinecore:0.1.2")

    val lombok = "org.projectlombok:lombok:1.18.28"
    compileOnly(lombok)
    annotationProcessor(lombok)
}

tasks.test {
    useJUnitPlatform()
}