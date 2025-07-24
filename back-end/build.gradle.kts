plugins {
	java
	id("org.springframework.boot") version "3.5.3"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.karka-deh"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(24)
	}
}


repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.springframework.boot:spring-boot-starter-jdbc")

  implementation ("org.springframework.boot:spring-boot-starter-validation")

  // oauth
  implementation ("org.springframework.boot:spring-boot-starter-security")
  implementation ("org.springframework.boot:spring-boot-starter-oauth2-client")

  // jwt related
  implementation("io.jsonwebtoken:jjwt-api:0.11.5")
  implementation("io.jsonwebtoken:jjwt-impl:0.11.5")
  implementation("io.jsonwebtoken:jjwt-jackson:0.11.5")

  // .env loader
  implementation ("io.github.cdimascio:dotenv-java:3.0.0")

  // oracle db
  implementation("com.oracle.database.jdbc:ojdbc11:21.9.0.0")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
