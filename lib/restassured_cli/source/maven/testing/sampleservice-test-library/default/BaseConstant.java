package GROUPID.tests.library.base;

/**
 * Constants used by ServiceTestBase and InternalConfigManager.
 * TODO: This package should be in a separate project and added as a dependency.
 * For the purpose of demo, keeping it in the test library module.
 */
public final class BaseConstant {

    /**
     * Private constructor to prevent instantiation.
     */
    private BaseConstant() {
    }

    /**
     * Key in property file to look for for user specified base url e.g. http://www.intuit.com
     */
    public static final String HTTP_PROPERTY_BASEURL = "http.baseurl";

    /**
     * Key in property file to look for user specified port.
     */
    public static final String HTTP_PROPERTY_PORT = "http.port";

    /**
     * Default directory containing properties file where it will contain all common properties
     * agnostic of the environment e.g. basePath
     */
    public static final String DEFAULT_CONFIG_DIRECTORY = "/default";

    /**
     * Attribute name to read in environment value i.e. -Dtargetenv=[ci|qa|e2e|perf|production]
     */
    public static final String ENVIRONMENT_PROPERTY = "targetenv";

    /**
     * Default targetenv (target environment) if -Dtargetenv is not specified during test execution outside of
     * maven profiles e.g. running tests in IDE (with right click, run test).
     */
    public static final String ENVIRONMENT_DEFAULT = "qa";

    /**
     * Default properties file that ServiceTestBase will look for if no file is specified in the constructor.
     */
    public static final String PROPERTIES_FILENAME_DEFAULT = "/config.properties";
}

