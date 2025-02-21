import org.openapitools.generator.gradle.plugin.tasks.GenerateTask

val jacksonDatabindNullableVersion = "0.2.6"
val restAssuredVersion = "5.5.0"
val logstashVersion = "8.0"

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
	implementation ("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")
    implementation("net.logstash.logback:logstash-logback-encoder:$logstashVersion")
    implementation("org.openapitools:jackson-databind-nullable:$jacksonDatabindNullableVersion")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.rest-assured:rest-assured:$restAssuredVersion")
	testImplementation("io.cucumber:cucumber-java:7.21.1")
	testImplementation("io.cucumber:cucumber-junit-platform-engine:7.21.1")
	testImplementation("io.cucumber:cucumber-spring:7.21.1")
	testImplementation("org.junit.platform:junit-platform-suite:1.11.4")
	runtimeOnly ("com.h2database:h2")
}

tasks.named<JavaCompile>("compileJava") {
    dependsOn("openApiGenerateClient", "openApiGenerateServer")
}
sourceSets["main"].java.srcDir("${layout.buildDirectory.asFile.get()}/generated/src/main/java")

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        events("passed", "failed", "skipped")
    }
}

flyway {
	url = "jdbc:sqlserver://localhost:1433;databaseName=skeleton_db;encrypt=false"
    user = "skeleton"
    password = "skele@Ton123"
    locations = arrayOf("filesystem:src/db/migration")
}

val apiBasePackage = "com.sample"
val generatedDirPath = "$buildDir/generated"
val apiFile = "$rootDir/resource/api.yml"


tasks.register<GenerateTask>("openApiGenerateClient"){
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

tasks.register<GenerateTask>("openApiGenerateServer"){
    generatorName.set("spring")
    inputSpec.set(apiFile)
    outputDir.set(generatedDirPath)
    apiPackage.set("$apiBasePackage.api")
    invokerPackage.set("$apiBasePackage.invoker")
    modelPackage.set("$apiBasePackage.model")
    configOptions.set(mapOf(
        "interfaceOnly" to "true",
        "skipDefaultInterface" to "true",
        "useSpringBoot3" to "true",
    ))
}
