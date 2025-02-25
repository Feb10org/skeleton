val jacksonDatabindNullableVersion = "0.2.6"
val restAssuredVersion = "5.5.0"
val logstashVersion = "8.0"

plugins {
	java
	id("org.springframework.boot") version "3.4.2"
	id("io.spring.dependency-management") version "1.1.7"
	id("org.openapi.generator") version "7.11.0"
    id("io.freefair.lombok") version "8.6"
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

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("net.logstash.logback:logstash-logback-encoder:$logstashVersion")
    //    implementation("com.microsoft.sqlserver:mssql-jdbc:12.8.1.jre11")
    implementation("org.openapitools:jackson-databind-nullable:$jacksonDatabindNullableVersion")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.rest-assured:rest-assured:$restAssuredVersion")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	testCompileOnly("org.projectlombok:lombok")
	testAnnotationProcessor("org.projectlombok:lombok")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.openapitools:jackson-databind-nullable:$jacksonDatabindNullableVersion")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.rest-assured:rest-assured:$restAssuredVersion")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")


	testImplementation("org.springframework:spring-jdbc")
	testImplementation("org.testcontainers:junit-jupiter")
	testImplementation("org.testcontainers:testcontainers")
	testImplementation("org.junit.jupiter:junit-jupiter")
	testImplementation("org.springframework.boot:spring-boot-starter-data-jdbc")
	testImplementation("com.microsoft.sqlserver:mssql-jdbc")
	testImplementation("org.testcontainers:mssqlserver")

	implementation("org.springframework.boot:spring-boot-starter")
	testImplementation("org.springframework.boot:spring-boot-test")

    implementation("io.github.cdimascio:dotenv-java:3.1.0")
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