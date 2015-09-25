package GROUPID.tests.service;

import GROUPID.tests.library.sample.base.SampleTestBase;
import GROUPID.tests.library.schema.Customer;
import GROUPID.tests.library.schema.Phone;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jayway.restassured.config.RestAssuredConfig;
import com.jayway.restassured.config.XmlConfig;
import com.jayway.restassured.path.xml.XmlPath;
import com.jayway.restassured.filter.session.SessionFilter;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.any;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.path.json.JsonPath.from;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Sample tests to show how to use RestAssured, and hamcrest assertions.
 * WireMock is used to stub responses to http://localhost:9999.
 * @author eing
 */
public class SampleComponentTest extends SampleTestBase {

    public SampleComponentTest() {
    }

    /**
     * Add stubs to WireMock server for this set of tests.
     */
    @BeforeClass
    public void setup() throws SOAPException, IOException {
        /**
         * Create a JSON response using JsonNodeFactory instead of hardcoding in string which is error prone.
         * {
                "customer": {
                    "firstname": "Jane", "lastname": "Smith",
                    "phone": [
                        { "number": "650-123-4566", "type": "work" },
                        { "number": "650-123-4567", "type": "work" },
                        { "number": "650-123-4568", "type": "fax" } ],
                    "city": "Mountain View", "state": "CA", "zip": 94040 }
         }
         */
        ObjectNode phone1 = new ObjectNode(JsonNodeFactory.instance);
        phone1.put("number", "650-123-4566");
        phone1.put("type", "work");
        ObjectNode phone2 = new ObjectNode(JsonNodeFactory.instance);
        phone2.put("number", "650-123-4567");
        phone2.put("type", "work");
        ObjectNode phone3 = new ObjectNode(JsonNodeFactory.instance);
        phone3.put("number", "650-123-4568");
        phone3.put("type", "fax");
        ArrayList<ObjectNode> phones = new ArrayList<ObjectNode>();
        phones.add(phone1);
        phones.add(phone2);
        phones.add(phone3);
        ObjectNode customerJson = new ObjectNode(JsonNodeFactory.instance);
        customerJson.
                putObject("customer").
                put("firstname", "Jane").
                put("lastname", "Smith").
                putPOJO("phone", phones).
                put("city", "Mountain View").
                put("state", "CA").
                put("zip", 94040);
        // Stub response for SimpleGet
        wireMockServer.
                givenThat(
                        (any(urlMatching("/customers/[0-9]+")).
                                willReturn(aResponse().
                                        withHeader("Content-Type", ContentType.JSON.toString()).
                                        withStatus(200).
                                        withBody(customerJson.toString()))));
        // Stub response for Auth
        wireMockServer.
                givenThat(
                        (get(urlEqualTo("/auth")).
                                willReturn(aResponse().
                                        withHeader("Content-Type", ContentType.ANY.toString()).
                                        withHeader("JSESSIONID", "AS34979870H").
                                        withStatus(200))));

        // Stub response for SOAP
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();
        SOAPEnvelope envelope = soapPart.getEnvelope();
        SOAPBody soapBody = envelope.getBody();
        // Add namespace declarations
        soapBody.addNamespaceDeclaration("ns2", "http://namespace2.com");

        SOAPElement vendorElement = soapBody.addChildElement("Vendor", "ns2");
        vendorElement.addChildElement("id", "ns2").addTextNode("1290");
        vendorElement.addChildElement("name", "ns2").addTextNode("John Smith");
        vendorElement.addChildElement("phone", "ns2").addTextNode("650-222-3333");

        soapMessage.saveChanges();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        soapMessage.writeTo(out);
        String soapMessageInText = new String(out.toByteArray());

        wireMockServer.
                givenThat(
                        (post(urlEqualTo("/v1/vendors")).
                                willReturn(aResponse().
                                        withHeader("Content-Type", ContentType.XML.toString()).
                                        withBody(soapMessageInText).
                                        withStatus(200))));

        // Stub response for all other requests (setting to lowest priority)
        wireMockServer.
                givenThat(
                        (any(urlPathMatching(".*")).atPriority(10).
                                willReturn(aResponse().
                                        withHeader("Content-Type", ContentType.ANY.toString()).
                                        withStatus(200))));

    }

    /**
     * Example of simple get request.
     */
    @Test
    public void testSimpleGet() {
        //@formatter:off
        given().
                spec(requestSpec).
                contentType(ContentType.JSON).
        when().
                get("/customers/1").
        then().assertThat().
                statusCode(200).
                body("customer.firstname", equalTo("Jane")).
                body("customer.lastname", equalTo("Smith"));
        //@formatter:on
    }

    /**
     * Example of session filter in request.
     */
    @Test
    public void testSessionFilter() {
        SessionFilter filter = new SessionFilter();
        //@formatter:off
        given().
                spec(requestSpec).
                auth().basic("janesmith", "xx93k2m3b1").
                filter(filter).
        when().
                get("/auth").
        then().assertThat().
                header("JSESSIONID", notNullValue());

        // Reuse session filter that is now populated with session id
        given().
                spec(requestSpec).
                filter(filter).
        when().
                get("/customers/2");
        //@formatter:on
    }

    /**
     * Example of object serialization in request.
     */
    @Test
    public void testObjectSerialization() {
        Customer customer = new Customer();
        customer.setFirstname("Jane");
        customer.setLastname("Smith");
        //@formatter:off
        Response response =
                given().
                        spec(requestSpec).
                        body(customer).
                when().
                        post("/customers/");
        //@formatter:on
    }

