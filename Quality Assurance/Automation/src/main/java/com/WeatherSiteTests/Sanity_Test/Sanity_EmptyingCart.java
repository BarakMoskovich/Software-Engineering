package WeatherSiteTests.Sanity_Test;

import WeatherSiteTests.PaymentUtils;
import WeatherSiteTests.Purchase_Test.Mail_Test;
import WeatherSiteTests.WeatherTest;
import org.apache.logging.log4j.LogManager;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class Sanity_EmptyingCart extends WeatherTest {
    private static final String EMPTY_CART_FAIL       = "CRITICAL ERROR WHEN CART IS EMPTY - SANITY CHECK FAIL";
    private static final String NOT_EMPTY_CART_FAIL   = "CRITICAL ERROR WHEN CART NOT EMPTY - SANITY CHECK FAIL";
    private static PaymentUtils paymentUtils;

    @BeforeClass
    public static void setLogger() {
        logger = LogManager.getLogger(Sanity_EmptyingCart.class.getName());
        paymentUtils = new PaymentUtils(driver,
                "data",
                "",
                Sanity_EmptyingCart.class);
    }

    /***
     * Checks if you can pay when the cart is empty
     * if yes the test fail
     */
    @Test
    public void cartEmpty() {
        paymentUtils.openCart();
        if (!paymentUtils.openPaymentFrame(true))
            Assert.fail(EMPTY_CART_FAIL);
    }

    /***
     * Checks if you can pay when there are products in the cart
     * If not the test failed
     */
    @Test
    public void cartNotEmpty() {
        if (!paymentUtils.openPaymentFrame(false))
            Assert.fail(NOT_EMPTY_CART_FAIL);
    }
}
