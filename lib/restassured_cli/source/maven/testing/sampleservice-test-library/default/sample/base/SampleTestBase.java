package GROUPID.tests.library.sample.base;

import GROUPID.tests.library.base.ServiceTestBase;

/**
 * Base class for common business logic that applies to all tests.
 * Please put any utility/helper functions in SampleServiceHelper in test-library module.
 */
public class SampleTestBase extends ServiceTestBase {

    public SampleTestBase() {
        super(Constant.PROPERTIES_FILE);
    }

}
