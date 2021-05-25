package WeatherSiteTests.Purchase_Test;

import WeatherSiteTests.PaymentUtils;
import WeatherSiteTests.WeatherTest;
import org.apache.logging.log4j.LogManager;
import org.junit.BeforeClass;
import org.junit.Test;

public class CardNumber_Test extends WeatherTest {
    private final static String INSERT_INVALID_CARD_NUMBER = "Insert invalid card number: ";
    private final static String EMPTY_CREDIT_CARD          = "Empty credit card number";
    private final static String SHORT_CREDIT_CARD          = "Credit card number is shorter than allowed";
    private final static String TEST_CREDIT_CARD           = "Credit card number for checking only";

    private static PaymentUtils paymentUtils;

    @BeforeClass
    public static void setLogger() {
        logger = LogManager.getLogger(CardNumber_Test.class.getName());
        paymentUtils = new PaymentUtils(driver,
                "data/PurchaseTest/cardNumber",
                INSERT_INVALID_CARD_NUMBER,
                CardNumber_Test.class);
    }

    /***
     * Checks if a empty credit card number is accepted
     * if yes the test fail
     */
    @Test
    public void cardNumberEmpty() {
        logger.info("Starting non correct test");
        paymentUtils.emptyTest(EMPTY_CREDIT_CARD, false);
        logger.info("All non correct values pass");
    }

    /***
     * Checks if credit card numbers for tests are accepted
     * if yes the test fail
     */
    @Test
    public void cardNumberTest() {
        logger.info("Starting non correct test");
        paymentUtils.test("cardNumberTest", TEST_CREDIT_CARD, true);
        logger.info("All non correct values pass");
    }

    /***
     * Checks if short credit card numbers are accepted
     * if yes the test fail
     */
    @Test
    public void cardNumberShort() {
        logger.info("Starting non correct test");
        paymentUtils.test("cardNumberShort", SHORT_CREDIT_CARD, false);
        logger.info("All non correct values pass");
    }
}
