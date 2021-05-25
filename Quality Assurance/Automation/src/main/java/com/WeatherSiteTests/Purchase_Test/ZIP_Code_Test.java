package WeatherSiteTests.Purchase_Test;

import WeatherSiteTests.PaymentUtils;
import WeatherSiteTests.WeatherTest;
import org.apache.logging.log4j.LogManager;
import org.junit.BeforeClass;
import org.junit.Test;

public class ZIP_Code_Test extends WeatherTest {
    private static final String INSERT_INVALID_ZIP     = "Insert invalid zip code: ";
    private static final String NONE_DIGITS_ZIP        = "no digits in Zip code field";
    private static final String LONG_ZIP               = "The zip code is too long";
    private static final String SHORT_ZIP              = "The zip code is too short";
    private static final String EMPTY_ZIP              = "empty Zip code field";

    private static PaymentUtils paymentUtils;

    @BeforeClass
    public static void setLogger() {
        logger = LogManager.getLogger(ZIP_Code_Test.class.getName());
        paymentUtils = new PaymentUtils(driver,
                "data/PurchaseTest/zipCode",
                INSERT_INVALID_ZIP,
                ZIP_Code_Test.class);
    }

    /***
     * Checks if a zip code is accepted
     * if yes the test fail
     */
    @Test
    public void zipEmpty() {
        logger.info("Starting non correct test");
        paymentUtils.emptyTest(EMPTY_ZIP, false);
        logger.info("All non correct values pass");
    }

    /***
     * Checks if an zip codes without digits are accepted
     * if yes the test fail
     */
    @Test
    public void noneDigits() {
        logger.info("Starting non correct test");
        paymentUtils.test("noneDigits", NONE_DIGITS_ZIP, true);
        logger.info("All non correct values pass");
    }

    /***
     * Checks if an long zip codes are accepted
     * if yes the test fail
     */
    @Test
    public void longZip() {
        logger.info("Starting non correct test");
        paymentUtils.test("longZip", LONG_ZIP, true);
        logger.info("All non correct values pass");
    }

    /***
     * Checks if an short zip codes are accepted
     * if yes the test fail
     */
    @Test
    public void shortZip() {
        logger.info("Starting non correct test");
        paymentUtils.test("shortZip", SHORT_ZIP, true);
        logger.info("All non correct values pass");
    }
}
