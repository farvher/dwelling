pluginManagement {
	repositories {
		maven { url = uri("https://repo.spring.io/snapshot") }
		maven { url = uri("https://repo.spring.io/milestone") }
		jcenter()
		mavenCentral()
		gradlePluginPortal()
	}
	resolutionStrategy {
		eachPlugin {
			if (requested.id.id == "org.springframework.boot") {
				useModule("org.springframework.boot:spring-boot-gradle-plugin:${requested.version}")
			}
		}
	}
}
rootProject.name = "dwelling"
