package WeatherSiteTests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class ProductUtils extends WeatherTest {
    private static final String OPEN_WEBSITE = "Opening a website";
    private static final String GOTO_CART    = "Go to cart page";
    private static final String ADD_TO_CART  = "Adding product to cart";
    private static final String FIXED_NAME   = "Cream UV-30";
    private static final int FIXED_PRICE     = 100;
    private static final Random RAND = new Random();
    private static final JavascriptExecutor js = (JavascriptExecutor) driver;

    public static HashMap<String, Integer> getCatalog() {
        HashMap<String, Integer> products = new HashMap<>();
        try {
            String rowText, tempName = "", price;
            List<WebElement> allProducts = driver.findElements(By.cssSelector("div[class^='text-center col-4']"));

            for (WebElement product : allProducts) {
                List<WebElement> productDetails = new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(product, By.tagName("p")));

                for (WebElement rowElement : productDetails) {
                    rowText = rowElement.getText();
                    if (rowText.contains("Price: Rs.")) {
                        price = rowText.substring(rowText.lastIndexOf(" ") + 1);
                        products.put(tempName, Integer.parseInt(price));
                    } else {
                        tempName = rowText;
                    }
                }
            }

            return products;
        } catch (Exception E) {
            logger.info("Problem with get product in product page");
        }
        return products;
    }

    public static boolean properDisplayOfCatalog() {
        try {
            String rowText;
            int productCounter = 0, detailsCounter = 0, elementCounter = 0;

            List<WebElement> allProducts = driver.findElements(By.cssSelector("div[class^='text-center col-4']"));

            for (WebElement product : allProducts) {
                rowText = product.getText();

                if (detailsCounter == 1 && rowText.contains("Price: Rs.")) { // if price
                    detailsCounter++;
                } else if (detailsCounter == 2 && rowText.contains("Add")) { // if price button
                    detailsCounter = 0;
                    productCounter++;
                } else if (detailsCounter > 3) { // something is missing
                    return false;
                } else { // name
                    detailsCounter++;
                }
                elementCounter++;
            }

            if (elementCounter / 3 == productCounter)
                return true;
        } catch (Exception E) {
            logger.info("Problem with get product in product page");
            return false;
        }
        return false;
    }

    public static boolean checkProductInCart(HashMap<String, Integer> values, HashMap<String, Integer> results) {
        if (values.isEmpty() || results.isEmpty())
            return false;
        else if (values.equals(results))
            return true;
        return false;
    }

    public static HashMap<String, Integer> getProductsInCart() {
        HashMap<String, Integer> products = new HashMap<>();
        try {
            WebElement tableProducts = driver.findElement(By.cssSelector("table[class='table table-striped']"));

            List<WebElement> tableRows = new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(tableProducts, By.tagName("tr")));

            String rowText, name, price;
            int index;

            for (WebElement rowElement : tableRows) {
                rowText = rowElement.getText();
                index   = rowText.lastIndexOf(" ");
                name    = rowText.substring(0, index);
                price   = rowText.substring(index + 1);

                if (!rowText.equals("Item Price"))
                    products.put(name, Integer.parseInt(price));
            }
            return products;

        } catch (Exception E) {
            logger.info("Problem with get product in carts");
        }
        return products;
    }

    public static HashMap<String, Integer> addFixedProduct(int products_number) {
        HashMap<String, Integer> products = new HashMap<>();

        for (int i = 0; i < products_number; i++) {
            if (!addProduct(FIXED_NAME, FIXED_PRICE))
                return null;

            if (products.containsKey(FIXED_NAME))
                products.put(FIXED_NAME, products.get(FIXED_NAME) + FIXED_PRICE);
            else
                products.put(FIXED_NAME, FIXED_PRICE);
        }

        return products;
    }

    public static HashMap<String, Integer> addProductFromCatalog() {
        HashMap<String, Integer> products = new HashMap<>();
        HashMap<String, Integer> catalog  = getCatalog();

        int products_number = RAND.nextInt(catalog.size()) + 1; // How much add products from catalog
        int multiple_number, price;
        String name;

        for (int i = 0; i < products_number; i++) {
            name  = catalog.keySet().toArray()[i].toString();
            price = catalog.get(name);
            multiple_number = RAND.nextInt(5) + 1;  // How much add each product

            for (int j = 0; j < multiple_number; j++) {
                if (!addProduct(name, price))
                    return null;
            }

            products.put(name, price * multiple_number);
        }

        return products;
    }

    public static boolean openProductPage(String path) {
        try {
            logger.info(OPEN_WEBSITE);
            driver.get(baseURL + path);
            return true;
        } catch (Exception e) {
            logger.info("Problem with entry to product list");
            return false;
        }
    }

    public static boolean gotoCart() {
        try {
            logger.info(GOTO_CART);
            js.executeScript("goToCart()");
            return true;
        } catch (Exception e) {
            logger.info("Problem with entry to cart");
            return false;
        }
    }

    public static boolean addProduct(String product, int price) {
        try {
            logger.info(ADD_TO_CART + "[name=" + product + ", price=" + price + "]");
            js.executeScript("addToCart('" + product + "'," + price + ")");
            return true;
        } catch (Exception e) {
            logger.info("Problem with adding product to cart");
            return false;
        }
    }
}
