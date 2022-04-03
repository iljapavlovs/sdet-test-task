plugins {
    id("org.springframework.boot") version "2.6.5"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    java
    id("org.unbroken-dome.test-sets") version "4.0.0"
    id("io.qameta.allure") version "2.9.6"
    id("io.qameta.allure-adapter") version "2.9.6"
    idea
}

java.sourceCompatibility = JavaVersion.VERSION_11
java.targetCompatibility = JavaVersion.VERSION_11
group = "co.copper"

repositories {
    mavenCentral()
}


dependencies {
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    implementation(springBoot("data-jpa"))
    implementation(springBoot("web"))
    implementation(springBoot("actuator"))
    implementation("com.h2database:h2")
    runtimeOnly("org.postgresql:postgresql")


//    e2e
    //api
    testImplementation("com.squareup.retrofit2:retrofit:2.9.0")
    testImplementation("com.squareup.retrofit2:converter-scalars:2.9.0")
    testImplementation("com.squareup.retrofit2:converter-jackson:2.9.0")
    testImplementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.13.2")
    testImplementation("com.squareup.okhttp3:logging-interceptor:3.8.1")


    testImplementation("io.cucumber:cucumber-java:6.9.1")
    testImplementation("io.cucumber:cucumber-junit:6.9.1")
    testImplementation("io.cucumber:cucumber-guice:6.9.1")

    //logging
    testImplementation("org.slf4j:slf4j-api:1.7.36")
    testImplementation("ch.qos.logback:logback-classic:1.2.11")
    testImplementation("ch.qos.logback:logback-core:1.2.11")
    testImplementation("com.google.guava:guava:23.0")




    testCompileOnly("org.projectlombok:lombok:1.18.22")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.22")

    testImplementation("org.assertj:assertj-core:3.22.0")
    testImplementation("org.testcontainers:testcontainers:1.16.3")


    testImplementation("com.fasterxml.jackson.core:jackson-databind:2.12.1")
    testImplementation("commons-configuration:commons-configuration:1.10")



    testImplementation("com.google.inject:guice:4.1.0")
    testImplementation("com.mycila.guice.extensions:mycila-guice-jsr250:4.0.rc1")
    testImplementation("com.mycila.guice.extensions:mycila-guice-closeable:4.0.rc1")

    testImplementation("javax.annotation:javax.annotation-api:1.3.2")

    testImplementation("io.qameta.allure:allure-cucumber6-jvm:2.17.3")
    testImplementation("io.qameta.allure:allure-okhttp3:2.17.3")


}

fun springBoot(module: String) = "org.springframework.boot:spring-boot-starter-$module"


testSets {
    create("e2e-test") {
        dirName = "e2e-test"
    }
}

sourceSets {
    named<SourceSet>("e2e-test") {

        // Workaround for https://github.com/unbroken-dome/gradle-testsets-plugin/issues/109
        compileClasspath += main.get().output + test.get().output
        runtimeClasspath += main.get().output + test.get().output
    }
}

tasks.getByName<Test>("e2e-test") {
    ignoreFailures = true
    include("**/RunInContainersTest.class")

    testLogging.showStandardStreams = true
    systemProperties(System.getProperties().toMap() as Map<String, Any>)

    // will not work - works only with Java Classes
    maxParallelForks = 3
}

allure {

    adapter {
        // Configure version for io.qameta.allure:allure-* adapters
        allureJavaVersion.set("2.17.3")
        aspectjVersion.set("1.9.5")

        autoconfigure.set(true)
        autoconfigureListeners.set(true)
        aspectjWeaver.set(true)

        frameworks {
            cucumber6Jvm {
                adapterVersion.set("2.17.3")
                enabled.set(true)
                autoconfigureListeners.set(true)
            }


        }
    }
}

