package com.ebay.model.pages;

import com.ebay.model.BasePage;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import java.util.ArrayList;

public class SearchPage extends BasePage {

    final static Logger logger = Logger.getLogger(SearchPage.class);

    private static SearchPage instance = null;

    //Directory location to store the raw html pages (for debugging purposes)
    protected String PagesDirectory   = System.getProperty("user.dir") + "/pages/";

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
    private By liProducts = By.className("s-item");

    /**
     * Resources
     */
    /*
        eBay Search page URL

        _nkw = is the Query string
        _dmd = is to show in List View
        _ipg = is the number of results per search page
        _pgn = is the page number of the Search results page.
     */
    private final String URL = "https://www.ebay.com/sch/i.html?_nkw={keyword}&_dmd=1&_ipg=200&_pgn={pageNumber}";

    //The search results page has this pattern for the ListItem (LI) for products
    private final String IDPATTERN = "srp-river-results-listing{idNumber}";

    // Pattern for the extracting the URL from the page
    private final String AHREFPATTERN = "<a href=\"(.+?)\"";

    public void setPagesDirectory(String location)  {PagesDirectory = location;}

    /**
     * Search results for the keyword on a given page number
     * @param keyword - Query string
     * @param pageNumer - Search page number
     */
    public void goToSearchUrl(String keyword, int pageNumer) {
        String url = URL.replace("{keyword}", keyword).
                         replace("{pageNumber}", String.valueOf(pageNumer));
        logger.debug("keyword=" + keyword + ", pagenumber=" + pageNumer);

        webDriverInstance.goToUrl(url);

        //Archive the page (for any debugging)
        //String filename = "search_" + pageNumer + ".html";
        //utils.saveToFile(webDriverInstance.getPageSource(), PagesDirectory, filename);
    }

    /**
     * Open the product page by the url for a given LI row element
     * from the search results page
     * @param url - The product url
     * @param idNumber - LI row element id.
     * @param pageNumber - Search Page(_pgn) number
     */
    public void goToProductPage(String url, int idNumber, int pageNumber) throws Exception {
        //logger.debug("idNumber=" + idNumber + ", pagenumber=" + pageNumber);

        webDriverInstance.goToUrl(url);

        //Archive the page
        // String filename  = "product_" + pageNumber + "_" + idNumber +  ".html";
        // utils.saveToFile(webDriverInstance.getPageSource(), PagesDirectory, filename);
    }

    /**
     * Get Product url by the LI id number
     * @param idNumber - LI row element id.
     * @return The href url.
     */
    public String  getProductUrlById(int idNumber) {
        String productId = IDPATTERN.replace("{idNumber}", String.valueOf(idNumber));
        By id = By.id(productId);
        String innerText = webDriverInstance.getElementInnerHtml(id);
        String aHref = utils.getByRegEx(AHREFPATTERN, innerText);
        logger.debug("ahref=" + aHref);

        return aHref;
    }

    /**
     * Get the number of products found in the search page.
     * @return ArrayList of Product URLs from the search page
     */
    public int getNumberOfProducts() {
        return webDriverInstance.getElementSize(liProducts);
    }

