package GROUPID.tests.service;

import GROUPID.tests.library.sample.base.SampleTestBase;
import GROUPID.tests.library.sample.base.Constant;
import com.jayway.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

/**
 * Default test generated to demonstrate coding best practices with
 *  1. SampleTestBase (business domain base class) and
 *  2. ServiceTestBase (business agnostic base class).
 */
public class SampleTest extends SampleTestBase {

    public SampleTest() {
    }

    /**
     */
    @Test
    public void testDummy() {
        logger.debug("use Logger and not System.out");
    }

}
