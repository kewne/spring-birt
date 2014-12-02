package io.github.kewne.spring_birt;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.birt.report.model.api.IResourceLocator;
import org.eclipse.birt.report.model.api.ModuleHandle;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Map;

public class ClasspathResourceLocator implements IResourceLocator {

    private static final Log LOGGER = LogFactory.getLog(ClasspathResourceLocator.class);

    private String prefix;

    public ClasspathResourceLocator(String prefix) {
        this.prefix = prefix;
    }

    public ClasspathResourceLocator() {
        this.prefix = "";
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public URL findResource(ModuleHandle moduleHandle, String fileName, int type) {
        return findResource(moduleHandle, fileName, type, Collections.emptyMap());
    }

    @Override
    public URL findResource(ModuleHandle moduleHandle, String fileName, int type, Map appContext) {
        try {
            return new ClassPathResource(Paths.get(prefix, fileName).toString()).getURL();
        } catch (IOException e) {
            LOGGER.debug("Resource not found, returning null...", e);
            return null;
        }
    }
}
