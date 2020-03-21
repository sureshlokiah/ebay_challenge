package com.ebay.utils;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebDriverInstance {

    final static Logger logger = Logger.getLogger(WebDriverInstance.class);

    private static WebDriverInstance webDriverInstance = null;
    private static WebDriver driver;
    private static WebDriverWait wait;

    private WebDriverInstance(){
        System.setProperty("webdriver.chrome.driver", Constants.DRIVERPATH);
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }

    public WebDriver getDriver(){
        return driver;
    }

    public static WebDriverInstance getWebDriverInstance(){
        if(webDriverInstance == null)
            webDriverInstance = new WebDriverInstance();
        return webDriverInstance;
    }

    public void tearDown(){
        driver.close();
        if(driver!=null)
            driver.quit();
    }

    /**
     * Get inner HTML of an element
     * @param elementBy - find element by id/xpath/css etc
     * @return element's inner HTML
     */
    public String getElementInnerHtml(By elementBy) {
        try {
            return driver.findElement(elementBy).getAttribute("innerHTML");
        } catch (ElementNotFoundException e) {
            logger.error(e);
            return "";
        }
    }

    /**
     * Go to a specific url
     * @param url - url where to go
     */
    public void goToUrl(String url) {
        driver.get(url);
    }

    /**
     * Get current url
     * @return - current url
     */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    /**
     * Get Page Source
     * @return - html page
     */
    public String getPageSource() { return driver.getPageSource(); }

    /**
     * Checks if an element exists
     * @param elementBy - find elements by id/xpath/css etc
     * @return true if element exists, false if it doesn't exists
     */
    public boolean elementExists(By elementBy) {
        try {
            return driver.findElements(elementBy).size() > 0;
        } catch (ElementNotFoundException e) {
            logger.error(e);
            return false;
        }
    }

    /**
     * Get number of elements having specific locator
     * @param elementBy - find elements by id/xpath/css etc
     * @return number of elements
     */
    public int getElementSize(By elementBy) {
        try {
            return driver.findElements(elementBy).size();
        } catch (ElementNotFoundException e) {
            logger.error(e);
            return 0;
        }
    }
}
