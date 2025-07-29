package com.liferay.upgrades.analyzer.project.dependency.detector;

import com.liferay.upgrades.analyzer.project.dependency.graph.builder.ProjectsDependencyGraphBuilder;
import com.liferay.upgrades.analyzer.project.dependency.model.Project;
import com.liferay.upgrades.analyzer.project.dependency.util.ProjectDetectorUtil;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Rafael Praxedes
 * @author Albert Gomes Cabral
 */
public class GradleProjectDetector implements ProjectDetector {

    @Override
    public boolean matches(String fileName, Path file) {
        return fileName.equals("build.gradle")
            && Files.exists(Paths.get(file.getParent().toString(), "src"))
            && _validateDetector(file);
    }

    @Override
    public void process(
        Path file, ProjectsDependencyGraphBuilder projectsDependencyGraphBuilder) {

        projectsDependencyGraphBuilder.addProject(
            _getProjectKey(
                file.getParent().toUri().getPath(),
                file.getParent().getFileName().toString()),
            _collectProjectDependencies(file));
    }

    private Set<Project> _collectProjectDependencies(Path gradleFile) {
        Set<Project> dependencies = new HashSet<>();

        Matcher matcher = _GRADLE_PROJECT_PATTERN.matcher(
            ProjectDetectorUtil.readFile(gradleFile));

        while (matcher.find() &&
                !_validateProjectDependency(matcher.group())) {

            dependencies.add(_getProjectKey(matcher.group(1)));
        }

        return dependencies;
    }

    private Project _getProjectKey(String path, String rawProjectName) {
        Project project = _getProjectKey(rawProjectName);

        project.setPath(path);

        return project;
    }

    private Project _getProjectKey(String rawProjectName) {
       return ProjectDetectorUtil.getProjectKey(rawProjectName, _projectInfos);
    }

    private boolean _validateDetector(Path path) {
        if (!Files.exists(
                Paths.get(path.getParent().toString(), "liferay-theme.json"))) {

            Path lookAndFeelPath = Paths.get(
                path.getParent().toString(),
                "/src/main/webapp/WEB-INF/liferay-look-and-feel.xml");

            return !lookAndFeelPath.toFile().exists();
        }

        return false;
    }

    private boolean _validateProjectDependency(String rawProjectName) {
        return rawProjectName.contains("//") || rawProjectName.contains("#");
    }

    private final Map<String, Project> _projectInfos = new HashMap<>();

    private static final Pattern _GRADLE_PROJECT_PATTERN = Pattern.compile(
        "//\\s+?compileOnly|compileInclude|implementation\\s+" +
                "project.*\\(*[\"'](.*)[\"']\\)");

}