    /**
     * Example of object deserialization in response.
     */
    @Test
    public void testObjectDeserialization() {
        //@formatter:off
        Customer customer =
                given().
                        spec(requestSpec).
                when().
                        get("/customers/3").
                then().assertThat().
                        extract().jsonPath().getObject("customer", Customer.class);
        //@formatter:on
        logger.debug(customer.toString());
    }

    /**
     * Example of getting a list from response body.
     */
    @Test
    public void testGetListOfAllPhones() {
        Response resp = given().spec(requestSpec).get("/customers/4");
        List<HashMap> phones = from(resp.asString()).getList("customer.phone", HashMap.class);
        // Phones which you should be asserting on
        for (HashMap<String, String> map : phones) {
            logger.debug(map.toString());
        }
    }

    /**
     * Example of getting a list from response body using groovy closure.
     */
    @Test
    public void testGetOnlyWorkPhones() {
        Response resp = given().spec(requestSpec).get("/customers/4");
        List<Map> phones = from(resp.asString()).get("customer.phone.findAll { phone-> phone.type == \"work\" }");
        // Phones which you should be asserting on
        for (Map map : phones) {
            logger.debug(map.toString());
        }
    }

    /**
     * Example of asserting an array item.
     */
    @Test
    public void testComplexAssertionsGetArrayItem() {
        //@formatter:off
        Phone phone =
                given().
                        spec(requestSpec).
                when().
                        get("/customers/4").
                then().
                        body("customer.phone.type", hasItems("work", "fax")).
                        extract().jsonPath().getObject("customer.phone[0]", Phone.class);
        //@formatter:on
        assertThat(phone.getType(), is("work"));
    }

    /**
     * Example of asserting arrays.
     */
    @Test
    public void testComplexAssertionsGetList() {
        //@formatter:off
        List<HashMap> phoneList =
                given().
                        spec(requestSpec).
                when().
                        get("/customers/4").
                then().
                        body("customer.phone.type", hasItems("work", "fax")).
                        extract().jsonPath().getList("customer.phone", HashMap.class);
        //@formatter:on
        assertThat(phoneList.size(), Matchers.is(3));
        for (HashMap phone : phoneList) {
            logger.debug(phone.toString());
            String phoneNumber = phone.get("number").toString();
            switch (phoneNumber) {
                case "650-123-4566":
                    assertThat(phone.get("type").toString(), is("work"));
                    break;
                case "650-123-4567":
                    assertThat(phone.get("type").toString(), is("work"));
                    break;
                case "650-123-4568":
                    assertThat(phone.get("type").toString(), is("fax"));
                    break;
                default:
                    Assert.fail(phoneNumber + "Did not match any of the expected phone numbers");
                    break;
            }
        }
    }

    /**
     * Example of asserting using groovy closures.
     */
    @Test
    public void testComplexAssertionsUsingGroovyClosures() {
        //@formatter:off
        List<HashMap> phoneList =
                given().
                        spec(requestSpec).
                when().
                        get("/customers/5").
                then().
                        extract().jsonPath().get("customer.phone.findAll { phone -> phone.type == \"work\" }");
        //@formatter:on
        assertThat(phoneList.size(), Matchers.is(2));
    }

    /**
     * Example of soap messaging in RestAssured.
     * @throws Exception
     */
    @Test
    public void testSOAPMessage() throws Exception {
        /*
        Construct SOAP Request Message instead of hardcoding as a string which is error prone:
        <SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
            <SOAP-ENV:Header/>
            <SOAP-ENV:Body xmlns:ns2="http://namespace2.com">
                <n2:GetVendor>
                    <n2:id>1290</n2:id>
                </n2:GetVendor>
            </SOAP-ENV:Body>
        </SOAP-ENV:Envelope>
         */
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();
        SOAPEnvelope envelope = soapPart.getEnvelope();
        SOAPBody soapBody = envelope.getBody();

        // Add namespace declarations
        soapBody.addNamespaceDeclaration("ns2", "http://namespace2.com");

        SOAPElement vendorElement = soapBody.addChildElement("Vendor", "ns2");
        vendorElement.addChildElement("id", "ns2").addTextNode("1290");
        soapMessage.saveChanges();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        soapMessage.writeTo(out);
        String soapMessageInText = new String(out.toByteArray());

        /* Response stub returned from WireMock
        <SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
            <SOAP-ENV:Header/>
            <SOAP-ENV:Body xmlns:ns2="http://namespace2.com">
                <ns2:Vendor>
                    <ns2:id>1290</ns2:id>
                    <ns2:name>John Smith</ns2:name>
                    <ns2:phone>650-222-3333</ns2:phone>
                </ns2:Vendor>
            </SOAP-ENV:Body>
        </SOAP-ENV:Envelope>
         */

        //@formatter:off
        response = given().
                config(RestAssuredConfig.newConfig().
                        xmlConfig(XmlConfig.xmlConfig().
                                namespaceAware(true).
                                declareNamespace("ns2", "http://namespace2.com"))).
                spec(requestSpec).
                header("SOAPAction", "http://localhost/getVendor").
                contentType("application/soap+xml; charset=UTF-8;").
                body(soapMessageInText).
        when().
                post("/v1/vendors").
        then().assertThat().
                contentType(ContentType.XML).
                statusCode(200).
                extract().response();
        //@formatter:on

        // One way to assert
        String responseString = response.asString();
        String vendorName = new XmlPath(responseString).getString("Envelope.Body.Vendor.name");
        assertThat(vendorName, is("John Smith"));

        // Another way to assert
        vendorName = XmlPath.with(responseString).get("Envelope.Body.Vendor.name");
        assertThat(vendorName, is("John Smith"));
    }

}
