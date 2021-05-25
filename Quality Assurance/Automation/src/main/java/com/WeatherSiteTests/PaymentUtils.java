package WeatherSiteTests;

import WeatherSiteTests.Purchase_Test.*;
import WeatherSiteTests.Sanity_Test.ListOfProducts;
import org.apache.logging.log4j.LogManager;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class PaymentUtils extends WeatherTest {
    @BeforeClass
    public static void setLogger() {
        System.setProperty("log4j.configurationFile", "log4j2_Purchase.properties");
        logger = LogManager.getLogger(ListOfProducts.class);
    }

    private final String ITEM_LIST_PATH             = "sunscreen";
    private final String CART_PATH                  = "cart";
    private final String OPEN_WEBSITE               = "Opening a website";
    private final String OPENING_PAYMENT_WINDOW     = "Opening a payment window";
    private final String GOTO_CART                  = "Go to cart page";
    private final String ADD_TO_CART                = "Adding item to cart";
    private final String INSERT_VALID_MAIL          = "Insert valid email";
    private final String INSERT_VALID_CARD_NUMBER   = "Insert valid card number";
    private final String INSERT_VALID_DATE          = "Insert valid card date";
    private final String INSERT_VALID_CVC           = "Insert valid card CVC";
    private final String INSERT_VALID_ZIP_CODE      = "Insert valid zip code";
    private final String REMEMBER_ME_CLICK          = "Click on Remember me";
    private final String REMEMBER_ME_PHONE          = "Insert phone number to Remember me";
    private final String VERIFICATION_CODE          = "Checks if an verification pane is open";
    private final String PAY                        = "Click on Pay button";

    private final String TIMEOUT                    = "Test time over, An error may have occurred.";
    private final String CANT_OPEN_WEB              = "Critical error: Failed to open site.";
    private final String NULL_POINTER_EXCEPTION     = "Error: Not found element.";

    // Default values
    private final String VALID_MAIL         = "something@domain.com";
    private final String ITEM               = "item"; // name of any item
    private final String VALID_CARD_NUMBER  = "4242424242424242";
    private final String VALID_DATE         = "1030"; // MMYY
    private final String VALID_CVC          = "123"; // Any 3 digits
    private final String VALID_ZIP_CODE     = "123456";


    public WebDriver drive;
    private String path;
    private FileUtils file;
    private ArrayList<String> valuesList;
    private Class<?> pClass;
    private String action;
    private Random random;

    public PaymentUtils(WebDriver drive, String path, String action, Class<?> pClass) {
        this.drive = drive;
        this.path = path;
        this.action = action;
        this.pClass = pClass;
        this.valuesList = new ArrayList<>();
        this.random = new Random();
    }

    private boolean runner(String value, String debugMsg) {
        String deb = action + debugMsg + ", VALUE = [" + value + "]";
        openPaymentFrame(false);

        if (pClass.equals(Mail_Test.class))
            insertMail(value, deb);
        else
            insertMail();

        if (pClass.equals(CardNumber_Test.class))
            insertCardNumber(value, deb);
        else
            insertCardNumber();


        if (pClass.equals(Date_Test.class))
            insertCardDate(value, deb);
        else
            insertCardDate();

        if (pClass.equals(CVC_Test.class))
            insertCVC(value, deb);
        else
            insertCVC();

        if (pClass.equals(ZIP_Code_Test.class))
            insertZipCode(value, deb);
        else
            insertZipCode();

        if (pClass.equals(RememberMe_Test.class))
            rememberMe(value);

        return pay();
    }

    private boolean checker(ArrayList<String> data, String debugMsg, Boolean assertBoolean)  {
        boolean runResult;
        for (String value : data) {
            runResult = runner(value,debugMsg);
            if (runResult == false && pClass.equals(RememberMe_Test.class))
                runResult = verificationCode();

            if (assertBoolean)
                Assert.assertTrue("An incorrect value passed the test", runResult);
            else
                Assert.assertFalse("An incorrect value passed the test", runResult);
        }
        return true;
    }

    public void test(String fileName, String debugMsg, Boolean assertBoolean)  {
        file = new FileUtils(fileName, path);
        valuesList.clear();
        valuesList = file.read();

        checker(valuesList, debugMsg, assertBoolean);
    }

    public void emptyTest(String debugMsg, Boolean assertBoolean) {
        valuesList.clear();
        valuesList.add("");
        checker(valuesList, debugMsg, assertBoolean);
    }

    public void sanityTest(String debugMsg) {
        valuesList.clear();
        valuesList.add("");
        if (!checker(valuesList, debugMsg, true))
            Assert.fail(debugMsg);
    }

    public boolean openCart() {
        try {
            logger.info(OPEN_WEBSITE);
            driver.get(baseURL + CART_PATH);
            return true;
        } catch (Exception e) { logger.error(CANT_OPEN_WEB); }
        return false;
    }

    public boolean openCartAndAddItem(String path, String item, int price) {
        try {
            js = (JavascriptExecutor) driver;

            logger.info(OPEN_WEBSITE);
            driver.get(baseURL + path);

            logger.info(ADD_TO_CART + "[name=" + item + ", price=" + price + "]");
            js.executeScript("addToCart('" + item + "'," + price + ")");

            logger.info(GOTO_CART);
            js.executeScript("goToCart()");
            return true;
        } catch (NullPointerException npe) {
          Assert.fail(NULL_POINTER_EXCEPTION);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
        return false;
    }

    public boolean openPaymentFrame(boolean emptyCart) {
        try {
            if (!emptyCart) {
                int price = random.nextInt(300) + 100; // 100-400
                openCartAndAddItem(ITEM_LIST_PATH, ITEM, price);
            } else {
                openCart();
            }

            logger.info(OPENING_PAYMENT_WINDOW);
            Objects.requireNonNull(findDynamicElement(By.xpath("/html/body/div[1]/div[3]/form/button"), 5)).click();

            WebDriverWait wait = new WebDriverWait(driver, 5);
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("stripe_checkout_app"));

            return (emptyCart) ? false : true;
        } catch (NullPointerException npe) {
            Assert.fail(NULL_POINTER_EXCEPTION);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
        return false;
    }

    private void insertMail() {
        insertMail(VALID_MAIL, INSERT_VALID_MAIL);
    }

    private void insertMail(String mail, String log_msg) {
        try {
            logger.info(log_msg);
            Objects.requireNonNull(findDynamicElement(By.cssSelector("input[type='email']"), 5)).sendKeys(mail);
            if (verificationCode()) {
                Thread.sleep(1000);
                Objects.requireNonNull(findDynamicElement(By.cssSelector("span[class='Header-navBack']"), 5)).click();
            }
        } catch (NullPointerException npe) {
            Assert.fail(NULL_POINTER_EXCEPTION);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    private void insertCardNumber() {
        insertCardNumber(VALID_CARD_NUMBER, INSERT_VALID_CARD_NUMBER);
    }

    private void insertCardNumber(String card_number, String log_msg) {
        try {
            logger.info(log_msg);
            Objects.requireNonNull(findDynamicElement(By.cssSelector("input[placeholder='Card number']"), 5)).sendKeys(card_number);
        } catch (NullPointerException npe) {
            Assert.fail(NULL_POINTER_EXCEPTION);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    private void insertCardDate() {
        insertCardDate(VALID_DATE, INSERT_VALID_DATE);
    }

    private void insertCardDate(String card_date, String log_msg) {
        try {
            logger.info(log_msg);
            Objects.requireNonNull(findDynamicElement(By.cssSelector("input[placeholder='MM / YY']"), 5)).sendKeys(card_date);
        } catch (NullPointerException npe) {
            Assert.fail(NULL_POINTER_EXCEPTION);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    private void insertCVC() {
        insertCVC(VALID_CVC, INSERT_VALID_CVC);
    }

    private void insertCVC(String cvc, String log_msg) {
        try {
            logger.info(log_msg);
            Objects.requireNonNull(findDynamicElement(By.cssSelector("input[placeholder='CVC']"), 5)).sendKeys(cvc);
        } catch (NullPointerException npe) {
            Assert.fail(NULL_POINTER_EXCEPTION);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    private void rememberMe(String phoneNumber) {
        try {
            logger.info(REMEMBER_ME_CLICK);
            Objects.requireNonNull(findDynamicElement(By.cssSelector("div[class='Section Section--fillTop']"), 2)).click();
            logger.info(REMEMBER_ME_PHONE);
            findDynamicElement(By.xpath("/html/body/div[2]/section/span[2]/div/div/main/form/div/div/div/div/div/div[2]/div/div/div/div/fieldset/fieldset/span/div/div[1]/input"),
                    5).sendKeys(phoneNumber);
        } catch (NullPointerException npe) {
            Assert.fail(NULL_POINTER_EXCEPTION);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    private void insertZipCode() {
        insertZipCode(VALID_ZIP_CODE, INSERT_VALID_ZIP_CODE);
    }

    private void insertZipCode(String zip_code, String log_msg) {
        try {
            logger.info(log_msg);
            findDynamicElement(By.cssSelector("input[placeholder='ZIP Code']"), 5).sendKeys(zip_code);
        } catch (Exception e) {
            // Continue
        }
    }

    private boolean pay() {
        try {
            logger.info(PAY);

            Objects.requireNonNull(findDynamicElement(By.xpath("//*[@id=\"container\"]/section/span[2]/div/div/main/form/nav/div/div/div/button"), 5)).click();

            if (!hasElement(driver.findElements(By.cssSelector("div")), "class", "is-invalid"))
                return true;
        } catch (NullPointerException npe) {
            Assert.fail(NULL_POINTER_EXCEPTION);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
        return false;
    }

    private boolean verificationCode() {
        try {
            logger.info(VERIFICATION_CODE);
            WebElement element = findDynamicElement(By.cssSelector("div[class='CodeNotReceived-actionMessage']"), 2);

            if (element == null)
                return false;
        } catch (NullPointerException npe) {
            Assert.fail(NULL_POINTER_EXCEPTION);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
        return true;
    }

    private WebElement findDynamicElement(By by, int timeOut) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, timeOut);
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            return element;
        } catch (Exception e) {
            // Continue
        }
        return null;
    }

    private boolean hasElement(List<WebElement> elements, String attribute, String active) {
        try {
            for (WebElement webElement : elements) {
                if (webElement.getAttribute(attribute).contains(active))
                    return true;
            }
        } catch (NullPointerException npe) {
            Assert.fail(NULL_POINTER_EXCEPTION);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
        return false;
    }
}