package WeatherSiteTests;

import WeatherSiteTests.Homepage_Test.TemperatureHomePage;
import WeatherSiteTests.ProductList_Test.AddMultipleItems_Test;
import WeatherSiteTests.ProductList_Test.ProductsList_Test;
import WeatherSiteTests.Purchase_Test.*;
import WeatherSiteTests.Sanity_Test.ListOfProducts;
import WeatherSiteTests.Sanity_Test.Sanity_EmptyingCart;
import WeatherSiteTests.Sanity_Test.Sanity_Payment;
import org.junit.runner.JUnitCore;
import org.junit.runner.notification.Failure;


public class AllTests {

    public static void runTestCases(Class... classes) {
        JUnitCore junit = new JUnitCore();
        org.junit.runner.Result result = junit.run(classes);
        for(Failure failure : result.getFailures())
            System.out.println(failure.toString());
    }

    public static void main(String[] args) {
        // Sanity Test Case
        runTestCases(
                ListOfProducts.class,
                Sanity_Payment.class,
                Sanity_EmptyingCart.class
        );

        // Payment Test Case
        runTestCases(
                Mail_Test.class,
                CardNumber_Test.class,
                Date_Test.class,
                CVC_Test.class,
                ZIP_Code_Test.class,
                RememberMe_Test.class
        );

        // Product list Test Case
        runTestCases(
                AddMultipleItems_Test.class,
                ProductsList_Test.class
        );

        // Home page Test Case
        runTestCases(
                TemperatureHomePage.class
        );
    }

}
