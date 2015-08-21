package GROUPID.tests.library.schema;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Please <b>DO NOT</b> develop any schema for testing. This is created for demo only.
 * This should be the same schema from your source code that should be made available.
 */
public class Phone {
    private final Logger logger = LoggerFactory.getLogger(Phone.class);
    private String type;
    private String number;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}