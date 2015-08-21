package GROUPID.tests.serviceintegration;

import GROUPID.tests.library.sample.base.SampleTestBase;

import com.jayway.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.any;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static com.github.tomakehurst.wiremock.stubbing.Scenario.STARTED;
import static com.jayway.awaitility.Awaitility.await;
import static com.jayway.restassured.RestAssured.given;

/**
 * Sample test to demonstrate working with an asynchronous batch transaction.
 * TODO: WireMock is used to stub responses but <b>should be removed for service integration testing</b>.
 * Stubbing in this test is only for demonstrating purpose.
 * @author eing
 */
public class SampleServiceIntegrationTest extends SampleTestBase {

    public SampleServiceIntegrationTest() {
    }

    /**
     * Add stubs to WireMock server for this test.
     * TODO: Remove this for actual service integration testing.
     */
    @BeforeClass
    public void setup() {
        //@formatter:off
        // returns 202: request has been accepted for processing, but the processing has not been completed
        wireMockServer.
                givenThat(
                        any(urlMatching("/batch")).
                                willReturn(aResponse().
                                        withHeader("Content-Type", ContentType.JSON.toString()).
                                        withHeader("X-AX-TRANSACTIONID", "1290").
                                        withStatus(202)));
        // returns
        // 1. state:IN_PROGRESS in header for first and second requests
        // 2. state:COMPLETE in header on the third request
        wireMockServer.
                givenThat(
                        get(urlMatching("/batch/[0-9]+/status")).
                                inScenario("Get batch status").
                        whenScenarioStateIs(STARTED).
                                willReturn(aResponse().
                                    withStatus(200).
                                    withHeader("Content-Type", ContentType.JSON.toString()).
                                    withHeader("state", "IN_PROGRESS")).
                                willSetStateTo("Second get status attempt"));

        wireMockServer.
                givenThat(
                        get(urlMatching("/batch/[0-9]+/status")).
                                inScenario("Get batch status").
                        whenScenarioStateIs("Second get status attempt").
                                willReturn(aResponse().
                                        withStatus(200).
                                        withHeader("Content-Type", ContentType.JSON.toString()).
                                        withHeader("state", "IN_PROGRESS")).
                                willSetStateTo("Third get status attempt"));

        wireMockServer.
                givenThat(
                        get(urlMatching("/batch/[0-9]+/status")).
                                inScenario("Get batch status").
                        whenScenarioStateIs("Third get status attempt").
                                willReturn(aResponse().
                                        withStatus(200).
                                        withHeader("Content-Type", ContentType.JSON.toString()).
                                        withHeader("state", "COMPLETE")).
                                willSetStateTo("Batch activity completed"));
        //@formatter:on
    }

    /**
     * Example of an asynchronous request and how to use awaitability.
     */
    @Test
    public void testAsyncOperation() throws Exception {
        /**
         * Typically an identifier is returned to the user on a batch process
         * so that you can use it to poll for its status.
         */
        //@formatter:off
        String batchId =
                given().
                    spec(requestSpec).
                when().
                        post("/batch").
                then().
                        statusCode(202).
                        extract().header("X-AX-TRANSACTIONID");
        //@formatter:on
        await().atMost(3, TimeUnit.SECONDS).until(batchActivityCompleted(batchId));
    }

    /**
     * Callback method for Java7. In Java8, you can code it inline in the test i.e.
     * await().atMost(3, TimeUnit.SECONDS).until(() -> batchActivityCompleted(batchId));
     * @param batchId batch job identifier to check status on
     * @return true if state is completed
     * @throws Exception
     */
    private Callable<Boolean> batchActivityCompleted(final String batchId) throws Exception {
        return new Callable<Boolean>() {
            public Boolean call() throws Exception {
                //@formatter:off
                String state =
                        given().
                            spec(requestSpec).
                        when().
                                get("/batch/{batchId}/status", batchId).
                        thenReturn().header("state");
                //@formatter:on
                return state.equals("COMPLETE");
            }
        };
    }

}