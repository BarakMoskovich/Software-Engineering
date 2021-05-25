package WeatherSiteTests.Homepage_Test;

import WeatherSiteTests.WeatherTest;
import org.apache.logging.log4j.LogManager;
import org.junit.*;
import org.openqa.selenium.*;

import java.util.List;

public class TemperatureHomePage extends WeatherTest {
    @BeforeClass
    public static void setLogger() {
        logger = LogManager.getLogger(TemperatureHomePage.class.getName());
    }

    public static final String EXPECTED_MOISTURIZERS_STRING = "Moisturizers";
    public static final String EXPECTED_SUNSCREENS_STRING = "Sunscreens";
    private static final int MINIMUM_SUNSCREEN_TEMP = 19;
    private static final int MAXIMUM_MOISTURIZERS_TEMP = 34;
    private boolean isMoisturizerTemp;
    private boolean isSunscreenTemp;

    private int getNumber(String text) {
        int num = 0;
        for(char c : text.toCharArray()) {
            if(Character.isDigit(c)) {
                num = num * 10 + Character.getNumericValue(c);
            }
        }
        return num;
    }

    private int assignProductByTemp(WebDriver driver) {
        String tempS = driver.findElement(By.id("temperature")).getText();
        int temp = getNumber(tempS.substring(0,2));

        isMoisturizerTemp = temp < 19;
        isSunscreenTemp = temp > 34;

        return temp;
    }

    /***
     * Checks if the temperature is constant
     * if no test fail
     */
    @Test
    public void homePageTemp() {
        // should show only 1 or less - moisturise || creams
        logger.info("Checking homepage buttons by temperature");

        driver.get(baseURL);
        int temperature = assignProductByTemp(driver);

        List<WebElement> a = driver.findElements(By.cssSelector("div[class^='text-center col-4']"));
        // ^= - starts with

        if(a.size() == 1) {
            String result = a.get(0).findElement(
                    By.cssSelector("div[class='text-center col-4']")).findElement(
                    By.tagName("h3")).getText();
            if(isMoisturizerTemp) {
                Assert.assertEquals(EXPECTED_MOISTURIZERS_STRING, result, "There should be only Moisturizers");
            } else if(isSunscreenTemp) {
                Assert.assertEquals(EXPECTED_SUNSCREENS_STRING, result, "There should be only Sunscreens");
            } else {
                // should be an error not available at this site version
                logger.error(
                        "Expected temperature above " + MINIMUM_SUNSCREEN_TEMP +
                                " or below " + MAXIMUM_MOISTURIZERS_TEMP +
                                ".\nGot: " + temperature
                );
                Assert.fail("Illegal temperature");
            }
        } else {
            logger.error(
                    "On temperature " + temperature + "Expected: " +
                            EXPECTED_MOISTURIZERS_STRING + " or " + EXPECTED_SUNSCREENS_STRING +
                            "\nGot: both."
            );
            Assert.fail("Should be only one of creams");
        }
    }
}
