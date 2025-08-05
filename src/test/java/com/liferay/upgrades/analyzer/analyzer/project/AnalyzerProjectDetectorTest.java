package com.liferay.upgrades.analyzer.analyzer.project;

import com.liferay.upgrades.analyzer.project.dependency.detector.GradleProjectDetector;
import com.liferay.upgrades.analyzer.project.dependency.detector.ProjectDetector;
import com.liferay.upgrades.analyzer.project.dependency.graph.builder.ProjectsDependencyGraphBuilder;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import com.liferay.upgrades.analyzer.project.dependency.util.ProjectDetectorUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Albert Gomes Cabral
 */
public class AnalyzerProjectDetectorTest {

    @Test
    public void testProjectDependencyGraphNonExistentProject() throws Exception {
        ProjectsDependencyGraphBuilder projectsDependencyGraphBuilder =
            new ProjectsDependencyGraphBuilder();

        ClassLoader classLoader = getClass().getClassLoader();

        Path fileP1 = Paths.get(
            Objects.requireNonNull(classLoader.getResource(
                    "detector/p1/build.gradle")
            ).toURI());

        Path fileP2 = Paths.get(
            Objects.requireNonNull(classLoader.getResource(
                    "detector/p2/build.gradle")
            ).toURI());

        ProjectDetector projectDetector = new GradleProjectDetector();

        _analyzer(
            fileP1, projectDetector, projectsDependencyGraphBuilder);

        _analyzer(
            fileP2, projectDetector, projectsDependencyGraphBuilder);

        String result = projectsDependencyGraphBuilder.build().toString();

        Assertions.fail(
            String.format(
                "p1: %s, p2: %s, result: %s",
                ProjectDetectorUtil.getProjectKey(fileP1),
                ProjectDetectorUtil.getProjectKey(fileP2), result));

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
