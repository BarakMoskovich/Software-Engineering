package WeatherSiteTests.Purchase_Test;

import WeatherSiteTests.PaymentUtils;
import WeatherSiteTests.WeatherTest;
import org.apache.logging.log4j.LogManager;
import org.junit.BeforeClass;
import org.junit.Test;

public class Date_Test extends WeatherTest {
    private static final String INSERT_INVALID_DATE    = "Insert invalid date: ";
    private static final String DATE_LENGTH            = "date length exceeds or lower than legal length";
    private static final String EXPIRED_DATE           = "card date is expired";
    private static final String EMPTY_DATE             = "empty date field";

    private static PaymentUtils paymentUtils;

    @BeforeClass
    public static void setLogger() {
        logger = LogManager.getLogger(Date_Test.class.getName());
        paymentUtils = new PaymentUtils(driver,
                "data/PurchaseTest/date",
                INSERT_INVALID_DATE,
                Date_Test.class);
    }

    /***
     * Checks if a empty date is accepted
     * if yes the test fail
     */
    @Test
    public void dateEmpty() {
        logger.info("Starting non correct test");
        paymentUtils.emptyTest(EMPTY_DATE, false);
        logger.info("All non correct values pass");
    }

    /***
     * Checks if an invalid length dates are accepted
     * if yes the test fail
     */
    @Test
    public void dateLength() {
        logger.info("Starting non correct test");
        paymentUtils.test("dateLength", DATE_LENGTH, false); // valid DDYY // DDYYYY
        logger.info("All non correct values pass");
    }

    /***
     * Checks if an expiration date is accepted
     * if yes the test fail
     */
    @Test
    public void expiredDate() {
        logger.info("Starting non correct test");
        paymentUtils.test("expiredDate", EXPIRED_DATE, false);
        logger.info("All non correct values pass");
    }
}