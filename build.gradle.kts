plugins {
    id("java")
    application
}

group = "com.adz1q.hospital.management"
version = "1.0"

repositories {
    mavenCentral()
}

application {
    mainClass.set("com.adz1q.hospital.management.Main")
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}
