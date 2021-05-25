package WeatherSiteTests.Purchase_Test;

import WeatherSiteTests.PaymentUtils;
import WeatherSiteTests.WeatherTest;
import org.apache.logging.log4j.LogManager;
import org.junit.BeforeClass;
import org.junit.Test;

public class Mail_Test extends WeatherTest {
    private static final String INSERT_INVALID_MAIL    = "Insert invalid mail: ";
    private static final String MISSING_AT_SIGN_MAIL   = "Missing @ in email address";
    private static final String OVER_ONE_AT_Sign       = "More than one @ sign in email address";
    private static final String SPECIAL_CHAR           = "Email address with special characters";
    private static final String EMPTY_MAIL             = "Empty mail field";
    private static final String MISSING_PREFIX         = "Missing prefix to email address";

    private static PaymentUtils paymentUtils;

    @BeforeClass
    public static void setLogger() {
        logger =  LogManager.getLogger(Mail_Test.class.getName());
        paymentUtils = new PaymentUtils(driver,
                "data/PurchaseTest/mail",
                INSERT_INVALID_MAIL,
                Mail_Test.class);
    }

    /***
     * Checks if a empty mail is accepted
     * if yes the test fail
     */
    @Test
    public void mailEmpty() {
        logger.info("Starting non correct test");
        paymentUtils.emptyTest(EMPTY_MAIL, false);
        logger.info("All non correct values pass");
    }

    /***
     * Checks if an mail addresses without @ sign are accepted
     * if yes the test fail
     */
    @Test
    public void missingAtSign() {
        logger.info("Starting non correct test");
        paymentUtils.test("missingAtSign", MISSING_AT_SIGN_MAIL, false);
        logger.info("All non correct values pass");
    }

    /***
     * Checks if an mail addresses with over one @ sign are accepted
     * if yes the test fail
     */
    @Test
    public void overOneAtSign() {
        logger.info("Starting non correct test");
        paymentUtils.test("overOneAtSign", OVER_ONE_AT_Sign, true);
        logger.info("All non correct values pass");
    }

    /***
     * Checks if an mail addresses with special characters are accepted
     * if yes the test fail
     */
    @Test
    public void specialChar() {
        logger.info("Starting non correct test");
        paymentUtils.test("specialChar", SPECIAL_CHAR, true);
        logger.info("All non correct values pass");
    }

    /***
     * Checks if an mail addresses without prefix are accepted
     * if yes the test fail
     */
    @Test
    public void missingPrefix() {
        logger.info("Starting non correct test");
        paymentUtils.test("missingPrefix", MISSING_PREFIX, false);
        logger.info("All non correct values pass");
    }
}
