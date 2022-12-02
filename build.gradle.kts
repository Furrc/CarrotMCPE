plugins {
    java
    `maven-publish`
    id("com.github.johnrengelman.shadow")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.fusesource.jansi:jansi:2.4.0")
    implementation("com.google.guava:guava:23.0")
    implementation("com.google.code.gson:gson:2.10")
    implementation("org.yaml:snakeyaml:1.33")
    implementation("net.minecrell:terminalconsoleappender:1.3.0")
    implementation("org.jline:jline-terminal:3.21.0")
    implementation("org.jline:jline-terminal-jna:3.21.0")
    implementation("org.jline:jline-reader:3.21.0")
    implementation("org.apache.logging.log4j:log4j-api:2.19.0")
    implementation("org.apache.logging.log4j:log4j-core:2.19.0")
    implementation("net.sf.jopt-simple:jopt-simple:5.0.4")
    implementation("org.iq80.leveldb:leveldb:0.12")
    implementation("io.netty:netty-all:4.1.85.Final")
    implementation("com.github.dblock:oshi-core:3.4.0")
    implementation("org.apache.commons:commons-lang3:3.12.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.9.0")

    compileOnly("org.projectlombok:lombok:1.18.24")
    annotationProcessor("org.projectlombok:lombok:1.18.24")
}

group = "ru.furrc"
version = "1.0"

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(11))

    withSourcesJar()
}

tasks {
    build { dependsOn(shadowJar) }
    shadowJar {
        archiveFileName.set("carrot.jar")
        transform(com.github.jengelman.gradle.plugins.shadow.transformers.Log4j2PluginsCacheFileTransformer::class.java)

        manifest {
            attributes["Main-Class"] = "cn.nukkit.Nukkit"
            attributes["Multi-Release"] = "true"
        }
    }
    withType<JavaCompile> { options.encoding = "UTF-8" }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group.toString()
            artifactId = "carrot"
            version = project.version.toString()

            from(components["java"])
        }
    }

    repositories {
        mavenLocal()
    }
}