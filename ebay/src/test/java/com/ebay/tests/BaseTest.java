package com.ebay.tests;

import com.ebay.model.pages.ProductPage;
import com.ebay.model.pages.SearchPage;
import com.ebay.utils.Utils;
import com.ebay.utils.WebDriverInstance;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import java.util.concurrent.TimeUnit;

public class BaseTest {

    final static Logger logger = Logger.getLogger(BaseTest.class);

    protected static WebDriverInstance webDriverInstance = WebDriverInstance.getWebDriverInstance();
    private static WebDriver driver = webDriverInstance.getDriver();
    protected Utils utils = Utils.getUtils();
    protected ProductPage productPage = ProductPage.getInstance();
    protected SearchPage searchPage = SearchPage.getInstance();

    @BeforeSuite
    public void setupSuite() {
        logger.info("Starting suite.");
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
    }

    @AfterSuite
    public void cleanupSuite() {
        logger.info("Ending suite.");
        webDriverInstance.tearDown();
    }

}
