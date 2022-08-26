plugins {
    `java-gradle-plugin`
    id("org.jetbrains.kotlin.jvm") version "1.6.21"
    groovy
}

val integrationTest by sourceSets.creating

dependencies {
    "integrationTestImplementation"(project)
}

val integrationTestTask = tasks.register<Test>("integrationTest") {
    description = "Runs the integration tests."
    group = "verification"
    testClassesDirs = integrationTest.output.classesDirs
    classpath = integrationTest.runtimeClasspath
    mustRunAfter(tasks.test)
}
tasks.check {
    dependsOn(integrationTestTask)
}

val functionalTest by sourceSets.creating
dependencies {
    "functionalTestImplementation"(project)
}
val functionalTestTask = tasks.register<Test>("functionalTest") {
    description = "Runs the functional tests."
    group = "verification"
    testClassesDirs = functionalTest.output.classesDirs
    classpath = functionalTest.runtimeClasspath
    mustRunAfter(tasks.test, integrationTestTask)
}
tasks.check {
    dependsOn(functionalTestTask)
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    testImplementation(platform("org.spockframework:spock-bom:2.1-groovy-3.0"))
    testImplementation("org.spockframework:spock-core")

    "integrationTestImplementation"(platform("org.spockframework:spock-bom:2.1-groovy-3.0"))
    "integrationTestImplementation"("org.spockframework:spock-core")

    "functionalTestImplementation"(platform("org.spockframework:spock-bom:2.1-groovy-3.0"))
    "functionalTestImplementation"("org.spockframework:spock-core")
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

gradlePlugin {
    testSourceSets(functionalTest)
}

gradlePlugin {
    val greeting by plugins.creating {
        id = "net.feelform.git-source-dependency"
        implementationClass = "net.feelform.git.GitSourceDependencyPlugin"
    }
}