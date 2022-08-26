package net.feelform.git

import org.gradle.testkit.runner.GradleRunner
import spock.lang.Specification
import spock.lang.TempDir

import static org.gradle.testkit.runner.TaskOutcome.SUCCESS

class GitSourceDependencyPluginTest extends Specification {
    @TempDir File testProjectDir
    File buildFile

    def setup() {
        buildFile = new File(testProjectDir, 'build.gradle')
        buildFile << """
            plugins {
                id 'net.feelform.git-source-dependency'
            }
        """
    }

    def "can successfully configure git source dependency URL through extension and verify it"() {
        buildFile << """
            git {
                url = 'https://www.google.com/'
            }
        """

        when:
        def result = GradleRunner.create()
                .withProjectDir(testProjectDir)
                .withArguments('verifyGitSourceDependency')
                .withPluginClasspath()
                .build()

        then:
        result.output.contains("Successfully resolved URL 'https://www.google.com/'")
        result.task(":verifyUrl").outcome == SUCCESS
    }
}
