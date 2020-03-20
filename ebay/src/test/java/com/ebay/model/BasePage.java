package com.ebay.model;

import com.ebay.utils.Utils;
import com.ebay.utils.WebDriverInstance;

public class BasePage {

    protected static WebDriverInstance webDriverInstance = WebDriverInstance.getWebDriverInstance();
    protected static Utils utils = Utils.getUtils();

}
