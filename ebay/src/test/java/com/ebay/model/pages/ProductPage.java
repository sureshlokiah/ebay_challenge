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
    private final String VIEWCOUNTPATTERN = "<span style=\"font-weight:bold;\">(.+?) viewed per hour<\\/span>";


    /**
     *
     * @return number of views from the last hour
     */
    public int getViewCount() {
        if(webDriverInstance.elementExists(viewCountDiv)) {
            String divText = webDriverInstance.getElementInnerHtml(viewCountDiv);
            String viewCount = utils.getByRegEx(VIEWCOUNTPATTERN, divText);
            if(viewCount.isEmpty()) {
                logger.debug("no watchers");
                return 0;
            }
            logger.debug("num of watchers=" + viewCount);
            return Integer.parseInt(viewCount);
        }
        logger.debug("no watchers");
        return 0;
    }

}
