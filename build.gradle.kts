import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.5.5"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.5.31"
    kotlin("plugin.spring") version "1.5.31"
    kotlin("kapt") version "1.5.31"
    `maven-publish`
}

group = "io.uvera"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    kapt("org.springframework.boot:spring-boot-autoconfigure-processor")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.bootJar {
    enabled = false
}

tasks.jar {
    enabled = true
    archiveClassifier.set("")
}

val sourcesJar by tasks.creating(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.getByName("main").allSource)
}

val myArtifactId: String = rootProject.name

val myArtifactGroup: String = project.group.toString()

val myArtifactVersion: String = project.version.toString()

publishing {
    publications {

        create<MavenPublication>("mavenJava") {
            groupId = myArtifactGroup
            artifactId = myArtifactId
            version = myArtifactVersion

            from(components["java"])
            artifact(sourcesJar) {
                classifier = "sources"
            }
            pom {
                packaging = "jar"
                name.set(myArtifactId)
            }
        }
    }
}
