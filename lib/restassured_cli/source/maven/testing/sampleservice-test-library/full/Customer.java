package GROUPID.tests.library.schema;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * Please <b>DO NOT</b> develop any schema for testing. This is created for demo only.
 * This should be the same schema from your source code that should be made available.
 */
public class Customer {
    private final Logger logger = LoggerFactory.getLogger(Customer.class);
    private String firstname;
    private String lastname;
    private ArrayList<Phone> phone;
    private String city;
    private String state;
    private String zip;

    public ArrayList<Phone> getPhone() {
        return phone;
    }

    public void setPhone(ArrayList<Phone> phone) {
        this.phone = phone;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    @Override
    public String toString() {
        ObjectWriter prettyPrinter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            String json = prettyPrinter.writeValueAsString(this);
            return json;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
    }
}