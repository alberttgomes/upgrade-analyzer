import com.liferay.dynamic.data.mapping.service.DDMContentLocalService;
import java.util.Enumeration;
import java.util.List;
import org.osgi.framework.Bundle; 
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

BundleContext bundleContext = FrameworkUtil.getBundle(DDMContentLocalService.class).getBundleContext();

for (Bundle bundle : bundleContext.getBundles()) {
    String headerContent = bundle.getHeaders().get("Fragment-Host");
    String bundleSymbolicName = bundle.getHeaders().get("Bundle-SymbolicName");

    if (headerContent != null && !headerContent.isEmpty()) {
        List<String> fileList = new ArrayList<>();

        collectFiles(bundle, "/", fileList);

        String reportMessage =  "Bundle: " + bundle.getSymbolicName() + " overrides: " + bundle.getHeaders().get("Fragment-Host");

        if (fileList.isEmpty()) {
            out.println(reportMessage);
        }
        else {
            out.println(reportMessage + ", files: " + fileList);
        }

    }
}

def collectFiles(Bundle bundle, String path, fileList) {
    Enumeration<String> resources = bundle.getEntryPaths(path);
    
    if (resources != null) {
        while (resources.hasMoreElements()) {
            String resourcePath = resources.nextElement();

            if (resourcePath.endsWith(".jsp") || resourcePath.endsWith(".jspf")) {
                fileList.add(resourcePath);
            }
            else if (resourcePath.endsWith("/")){
                collectFiles(bundle, resourcePath, fileList);
            }
        }
    }
}