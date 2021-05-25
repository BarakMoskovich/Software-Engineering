package WeatherSiteTests.ProductList_Test;

import WeatherSiteTests.ProductUtils;
import WeatherSiteTests.WeatherTest;
import org.apache.logging.log4j.LogManager;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.HashMap;


public class AddMultipleItems_Test extends WeatherTest {
    private final String PRODUCT_LIST_PATH   = "sunscreen";
    private static final int FIXED_ADD_LOOP  = 5;

    private ProductUtils proUtil;

    @BeforeClass
    public static void setLogger() {
        logger = LogManager.getLogger(AddMultipleItems_Test.class.getName());
    }

    /***
     * Checks if adding one product multiple times is done properly
     * if not the test fail
     */
    @Test
    public void addMultipleSameProduct() {
        logger.info("Starting adding multiple same product test");
        Assert.assertTrue("Critical error : Unable to open product list", proUtil.openProductPage(PRODUCT_LIST_PATH));
        HashMap<String, Integer> products = proUtil.addFixedProduct(FIXED_ADD_LOOP);

        Assert.assertTrue("Critical error : Unable to open cart", proUtil.gotoCart());
        HashMap<String, Integer> results = proUtil.getProductsInCart();

        if (!proUtil.checkProductInCart(products, results)) {
            Assert.fail("An error occurred while adding a product multiple times, may not have been able to added the product or not in the selected quantity.");
        }
    }

    /***
     * Checks if you have added multiple products is done properly
     * if not the test fail
     */
    @Test
    public void addMultipleProducts() {
        logger.info("Starting adding multiple products test");
        Assert.assertTrue("Critical error : Unable to open product list", proUtil.openProductPage(PRODUCT_LIST_PATH));
        HashMap<String, Integer> products = proUtil.addProductFromCatalog();

        Assert.assertTrue("Critical error : Unable to open cart", proUtil.gotoCart());
        HashMap<String, Integer> results = proUtil.getProductsInCart();

        if (!proUtil.checkProductInCart(products, results)) {
            Assert.fail("An error occurred while adding products, may not be added products or quantity of products");
        }
    }
}
