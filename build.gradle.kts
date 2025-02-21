val jacksonDatabindNullableVersion = "0.2.6"
val restAssuredVersion = "5.5.0"

plugins {
	java
	id("org.springframework.boot") version "3.4.2"
	id("io.spring.dependency-management") version "1.1.7"
	id("org.openapi.generator") version "7.11.0"
    id("org.flywaydb.flyway") version "11.3.3"
}

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("org.flywaydb:flyway-sqlserver:11.3.3")
        classpath("com.microsoft.sqlserver:mssql-jdbc:12.8.1.jre11")
    }
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
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.openapitools:jackson-databind-nullable:$jacksonDatabindNullableVersion")
	//    implementation("com.microsoft.sqlserver:mssql-jdbc:12.8.1.jre11")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	testImplementation("io.rest-assured:rest-assured:$restAssuredVersion")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
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


flyway {
	url = "jdbc:sqlserver://localhost:1433;databaseName=skeleton_db;encrypt=false"
    user = "skeleton"
    password = "skele@Ton123"
    locations = arrayOf("filesystem:src/db/migration")
}