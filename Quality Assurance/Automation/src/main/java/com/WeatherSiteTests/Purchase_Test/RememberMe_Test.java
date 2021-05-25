package WeatherSiteTests.Purchase_Test;

import WeatherSiteTests.PaymentUtils;
import WeatherSiteTests.WeatherTest;
import org.apache.logging.log4j.LogManager;
import org.junit.BeforeClass;
import org.junit.Test;

public class RememberMe_Test extends WeatherTest {
    private final static String INSERT_INVALID_MAIL = "Insert invalid phone number to Remember Me: ";
    private final static String PHONE_NUMBER_EMPTY  = "Empty phone number field";
    private final static String INVALID_LENGTH      = "Invalid phone number length";

    private static PaymentUtils paymentUtils;

    @BeforeClass
    public static void setLogger() {
        logger = LogManager.getLogger(RememberMe_Test.class.getName());
        paymentUtils = new PaymentUtils(driver,
                "data/PurchaseTest/rememberMe",
                INSERT_INVALID_MAIL,
                RememberMe_Test.class);
    }

    /***
     * Checks if a phone number is accepted
     * if yes the test fail
     */
    @Test
    public void phoneNumberEmpty() {
        logger.info("Starting non correct test");
        paymentUtils.emptyTest(PHONE_NUMBER_EMPTY, false);
        logger.info("All non correct values pass");
    }

    /***
     * Checks if an invalid phone numbers are accepted
     * if yes the test fail
     */
    @Test
    public void invalidLengthPhoneNumber() {
        logger.info("Starting non correct test");
        paymentUtils.test("invalidLengthPhoneNumber", INVALID_LENGTH, true);
        logger.info("All non correct values pass");
    }
}
