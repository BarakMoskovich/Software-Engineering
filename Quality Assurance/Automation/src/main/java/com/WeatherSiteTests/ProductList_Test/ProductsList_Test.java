package WeatherSiteTests.ProductList_Test;

import WeatherSiteTests.ProductUtils;
import WeatherSiteTests.WeatherTest;
import org.apache.logging.log4j.LogManager;
import org.junit.*;

import java.util.HashMap;

public class ProductsList_Test extends WeatherTest {
    private final String PRODUCT_LIST_PATH   = "sunscreen";

    private ProductUtils proUtil;

    @BeforeClass
    public static void setLogger() {
        logger = LogManager.getLogger(ProductsList_Test.class.getName());
    }

    @Before
    public void openProductList() {
        Assert.assertTrue("Critical error : Unable to open product list", proUtil.openProductPage(PRODUCT_LIST_PATH));
    }

    /***
     * Checks if it is possible to enter the products page
     * If not the test failed
     */
    @Test
    public void entryToProductsPage() {
        HashMap<String, Integer> results = proUtil.getCatalog();

        System.out.println(results);

        if (results.isEmpty())
            Assert.fail("Product list does not exist");
    }

    @Test
    /***
     * Checks if the product catalog appears correctly
     * Name, price and add button
     * If not the test failed
     */
    public void properDisplayOfProducts() {
        // Name, Price, Add button
        if (!proUtil.properDisplayOfCatalog())
            Assert.fail("Invalid product list: There may be a missing name, price, or add button");
    }

    /***
     * Checks if the product catalog appears regularly
     * If not the test failed
     */
    @Test
    public void uniformCatalog() {
        HashMap<String, Integer> results1 = proUtil.getCatalog();
        driver.navigate().refresh();
        HashMap<String, Integer> results2 = proUtil.getCatalog();

        if (!results1.equals(results2))
            Assert.fail("Non-uniform product list");
    }
}
