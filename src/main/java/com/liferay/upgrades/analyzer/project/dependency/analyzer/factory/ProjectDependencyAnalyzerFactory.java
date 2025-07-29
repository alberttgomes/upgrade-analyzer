package com.liferay.upgrades.analyzer.project.dependency.analyzer.factory;

import com.liferay.upgrades.analyzer.project.dependency.analyzer.ProjectDependencyAnalyzer;
import com.liferay.upgrades.analyzer.project.dependency.constant.ExportOptionsConstant;
import com.liferay.upgrades.analyzer.project.dependency.detector.*;

import java.util.List;

public class ProjectDependencyAnalyzerFactory {

    public static ProjectDependencyAnalyzer getProjectDependencyAnalyzer(
        String exportType) {

        if (exportType.equals(ExportOptionsConstant.EXPORT_TYPE_GAME_PLAN) ||
                exportType.equals(ExportOptionsConstant.EXPORT_TYPE_DOT_GRAPH)) {
            return new ProjectDependencyAnalyzer(
                List.of(
                    new GradleProjectDetector(), new MavenProjectDetector(),
                    new JSPortletProjectDetector(), new ThemeProjectDetector()));
        }
        else if (exportType.equals(ExportOptionsConstant.EXPORT_TYPE_STARTUP_GAME_PLAN)) {
            return new ProjectDependencyAnalyzer(
                List.of(
                    new APIModuleProjectDetector(), new ExporterModuleDetector(),
                    new FragmentHostModuleProjectDetector(), new JSPortletModuleProjectDetector(),
                    new PluginModuleProjectDetector(), new ServiceModuleProjectDetector(),
                    new OtherModuleProjectDetector(), new ThemeModuleProjectDetector()));
        }
        else throw new RuntimeException(
            "Unsupported export type: " + exportType);
    }

}
