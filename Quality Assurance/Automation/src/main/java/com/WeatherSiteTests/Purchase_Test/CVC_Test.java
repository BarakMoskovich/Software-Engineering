package WeatherSiteTests.Purchase_Test;

import WeatherSiteTests.PaymentUtils;
import WeatherSiteTests.WeatherTest;
import org.apache.logging.log4j.LogManager;
import org.junit.BeforeClass;
import org.junit.Test;

public class CVC_Test extends WeatherTest {
    private static final String INSERT_INVALID_CVC     = "Insert invalid CVC: ";
    private static final String NONE_DIGITS_CVC        = "no digits in CVC field";
    private static final String CHAR_AND_DIGITS_CVC    = "char and digits in CVC field";
    private static final String SPECIAL_CHAR_CVC       = "special chars in CVC field";
    private static final String SHORT_CVC              = "CVC doesn't meet length requirements";
    private static final String EMPTY_CVC              = "empty CVC field";

    private static PaymentUtils paymentUtils;

    @BeforeClass
    public static void setLogger() {
        logger = LogManager.getLogger(CVC_Test.class.getName());
        paymentUtils = new PaymentUtils(driver,
                "data/PurchaseTest/CVC",
                INSERT_INVALID_CVC,
                CVC_Test.class);
    }

    /***
     * Checks if a empty CVC is accepted
     * if yes the test fail
     */
    @Test
    public void cvcEmpty() {
        logger.info("Starting non correct test");
        paymentUtils.emptyTest(EMPTY_CVC, false);
        logger.info("All non correct values pass");
    }

    /***
     * Checks if CVCs without digits are accepted
     * if yes the test fail
     */
    @Test
    public void noneDigits() {
        logger.info("-- Starting non correct test");
        paymentUtils.test("noneDigits", NONE_DIGITS_CVC, false);
        logger.info("All non correct values pass");
    }

    /***
     * Checks if CVCs with characters and digits are accepted
     * if yes the test fail
     */
    @Test
    public void charAndDigits() {
        logger.info("Starting non correct test");
        paymentUtils.test("charAndDigits", CHAR_AND_DIGITS_CVC, false);
        logger.info("All non correct values pass");
    }

    /***
     * Checks if CVCs with special characters are accepted
     *if yes the test fail
     */
    @Test
    public void specialChars() {
        logger.info("Starting non correct test");
        paymentUtils.test("specialChars", SPECIAL_CHAR_CVC, false);
        logger.info("All non correct values pass");
    }

    /***
     * Checks if short (<3) CVCs are accepted
     * if yes the test fail
     */
    @Test
    public void shortCVC() {
        logger.info("Starting non correct test");
        paymentUtils.test("shortCVC", SHORT_CVC, false);
        logger.info("All non correct values pass");
    }
}
