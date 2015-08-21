package GROUPID.tests.library.base;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.builder.ResponseSpecBuilder;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import com.jayway.restassured.specification.ResponseSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * This is a generic business agnostic base class which provides properties management,
 * logging and common RestAssured variables such as requestSpec and responseSpec.
 *
 * TODO: This package should be in a separate project and added as a dependency.
 * For the purpose of demo, keeping it in the test library module.
 * @author eing
 */
public class ServiceTestBase {

    public RequestSpecification requestSpec;
    public RequestSpecBuilder requestSpecBuilder = null;
    public Response response;
    public ResponseSpecification responseSpec;
    public ResponseSpecBuilder responseSpecBuilder;
    public String propertiesFile = null;
    public static String configDir = null;
    protected final Logger logger;
    protected Properties internalProperties = null;

    /**
     * Default settings:
     *  Property file: config.properties
     *  -Dtargetenv=QA
     */
    public ServiceTestBase() {
        this(BaseConstant.PROPERTIES_FILENAME_DEFAULT);
    }

    public ServiceTestBase(String propertiesFile) {
        logger = LoggerFactory.getLogger(getClass());
        if (configDir == null) {
            String currentEnvironment = System
                    .getProperty(BaseConstant.ENVIRONMENT_PROPERTY);
            if (currentEnvironment == null) {
                currentEnvironment = System
                        .getenv(BaseConstant.ENVIRONMENT_PROPERTY);
                if (currentEnvironment == null) {
                    currentEnvironment = BaseConstant.ENVIRONMENT_DEFAULT;
                }
            }
            configDir = "/" + currentEnvironment;
        }
        this.propertiesFile = propertiesFile;
        requestSpecBuilder = new RequestSpecBuilder();
        responseSpecBuilder = new ResponseSpecBuilder();

        if (internalProperties == null) {
            // Only need to read from properties file and set http defaults once for all tests
            readFromPropertiesFile();
            setHttpDefaults();
        }
    }

    /**
     * Read properties file from both /default and /[targetenv] folder.
     * The latter properties file will overwrite the default properties.
     */
    protected void readFromPropertiesFile() {
        // Read default/<properties file> first
        String defaultPropertiesFile = BaseConstant.DEFAULT_CONFIG_DIRECTORY + propertiesFile;
        internalProperties = InternalConfigManager.getAllConfig(defaultPropertiesFile);

        // Read <targetenv>/<properties file> next which overwrites default
        String configPath = configDir + propertiesFile;
        internalProperties.putAll(InternalConfigManager.getAllConfig(configPath));
    }

    /**
     * Initialize from property file if specified
     *  1. http.baseurl : base url e.g. http://www.intuit.com (default is localhost)
     *  2. http.port : http port e.g. 9999 (default is 80)
     */
    protected void setHttpDefaults() {

        // Set the base URI
        String baseUri = internalProperties.getProperty(BaseConstant.HTTP_PROPERTY_BASEURL);
        if (baseUri != null) {
            RestAssured.baseURI = baseUri;
            requestSpecBuilder.setBaseUri(baseUri);
        }
        // Set default port
        String defaultPort = internalProperties.getProperty(BaseConstant.HTTP_PROPERTY_PORT);
        if (defaultPort != null) {
            int port = Integer.parseInt(defaultPort);
            RestAssured.port = port;
            requestSpecBuilder.setPort(port);
        }
    }
}