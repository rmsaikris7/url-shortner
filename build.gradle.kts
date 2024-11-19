plugins {
	java
	id("org.springframework.boot") version "3.3.5"
	id("io.spring.dependency-management") version "1.1.6"
	id("com.diffplug.spotless") version "6.25.0"
}

group = "com.dkb.urlshortner"
version = "0.0.1"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	// spring dependencies
	implementation(libs.springBootStarterDataJpa)
	implementation(libs.springBootStarterDataRedis)
	implementation(libs.springBootStarterWeb)

	// external dependencies
	annotationProcessor(libs.mapstructProcessor)
	implementation(libs.flywayCore)
	implementation(libs.flywayDatabasePostgresql)
	implementation(libs.jakartaValidationApi)
	implementation(libs.commonsCodec)
	implementation(libs.mapstruct)
	implementation(libs.hibernateValidator)
	implementation(libs.dbSchedulerSpringBootStarter)
	implementation(libs.springdocWebmvcUI)
	implementation(libs.spotlessLib)
	implementation(libs.spotlessPluginGradle)
	runtimeOnly(libs.postgresql)

	// test dependencies
	testImplementation(libs.springBootStarterTest)
	testImplementation(libs.testcontainersJunitJupiter)
	testImplementation(libs.testcontainersPostgresql)
	testImplementation(libs.restAssured)
	testRuntimeOnly(libs.junitPlatformLauncher)
}

spotless {
	java {
		target("src/*/java/**/*.java")
		toggleOffOn()
		palantirJavaFormat()
		removeUnusedImports()
		trimTrailingWhitespace()
		endWithNewline()
		formatAnnotations()
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
