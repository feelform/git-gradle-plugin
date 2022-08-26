plugins {
    `java-gradle-plugin`
    id("org.jetbrains.kotlin.jvm") version "1.6.21"
    groovy
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    testImplementation(platform("org.spockframework:spock-bom:2.1-groovy-3.0"))
    testImplementation("org.spockframework:spock-core")
}

gradlePlugin {
    // Define the plugin
    val greeting by plugins.creating {
        id = "net.feelform.gradle.plugin.git.Settings"
        implementationClass = "net.feelform.gradle.plugin.git.SettingsPlugin"
    }
}

// Add a source set for the functional test suite
val functionalTestSourceSet = sourceSets.create("functionalTest") {
}

configurations["functionalTestImplementation"].extendsFrom(configurations["testImplementation"])

// Add a task to run the functional tests
val functionalTest by tasks.registering(Test::class) {
    testClassesDirs = functionalTestSourceSet.output.classesDirs
    classpath = functionalTestSourceSet.runtimeClasspath
    useJUnitPlatform()
}

gradlePlugin.testSourceSets(functionalTestSourceSet)

tasks.named<Task>("check") {
    // Run the functional tests as part of `check`
    dependsOn(functionalTest)
}

tasks.named<Test>("test") {
    // Use JUnit Jupiter for unit tests.
    useJUnitPlatform()
}