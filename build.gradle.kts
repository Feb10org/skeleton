val jacksonDatabindNullableVersion = "0.2.6"
val restAssuredVersion = "5.5.0"
val wiremockTestcontainersVersion = "1.0-alpha-14"

plugins {
	java
	id("org.springframework.boot") version "3.4.2"
	id("io.spring.dependency-management") version "1.1.7"
	id("org.openapi.generator") version "7.11.0"
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
	implementation("org.openapitools:jackson-databind-nullable:$jacksonDatabindNullableVersion")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.rest-assured:rest-assured:$restAssuredVersion")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	testImplementation("org.wiremock.integrations.testcontainers:wiremock-testcontainers-module:$wiremockTestcontainersVersion")
	testImplementation("org.wiremock:wiremock:3.12.0")
//	testImplementation("org.testcontainers:wiremock:1.19.3")
	testImplementation("org.testcontainers:junit-jupiter:1.20.4")
}

openApiGenerate {
	generatorName.set("java")
	inputSpec.set("$rootDir/src/main/resources/remote_apis/petstore_api.yaml")
	outputDir.set("${layout.buildDirectory.asFile.get()}/generated")
	apiPackage.set("com.example.api")
	modelPackage.set("com.example.model")
	invokerPackage.set("com.example.invoker")
	configOptions.set(
        mapOf(
            "library" to "restclient",
			"generateBuilders" to "true",
            "dateLibrary" to "java8"
        )
	)
	modelNameSuffix.set("Dto")
	generateApiTests.set(false)
	generateModelTests.set(false)
}

tasks.named<JavaCompile>("compileJava") {
	dependsOn("openApiGenerate")
}

sourceSets["main"].java.srcDir("${layout.buildDirectory.asFile.get()}/generated/src/main/java")

tasks.withType<Test> {
	useJUnitPlatform()
}
