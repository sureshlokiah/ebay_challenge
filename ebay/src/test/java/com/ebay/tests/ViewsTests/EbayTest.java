package com.ebay.tests.ViewsTests;

import com.ebay.tests.BaseTest;
import org.testng.annotations.Test;
import java.util.HashMap;
import org.apache.log4j.Logger;

public class EbayTest extends BaseTest {

    final static Logger logger = Logger.getLogger(EbayTest.class);

    private final String keyword = "rolex";
    private final int maxNumOfPages = 5;
    private static int productsChecked = 0;
    private final int maxNumOfProductsToBeChecked = 250;
    private final int maxNumOfWatchesToBeDisplayed = 5;

    @Test
    public void testRolexMaxViews() {
        int countIssuesInSearchPage = 0;

        HashMap<String, Integer> watches = new HashMap<>();
        for (int pageNumber = 1; pageNumber <= maxNumOfPages && productsChecked < maxNumOfProductsToBeChecked && countIssuesInSearchPage < 3; pageNumber++) {
           searchPage.goToSearchUrl(keyword, pageNumber);
            String currentUrl = webDriverInstance.getCurrentUrl();
            int maxProductsPerPage = searchPage.getNumberOfProducts();
            for (int idNumber = 1; idNumber <= maxProductsPerPage && productsChecked < maxNumOfProductsToBeChecked && countIssuesInSearchPage < 3; idNumber++) {
                boolean isSuccess = searchPage.openProductById(idNumber);
                if (!isSuccess)
                {
                    countIssuesInSearchPage++;
                    continue;
                }
                int viewCount = productPage.getViewCount();
                String url = webDriverInstance.getCurrentUrl();
                logger.info(productsChecked + ".Views:" + viewCount + " - " + url);
                watches.put(url, viewCount);
                webDriverInstance.goToUrl(currentUrl);
                productsChecked++;
            }
        }
        if (countIssuesInSearchPage >= 3)
        {
            logger.error("SEARCHPAGEISSUE Count: " + countIssuesInSearchPage);
        }

        utils.sortHashMap2(watches,maxNumOfWatchesToBeDisplayed);
    }

}
