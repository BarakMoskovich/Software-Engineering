package WeatherSiteTests;

import org.apache.logging.log4j.Logger;
import org.junit.*;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class WeatherTest {

    protected final static String WEB_DRIVER = "webdriver.chrome.driver";
    //    protected final String WEB_DRIVER_PATH = "C:/chromedriver_win32/chromedriver.exe";
    protected final String WEB_DRIVER_PATH = "C:\\Program Files (x86)\\chromedriver\\chromedriver.exe";
    protected final static String baseURL = "https://weathershopper.pythonanywhere.com/";
    protected static WebDriver driver;
    protected Map<String, Object> vars;
    protected JavascriptExecutor js;
    protected static Logger logger;

    @Before
    public void setUp() {
        System.setProperty(WEB_DRIVER, WEB_DRIVER_PATH);
        driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(1052, 666));
        js = (JavascriptExecutor) driver;
        vars = new LinkedHashMap<>();
    }

    @Rule(order = Integer.MIN_VALUE)
    public TestWatcher watch = new TestWatcher() {

        @Override
        protected void starting(Description description) {
            logger.info(description.getMethodName());
        }

        @Override
        protected void failed(Throwable e, Description description) {
            logger.debug(description.getMethodName() + " | Reason: " + e.getMessage());
        }

        @Override
        protected void succeeded(Description description) {
            logger.info(description.getMethodName());
        }
    };

    @After
    public void tearDown() {
        driver.quit();
    }
}
