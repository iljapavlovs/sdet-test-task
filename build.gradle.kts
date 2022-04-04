plugins {
    id("org.springframework.boot") version "2.6.5"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    java
    id("org.unbroken-dome.test-sets") version "4.0.0"
    id("io.qameta.allure") version "2.9.6"
    idea
}

java.sourceCompatibility = JavaVersion.VERSION_11
java.targetCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

val allureVersion = "2.17.3"
val retrofitVersion = "2.9.0"

dependencies {
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testCompileOnly("org.projectlombok:lombok")
    testAnnotationProcessor("org.projectlombok:lombok")

    implementation(springBoot("data-jpa"))
    implementation(springBoot("web"))
    implementation(springBoot("actuator"))
//    testImplementation("org.springframework.boot:spring-boot-starter-test") {
//        exclude(group = "org.junit.vintage', module= 'junit-vintage-engine")
//    }
    implementation("com.h2database:h2")
    runtimeOnly("org.postgresql:postgresql")


    // E2E
    //api
    testImplementation("com.squareup.retrofit2:retrofit:${retrofitVersion}")
    testImplementation("com.squareup.retrofit2:converter-scalars:${retrofitVersion}")
    testImplementation("com.squareup.retrofit2:converter-jackson:${retrofitVersion}")
    testImplementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.13.2")
    testImplementation("com.squareup.okhttp3:logging-interceptor:3.8.1")

    //cucumber and junit
    testImplementation(platform("org.junit:junit-bom:5.8.2"))
    testImplementation(platform("io.cucumber:cucumber-bom:7.2.3"))
    testImplementation("io.cucumber:cucumber-java")
    testImplementation("io.cucumber:cucumber-junit-platform-engine")
    testImplementation("org.junit.platform:junit-platform-suite")
    testImplementation("org.junit.jupiter:junit-jupiter")

    //logging
    testImplementation("org.slf4j:slf4j-api:1.7.36")
    testImplementation("ch.qos.logback:logback-classic:1.2.11")
    testImplementation("ch.qos.logback:logback-core:1.2.11")

    testImplementation("org.assertj:assertj-core:3.22.0")
    testImplementation("org.testcontainers:testcontainers:1.16.3") {
        exclude(group = "junit", module = "junit")
    }
    testImplementation("org.testcontainers:junit-jupiter:1.16.3")

    testImplementation("com.fasterxml.jackson.core:jackson-databind:2.12.1")

    testImplementation("io.qameta.allure:allure-cucumber7-jvm:${allureVersion}")
    testImplementation("io.qameta.allure:allure-okhttp3:${allureVersion}")
}

fun springBoot(module: String) = "org.springframework.boot:spring-boot-starter-$module"


testSets {
    create("e2e-test") {
        dirName = "e2e-test"
    }
}

sourceSets {
    named<SourceSet>("e2e-test") {
        java {
            srcDirs("src/e2e-test/java")
        }
        resources {
            srcDirs("src/e2e-test/resources")
        }


        // Workaround for https://github.com/unbroken-dome/gradle-testsets-plugin/issues/109
        compileClasspath += main.get().output + test.get().output
        runtimeClasspath += main.get().output + test.get().output
    }
}

tasks.named<Test>("e2e-test") {
    useJUnitPlatform()
    systemProperties(System.getProperties().toMap() as Map<String, Any>)
    systemProperty("cucumber.junit-platform.naming-strategy", "long")
    ignoreFailures = true

    include("**/RunInContainersTest.class")
    testLogging.showStandardStreams = true
}

allure {

    version.set(allureVersion)
    adapter {
//        // Configure version for io.qameta.allure:allure-* adapters
        allureJavaVersion.set(allureVersion)
        aspectjVersion.set("1.9.5")

        autoconfigure.set(true)
        autoconfigureListeners.set(true)
        aspectjWeaver.set(true)

//        frameworks {
        //todo - allure still missing cucumber7Jvm
//            cucumber7Jvm {
//                adapterVersion.set(allureVersion)
//                enabled.set(true)
//                autoconfigureListeners.set(true)
//            }

    }
}


// todo - dirty hack from https://github.com/gradle/gradle/issues/17236
gradle.taskGraph.whenReady {
    allTasks
        .filter { it.hasProperty("duplicatesStrategy") } // Because it's some weird decorated wrapper that I can't cast.
        .forEach {
            it.setProperty("duplicatesStrategy", "EXCLUDE")
        }
}