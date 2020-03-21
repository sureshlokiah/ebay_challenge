package com.ebay.model.pages;

import com.ebay.model.BasePage;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;

public class ProductPage extends BasePage {

    final static Logger logger = Logger.getLogger(ProductPage.class);

    private static ProductPage instance = null;

    private ProductPage() {
    }

    public static ProductPage getInstance() {
        if (instance == null)
            instance = new ProductPage();
        return instance;
    }

    /**
     * Elements
     */
    private By viewCountDiv = By.id("vi_notification_new");

    /**
     * Resources
     */

    // Pattern for the extracting the ViewCount from the Product page.
    private final String VIEWCOUNTPATTERN = "<span style=\"font-weight:bold;\">(.+?) viewed per hour<\\/span>";


    /**
     *
     * @return number of views from the last hour
     */
    public int getViewCount() {
        int viewCount = 0;
        if(webDriverInstance.elementExists(viewCountDiv)) {
            String divText = webDriverInstance.getElementInnerHtml(viewCountDiv);
            String strViewCount = utils.getByRegEx(VIEWCOUNTPATTERN, divText);

            if (!strViewCount.isEmpty()) {
                viewCount = Integer.parseInt(strViewCount);
            }
        }
        logger.debug("Num. of watchers = " + viewCount);

        return viewCount;
    }

}
