package com.liferay.upgrades.analyzer.project.dependency.detector;

import com.liferay.upgrades.analyzer.project.dependency.graph.builder.ProjectsDependencyGraphBuilder;
import com.liferay.upgrades.analyzer.project.dependency.model.Project;
import com.liferay.upgrades.analyzer.project.dependency.util.ProjectDetectorUtil;

import java.nio.file.Path;
import java.util.Collections;

/**
 * @author Albert Gomes Cabral
 */
public class JSPortletModuleProjectDetector extends JSPortletProjectDetector {

    @Override
    public void process(
        Path file, ProjectsDependencyGraphBuilder projectsDependencyGraphBuilder) {

        Project project = ProjectDetectorUtil.getProjectKey(file);

        String detectorKey = String.format(
            "%s=%s", project.getKey(), getClass().getSimpleName());

        project.setKey(detectorKey);
        project.setName(detectorKey);

        projectsDependencyGraphBuilder.addProject(
            project, Collections.emptySet());

    }

}
