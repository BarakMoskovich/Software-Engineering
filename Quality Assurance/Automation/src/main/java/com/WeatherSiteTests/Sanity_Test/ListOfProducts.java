package WeatherSiteTests.Sanity_Test;

import WeatherSiteTests.Homepage_Test.TemperatureHomePage;
import WeatherSiteTests.ProductList_Test.ProductsList_Test;
import WeatherSiteTests.Purchase_Test.Mail_Test;
import WeatherSiteTests.WeatherTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ListOfProducts extends WeatherTest {
    public final static String MOISTURIZER = TemperatureHomePage.EXPECTED_MOISTURIZERS_STRING.substring(0, TemperatureHomePage.EXPECTED_MOISTURIZERS_STRING.length() - 1).toLowerCase();
    public final static String SUNSCREEN = TemperatureHomePage.EXPECTED_SUNSCREENS_STRING.substring(0, TemperatureHomePage.EXPECTED_SUNSCREENS_STRING.length() - 1).toLowerCase();
    public final static String ADD_BUTTON_STRING = "Add";

    @BeforeClass
    public static void setLogger() {
        logger = LogManager.getLogger(ListOfProducts.class.getName());
    }

    private List<WebElement> getAllProductElements(String extendURL) {
        driver.get(baseURL + extendURL);
        return driver.findElements(By.cssSelector("div[class^='text-center col-4']"));
    }

    private int getPriceFromString(String priceElement) {
        int price = -1;

        if(priceElement != null) {
            price++;
            for(int i = 0; i < priceElement.length(); i++) {
                if(Character.isDigit(priceElement.charAt(i))) {
                    price = price*10 + Integer.parseInt(String.valueOf(priceElement.charAt(i)));
                }
            }
        }

        return price;
    }

    private boolean isA_ProductShown(WebElement webElement) {
        String productName = webElement.findElement(By.xpath("/html/body/div[1]/div[2]/div[1]/p[1]")).getText();

        String priceElement = webElement.findElement(By.xpath("/html/body/div[1]/div[2]/div[1]/p[2]")).getText();
        boolean isPriceElementContainsNumbers = getPriceFromString(priceElement) != -1;

        boolean isButtonExists = ADD_BUTTON_STRING.compareTo(
                webElement.findElement(
                        By.xpath("/html/body/div[1]/div[2]/div[1]/button")
                ).getText()
        ) == 0;

        Assert.assertNotNull("There is no description to product", productName);
        Assert.assertTrue("There is no price for products", isPriceElementContainsNumbers);
        Assert.assertTrue("There is no Add button for products", isButtonExists);

        return true;
    }

    private void checkProductInfo(String productsName) {
        logger.info("Checking products in " + productsName +
                " - Description, Price & Add button.");
        List<WebElement> l = getAllProductElements(productsName);

        logger.info("There is a list of products");
        for(WebElement webElement : l) {
            Assert.assertTrue("Some info is missing in the product", isA_ProductShown(webElement));
        }
    }

    private void backAndSameLink(String beingChecked, String URL_extention) {
        logger.info("Checking " + beingChecked + " static products after pressing" +
                " back button and returning to the same page");

        List<WebElement> expected = getAllProductElements(URL_extention);
        driver.get(baseURL);
        List<WebElement> result = getAllProductElements(URL_extention);

        Assert.assertEquals("Product list must be equal", expected.size(), result.size());

        Assert.assertTrue("At least one product changed",expected.containsAll(result));
    }

    private void refreshAndSameProduct(String beingChecked, String URL_extention) {
        logger.info("Checking " + beingChecked + " static products after pressing" +
                " back button and returning to the same page");

        List<WebElement> expected = getAllProductElements(URL_extention);
        List<WebElement> result = getAllProductElements(URL_extention);

        Assert.assertEquals("Product list must be equal", expected.size(), result.size());

        Assert.assertTrue("At least one product changed",expected.containsAll(result));
    }

    /***
     * Checks if it is possible to enter the products page (Moisturizer)
     * If not the test failed
     */
    @Test
    public void ProductsShownMoisturizers() {
        logger.info("Checking is there a list of products for " + TemperatureHomePage.EXPECTED_MOISTURIZERS_STRING);
        List<WebElement> l = getAllProductElements(MOISTURIZER);
        Assert.assertNotNull("There is no list of products", l);
    }

    /***
     * Checks if it is possible to enter the products page (Sunscreen)
     * If not the test failed
     */
    @Test
    public void ProductsShownSunscreens() {
        logger.info("Checking is there a list of products for " + TemperatureHomePage.EXPECTED_SUNSCREENS_STRING);
        List<WebElement> l = getAllProductElements(SUNSCREEN);
        Assert.assertNotNull("There is no list of products", l);
    }

    /***
     * Checks if the product catalog appears correctly (Moisturizer)
     * Name, price and add button
     * If not the test failed
     */
    @Test
    public void MoisturizersProductInfo() {
        logger.info("Checking product - Description, Price & Add button for " + TemperatureHomePage.EXPECTED_MOISTURIZERS_STRING);
        checkProductInfo(MOISTURIZER);
    }

    /***
     * Checks if the product catalog appears correctly (Sunscreen)
     * Name, price and add button
     * If not the test failed
     */
    @Test
    public void SunscreensProductInfo() {
        logger.info("Checking product - Description, Price & Add button for " + TemperatureHomePage.EXPECTED_SUNSCREENS_STRING);
        checkProductInfo(SUNSCREEN);
    }

    /***
     * Checks if the product list is constant after refresh (Sunscreen page)
     * if not the test fail
     */
    @Test
    public void StaticSunscreenProductsAfterRefresh() {
        refreshAndSameProduct(TemperatureHomePage.EXPECTED_SUNSCREENS_STRING, SUNSCREEN);
    }

    /***
     * Checks if the product list is constant after refresh (Moisturizer page)
     * if not the test fail
     */
    @Test
    public void StaticMoisturizerProductsAfterRefresh() {
        refreshAndSameProduct(TemperatureHomePage.EXPECTED_MOISTURIZERS_STRING, MOISTURIZER);
    }

    /***
     * Checks if the product list is constant after back and return (Sunscreen page)
     * if not the test fail
     */
    @Test
    public void StaticSunscreenProductsAfterBackAndReturn() {
        backAndSameLink(TemperatureHomePage.EXPECTED_SUNSCREENS_STRING, SUNSCREEN);
    }

    /***
     * Checks if the product list is constant after back and return (Moisturizer page)
     * if not the test fail
     */
    @Test
    public void StaticMoisturizerProductsAfterBackAndReturn() {
        backAndSameLink(TemperatureHomePage.EXPECTED_MOISTURIZERS_STRING, MOISTURIZER);
    }
}
