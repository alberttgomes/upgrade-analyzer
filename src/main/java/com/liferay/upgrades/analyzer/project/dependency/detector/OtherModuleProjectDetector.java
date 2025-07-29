package com.liferay.upgrades.analyzer.project.dependency.detector;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author Albert Gomes Cabral
 */
public class OtherModuleProjectDetector extends BaseStartupProjectDetector {

    @Override
    protected String getClassName() {
        return OtherModuleProjectDetector.class.getSimpleName();
    }

    @Override
    public boolean matches(String fileName, Path file) {
        return fileName.equals("bnd.bnd") && _validateDetector(fileName, file);
    }

    private boolean _validateDetector(String fileName, Path file) {
        boolean other = true;

        for (Map.Entry<String, Supplier<ProjectDetector>> supplierEntry :
                _PROJECT_DETECTORS_SUPPLIERS.entrySet()) {

            ProjectDetector projectDetector = supplierEntry.getValue().get();

            if (projectDetector != null && projectDetector.matches(
                    fileName, file)) {

                other = false;
                break;
            }
        }

        return other;
    }

    private static final Map<String, Supplier<ProjectDetector>> _PROJECT_DETECTORS_SUPPLIERS =
        new HashMap<>();

    static {
        _PROJECT_DETECTORS_SUPPLIERS.put(
            APIModuleProjectDetector.class.getSimpleName(),
            APIModuleProjectDetector::new);
        _PROJECT_DETECTORS_SUPPLIERS.put(
            ExporterModuleDetector.class.getSimpleName(),
            ExporterModuleDetector::new);
        _PROJECT_DETECTORS_SUPPLIERS.put(
            FragmentHostModuleProjectDetector.class.getSimpleName(),
            FragmentHostModuleProjectDetector::new);
        _PROJECT_DETECTORS_SUPPLIERS.put(
            ServiceModuleProjectDetector.class.getSimpleName(),
            ServiceModuleProjectDetector::new);
    }

}
