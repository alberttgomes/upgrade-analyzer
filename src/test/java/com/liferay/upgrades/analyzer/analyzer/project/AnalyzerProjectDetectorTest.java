package com.liferay.upgrades.analyzer.analyzer.project;

import com.liferay.upgrades.analyzer.project.dependency.detector.GradleProjectDetector;
import com.liferay.upgrades.analyzer.project.dependency.detector.ProjectDetector;
import com.liferay.upgrades.analyzer.project.dependency.graph.builder.ProjectsDependencyGraphBuilder;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Albert Gomes Cabral
 */
public class AnalyzerProjectDetectorTest {

    @Test
    public void testProjectDependencyGraphNonExistentProject() throws IOException {
        ProjectsDependencyGraphBuilder projectsDependencyGraphBuilder =
            new ProjectsDependencyGraphBuilder();

        ClassLoader classLoader = getClass().getClassLoader();

        String rootProjectPathP1 = Objects.requireNonNull(
            classLoader.getResource(
                "com/liferay/upgrades/analyzer/gradle/detector/p1/build.gradle")).getPath();

        Path fileP1 = Paths.get(rootProjectPathP1);

        String rootProjectPathP2 = Objects.requireNonNull(
            classLoader.getResource(
                "com/liferay/upgrades/analyzer/gradle/detector/p2/build.gradle")).getPath();

        Path fileP2 = Paths.get(rootProjectPathP2);

        ProjectDetector projectDetector = new GradleProjectDetector();

        _analyzer(
            fileP1, projectDetector, projectsDependencyGraphBuilder);

        _analyzer(
            fileP2, projectDetector, projectsDependencyGraphBuilder);

        String result = projectsDependencyGraphBuilder.build().toString();

        Assertions.assertEquals(_EXPECTED_DEPENDENCIES_OUT_PUT, result);
    }

    private void _analyzer(
        Path path,  ProjectDetector projectDetector,
        ProjectsDependencyGraphBuilder projectsDependencyGraphBuilder) {

        if (projectDetector.matches("build.gradle", path)) {
            projectDetector.process(path, projectsDependencyGraphBuilder);
        }
    }

    private static final String _EXPECTED_DEPENDENCIES_OUT_PUT =
        "leaves: [{\n" +
        " \"name\": \":detector:p2\",\n" +
        " \"dependencies\": [{\n" +
        " \"name\": \"p1\",\n" +
        " \"dependencies\": [] \n" +
        "}] \n" +
        "}]";
}
