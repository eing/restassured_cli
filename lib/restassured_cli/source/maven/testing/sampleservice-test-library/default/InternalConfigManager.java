package GROUPID.tests.library.base;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Helper class to return list of properties.
 * TODO: This package should be in a separate project and added as a dependency.
 * For the purpose of demo, keeping it in the test library module.
 */
public class InternalConfigManager {

    private final static Logger logger = LoggerFactory.getLogger(InternalConfigManager.class);

    /**
     * Reads all properties from file.
     * @return properties
     */
    public synchronized static Properties getAllConfig(String propertiesFile) {
        try {
            InputStream is = InternalConfigManager.class.getResourceAsStream(propertiesFile);
            if (is == null) {
                File file = new File(propertiesFile);
                if (!file.isDirectory()) {
                    if (file.exists()) {
                        is = new FileInputStream(file);
                    } else {
                        throw new IOException("File not exist: " + propertiesFile);
                    }
                } else {
                    throw new IOException("Unexpected dir path passed (a file path should be passed): " +
                            propertiesFile);
                }
            }
            Properties testProperties = new Properties();
            testProperties.load(is);
            return testProperties;
        }
        catch (Exception ex)
        {
            logger.error("Cannot load test configuration");
            return null;
        }
    }
}