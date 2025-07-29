package com.liferay.upgrades.analyzer.project.dependency.detector;

import com.liferay.upgrades.analyzer.project.dependency.graph.builder.ProjectsDependencyGraphBuilder;
import com.liferay.upgrades.analyzer.project.dependency.model.Project;

import java.nio.file.Path;
import java.util.Collections;
import java.util.Objects;

/**
 * @author Albert Gomes Cabral
 */
public class ThemeModuleProjectDetector extends ThemeProjectDetector {

    @Override
    public void process(
        Path file, ProjectsDependencyGraphBuilder projectsDependencyGraphBuilder) {

        Project project = getThemeProjectKey(file);

        if (Objects.nonNull(project)) {
            String detectorKey = String.format(
                "%s=%s", project.getKey(), getClass().getSimpleName());

            project.setKey(detectorKey);
            project.setName(detectorKey);

            projectsDependencyGraphBuilder.addProject(
                project, Collections.emptySet());
        }
    }

}
