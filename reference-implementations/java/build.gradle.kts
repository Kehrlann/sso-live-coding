plugins {
    java
    id("org.springframework.boot") version "3.2.1"
    id("io.spring.dependency-management") version "1.1.3"
}

group = "wf.garnier"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.bootRun {
    val lines = File("../../.env").readLines()
    for (line in lines) {
        if (line.startsWith("#") || line.isBlank()) {
            continue
        }
        val parsedLine = line.split("=")
        systemProperty(parsedLine[0], parsedLine[1].replace("\"", ""))
    }
}