    /**
     * Get Products list form the search results page
     * @return Count of products from the Search page
     */
    public ArrayList<String> getProductsList() throws Exception
    {
        ArrayList<String> productsList = new ArrayList<>();
        /*
        <li class="s-item   " data-view="mi:1686|iid:1" id="srp-river-results-listing1"><div class="s-item__wrapper clearfix"><div class="s-item__image-section"><div class="s-item__image"><a href="https://www.ebay.com/itm/Rolex-Explorer-Stainless-Steel-Black-Dial-Automatic-Mens-Watch-214270/153848293948?hash=item23d212aa3c:g:5L4AAOSw03VeWT5s" tabindex="-1" aria-hidden="true" data-track="{&quot;eventFamily&quot;:&quot;LST&quot;,&quot;eventAction&quot;:&quot;ACTN&quot;,&quot;actionKind&quot;:&quot;NAVSRC&quot;,&quot;actionKinds&quot;:[&quot;NAVSRC&quot;],&quot;operationId&quot;:&quot;2351460&quot;,&quot;flushImmediately&quot;:false,&quot;eventProperty&quot;:{&quot;moduledtl&quot;:&quot;mi:1686|iid:1|li:7400|luid:1|scen:Listings&quot;,&quot;parentrq&quot;:&quot;f0dd844b1700ad4d7f830d4dfffec73c&quot;,&quot;pageci&quot;:&quot;e08781fa-6992-11ea-8282-74dbd180c3f9&quot;}}" _sp="p2351460.m1686.l7400"><div class="s-item__image-wrapper"><div class="s-item__image-helper"></div><img class="s-item__image-img" alt="Rolex Explorer Stainless Steel Black Dial Automatic Mens Watch 214270" src="https://i.ebayimg.com/thumbs/images/g/5L4AAOSw03VeWT5s/s-l225.webp" loading="eager" onload="SITE_SPEED.ATF_TIMER.measure(this); if (performance &amp;&amp; performance.mark) { performance.mark(&quot;first-meaningful-paint&quot;); }" data-atftimer="1584589014938"></div></a></div></div><div class="s-item__info clearfix"><div class="s-item__title-hotness"></div><a class="s-item__link" href="https://www.ebay.com/itm/Rolex-Explorer-Stainless-Steel-Black-Dial-Automatic-Mens-Watch-214270/153848293948?hash=item23d212aa3c:g:5L4AAOSw03VeWT5s" data-track="{&quot;eventFamily&quot;:&quot;LST&quot;,&quot;eventAction&quot;:&quot;ACTN&quot;,&quot;actionKind&quot;:&quot;NAVSRC&quot;,&quot;actionKinds&quot;:[&quot;NAVSRC&quot;],&quot;operationId&quot;:&quot;2351460&quot;,&quot;flushImmediately&quot;:false,&quot;eventProperty&quot;:{&quot;moduledtl&quot;:&quot;mi:1686|iid:1|li:7400|luid:1|scen:Listings&quot;,&quot;parentrq&quot;:&quot;f0dd844b1700ad4d7f830d4dfffec73c&quot;,&quot;pageci&quot;:&quot;e08781fa-6992-11ea-8282-74dbd180c3f9&quot;}}" _sp="p2351460.m1686.l7400"><h3 class="s-item__title">Rolex Explorer Stainless Steel Black Dial Automatic Mens Watch 214270</h3></a><div class="s-item__subtitle"><span class="SECONDARY_INFO">Pre-Owned</span><span class="srp-separator srp-separator--TEXT_MIDDOT"> · </span>Rolex Explorer<span class="srp-separator srp-separator--TEXT_MIDDOT"> · </span>Stainless Steel</div><div class="s-item__details clearfix"><div class="s-item__detail s-item__detail--primary"><span class="s-item__price">$5,545.04</span>  <span class="s-item__trending-price">List price: <span class="clipped">Previous Price</span><span class="STRIKETHROUGH">$5,899.00</span></span></div><div class="s-item__detail s-item__detail--primary"><span class="s-item__purchase-options-with-icon" aria-label="">or Best Offer</span></div><div class="s-item__detail s-item__detail--primary"><span class="s-item__shipping s-item__logisticsCost"><span class="BOLD">Free Shipping</span></span></div><div class="s-item__detail s-item__detail--primary"><span class="s-item__free-returns s-item__freeReturnsNoFee"><span class="BOLD">Free Returns</span></span></div><div class="s-item__detail s-item__detail--primary"><span class="s-item__hotness s-item__authorized-seller" aria-label="Authenticity Verified"><span aria-hidden="true" role="img"><svg class="ebayui-image-clipped"><g id="ebayui-image-authorized-seller"><path d="M19 9.522a4.058 4.058 0 0 0-.969-2.65c-.35-.413-.593-.92-.625-1.459a4.03 4.03 0 0 0-1.182-2.63 4.054 4.054 0 0 0-2.617-1.176c-.544-.034-1.053-.277-1.466-.629A4.08 4.08 0 0 0 9.483 0a4.082 4.082 0 0 0-2.656.977c-.414.353-.922.596-1.466.63a4.052 4.052 0 0 0-2.617 1.175 4.03 4.03 0 0 0-1.182 2.64c-.03.536-.275 1.038-.62 1.452A4.055 4.055 0 0 0 0 9.462a4.05 4.05 0 0 0 .939 2.614c.347.42.592.927.62 1.47a4.052 4.052 0 0 0 3.802 3.847c.544.034 1.052.277 1.466.629A4.08 4.08 0 0 0 9.483 19a4.08 4.08 0 0 0 2.658-.978c.413-.352.922-.595 1.466-.63a4.054 4.054 0 0 0 2.617-1.174 4.03 4.03 0 0 0 1.18-2.622c.034-.541.279-1.045.629-1.459.599-.707.963-1.617.967-2.615" fill="#3256F8"></path><path d="M5.06 9.687v.001a.632.632 0 0 0 .005.895l.445-.45.446-.45a.635.635 0 0 0-.896.004M14.434 5.626a.634.634 0 0 0-.897.004h.001l-5.782 5.838-1.8-1.784-.891.9 2.25 2.228c.25.247.65.245.897-.004l-.004-.003.445-.449.003.002 5.781-5.837h.001a.635.635 0 0 0-.004-.895" fill="#FFF"></path></g></svg>
<svg aria-hidden="true" focusable="false" class="ebayui-image-authorized-seller" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px" viewBox="0 0 19 19" enable-background="new 0 0 19 19" xml:space="preserve"><use xlink:href="#ebayui-image-authorized-seller"></use></svg></span><span class="BOLD">Authenticity Verified</span></span></div><div class="s-item__detail s-item__detail--primary"><span class="s-item__dynamic s-item__additionalItemHotness"><span class="BOLD NEGATIVE">164 Watching</span></span></div><div class="s-item__detail s-item__detail--primary"><span class="s-item__watchheart"><a href="https://www.ebay.com/myb/WatchListAdd?item=153848293948&amp;pt=null&amp;srt=0100040000005041df8d956b82b1d45b6cc95a5cdd707ff3e7a8e9103e85026f7855b9b35e7049aeadddf740d2cf3a8a77749566b4062dd49eb4250baf7cd46e84840ae25d68ea548184b64ca0843b5021f59f6e726159&amp;ru=https%3A%2F%2Fwww.ebay.com%2Fsch%2Fi.html%3F_from%3DR40%26_nkw%3Drolex%26_sacat%3D0" aria-label="Tap to watch item" _sp="p2351460.m4114.l8480"><span class="s-item__watchheart--watch s-item__watchheart-text">Watch</span></a><span class="x-tooltip" id="srp-river-results-listing1-w1"><span class="tooltip tooltip--click flyout--js" id="flyout-18"><span class="tooltip__trigger" tabindex="0" aria-labelledby="tooltip-srp-river-results-listing1-w1" aria-describedby="tooltip-srp-river-results-listing1-w1" aria-controls="tooltip-srp-river-results-listing1-w1" aria-expanded="false"><button aria-hidden="true" tabindex="-1" class="s-item__toomanywatches__triggerbtn"></button></span><span id="tooltip-srp-river-results-listing1-w1" class="tooltip__overlay flyout-notice flyout-notice--information" role="tooltip"><button class="svg-icon tooltip__close-button" aria-label="" data-w-onclick="dismissFlyout|srp-river-results-listing1-w1"><svg aria-hidden="true" width="12" height="12"><use xlink:href="#svg-icon-close"></use></svg></button><span class="flyout-notice__pointer-top-left"></span></span></span></span></span></div></div></div></div></li>
        */
        int ProductsPerPage = getNumberOfProducts();

        //logger.info("ProductsPerPage = " + ProductsPerPage);

        for (int idNumber = 1; idNumber < ProductsPerPage; idNumber++)
        {
            String product_url = getProductUrlById(idNumber);
            productsList.add(product_url);
        }

        return productsList;
    }
}
