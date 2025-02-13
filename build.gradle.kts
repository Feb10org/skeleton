plugins {
	java
	id("org.springframework.boot") version "3.4.2"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "abc"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation ("org.springframework.boot:spring-boot-starter-data-jpa")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	// cucumber
	testImplementation("io.cucumber:cucumber-java:7.21.1")
	testImplementation("io.cucumber:cucumber-junit:7.21.1")
	testImplementation("io.cucumber:cucumber-spring:7.21.1")
	// engine to run junit4
	testImplementation ("org.junit.vintage:junit-vintage-engine:5.11.4")

	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

	runtimeOnly ("com.h2database:h2")
}

tasks.withType<Test> {
	useJUnitPlatform()
	testLogging {
		events("passed", "failed", "skipped")
	}
}



