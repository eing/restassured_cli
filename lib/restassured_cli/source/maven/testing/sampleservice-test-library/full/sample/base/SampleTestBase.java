package GROUPID.tests.library.sample.base;

import GROUPID.tests.library.base.ServiceTestBase;
import GROUPID.tests.library.base.BaseConstant;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.jayway.restassured.filter.log.LogDetail;
import com.jayway.restassured.filter.log.ResponseLoggingFilter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

/**
 * Base class for common business logic that applies to all tests.
 * Please put any utility/helper functions in SampleServiceHelper in test-library module.
 * @author eing
 */
public class SampleTestBase extends ServiceTestBase {

    /**
     * Creates only one instance of WireMock server for all tests that extends SampleTestBase.
     */
    protected static WireMockServer wireMockServer = null;

    /**
     * Creates default request and response specification for reuse.
     */
    public SampleTestBase() {
        super(Constant.PROPERTIES_FILE);
        requestSpecBuilder.log(LogDetail.ALL).addFilter(new ResponseLoggingFilter(LogDetail.ALL));
        requestSpec = requestSpecBuilder.build();
        responseSpecBuilder.expectStatusCode(200);
        responseSpec = responseSpecBuilder.build();
    }

    /**
     * Starts up WireMock before any tests gets run.
     */
    @BeforeSuite
    public void beforeClass() {
        if (wireMockServer == null) {
            logger.debug("========== Starting WireMock ==========");
            String wiremockPort = internalProperties.getProperty(BaseConstant.HTTP_PROPERTY_PORT);
            if (wiremockPort != null) {
                wireMockServer = new WireMockServer(wireMockConfig().port(
                        Integer.parseInt(wiremockPort)));
                wireMockServer.start();
            }
        }
    }

    /**
     * Stops WireMock when all tests are executed.
     */
    @AfterSuite
    public void afterClass() {
        logger.debug("========== Stopping WireMock ==========");
        if (wireMockServer != null) {
            wireMockServer.stop();
            wireMockServer = null;
        }
    }
}