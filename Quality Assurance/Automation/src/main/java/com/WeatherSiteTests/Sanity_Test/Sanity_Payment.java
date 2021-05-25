package WeatherSiteTests.Sanity_Test;

import WeatherSiteTests.PaymentUtils;
import WeatherSiteTests.WeatherTest;
import org.apache.logging.log4j.LogManager;
import org.junit.BeforeClass;
import org.junit.Test;

public class Sanity_Payment extends WeatherTest {
    private static final String SANITY_PAYMENT_ERR  = "CRITICAL ERROR IN PAYMENT - SANITY CHECK FAIL";
    private static PaymentUtils paymentUtils;

    @BeforeClass
    public static void setLogger() {
        logger = LogManager.getLogger(Sanity_Payment.class.getName());
        paymentUtils = new PaymentUtils(driver,
                "data",
                "",
                Sanity_Payment.class);
    }

    /***
     * Checks if valid payment values are accepted
     * If not the test failed
     */
    @Test
    public void validPurchase() {
        paymentUtils.sanityTest(SANITY_PAYMENT_ERR);
    }
}