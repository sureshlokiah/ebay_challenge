package com.ebay.model.pages;

import com.ebay.model.BasePage;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;

public class SearchPage extends BasePage {

    final static Logger logger = Logger.getLogger(SearchPage.class);

    private static SearchPage instance = null;

    private SearchPage() {
    }

    public static SearchPage getInstance() {
        if (instance == null)
            instance = new SearchPage();
        return instance;
    }

    /**
     * Elements
     */
    private By liProducts = By.xpath("//li[contains(@id,'srp-river-results-listing')]");

    /**
     * Resources
     */
    private final String URL = "https://www.ebay.com/sch/i.html?_nkw={keyword}&_dmd=1&_pgn={pageNumber}";
    private final String IDPATTERN = "srp-river-results-listing{idNumber}";
    private final String AHREFPATTERN = "<a href=\"(.+?)\"";

    public void goToSearchUrl(String keyword, int pageNumer) {
        String url = URL.replace("{keyword}", keyword).
                         replace("{pageNumber}", String.valueOf(pageNumer));
        logger.debug("keyword=" + keyword + ", pagenumber=" + pageNumer);
        webDriverInstance.goToUrl(url);
        String filepath = "/tmp/search_" + keyword + "_" + pageNumer + ".html";
        utils.saveToFile(webDriverInstance.getPageSource(),filepath);

    }

    public boolean  openProductById(int idNumber) {
        String productId = IDPATTERN.replace("{idNumber}", String.valueOf(idNumber));
        By id = By.id(productId);
        String innerText = webDriverInstance.getElementInnerHtml(id);

        //ToCheck: At least one time after a 1hour test the code encountered
        // a failure in getting the product url from the search page, though it is parsed to exist!
        // We catch the error and return false as failure.
        if (innerText.equals(""))
        {
            logger.debug("SEARCHPAGEISSUE: Unable to find " + productId);
            return false;
        }

        String aHref = utils.getByRegEx(AHREFPATTERN, innerText);
        logger.debug("ahref=" + aHref);
        webDriverInstance.goToUrl(aHref);
        String filepath = "/tmp/product_page" + ".html";
        utils.saveToFile(webDriverInstance.getPageSource(), filepath);
        return true;
    }

    public int getNumberOfProducts() {
        return webDriverInstance.getElementSize(liProducts);
    }

}
