plugins {
	java
	id("org.springframework.boot") version "3.4.2"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "abc"
version = "0.0.1-SNAPSHOT"

val jaxws = configurations.create("jaxws")

val jaxwsSourceDir = "${buildDir}/generated/sources/jaxws"
val wsdlApiFile = "$rootDir/resource/api.wsdl"

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
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-web-services")

	jaxws("com.sun.xml.ws:jaxws-tools:3.0.0")
	jaxws("jakarta.xml.bind:jakarta.xml.bind-api:3.0.0")
	jaxws("jakarta.activation:jakarta.activation-api:2.0.0")
	jaxws("com.sun.xml.ws:jaxws-rt:3.0.0")

}

java.sourceSets["main"].java.srcDir(jaxwsSourceDir)

tasks.withType<JavaCompile>().configureEach {
	dependsOn("wsimport")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

val wsimport =  tasks.register("wsimport") {
	description = "Generate classes from wsdl using wsimport"

	doLast {
		project.mkdir(jaxwsSourceDir)
		ant.withGroovyBuilder {
				"taskdef"("name" to "wsimport",
						"classname" to "com.sun.tools.ws.ant.WsImport",
						"classpath" to jaxws.asPath
				)
				"wsimport"(
					"keep" to true,
					"destdir" to jaxwsSourceDir,
					"extension" to "true",
					"verbose" to true,
					"wsdl" to wsdlApiFile,
					"xnocompile" to true,
					"package" to "com.example.consumingwebservice.wsdl"
						) {
					"xjcarg"("value" to "-XautoNameResolution")
				}
		}
	}
}