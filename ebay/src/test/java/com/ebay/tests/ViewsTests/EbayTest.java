package com.ebay.tests.ViewsTests;

import com.ebay.tests.BaseTest;
import org.apache.commons.io.FileUtils;
import org.testng.annotations.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.log4j.Logger;

public class EbayTest extends BaseTest {

    final static Logger logger = Logger.getLogger(EbayTest.class);

    private final String keyword = "rolex";
    private final int maxNumOfPages = 5;
    private static int productsChecked = 0;
    private final int maxNumOfProductsToBeChecked = 25;
    private final int maxNumOfWatchesToBeDisplayed = 5;

/*
    public void testRolexMaxViews2() {

        HashMap<String, Integer> watches = new HashMap<>();

        try
        {
            int countIssuesInSearchPage = 0;

            searchPage.setPagesDirectory(pages_directory);

            //Writer to capture ViewCounts for Products in separate report file
            PrintWriter writer_products  = new PrintWriter(new FileWriter(output_directory + "products.txt"));

            for (int pageNumber = 1; pageNumber <= maxNumOfPages && productsChecked < maxNumOfProductsToBeChecked && countIssuesInSearchPage < 3; pageNumber++) {

                //1. Search for query string
                searchPage.goToSearchUrl(keyword, pageNumber + 1);

                String currentUrl = webDriverInstance.getCurrentUrl();

                //2. Get the number of products in the search results page.
                int maxProductsPerPage = searchPage.getNumberOfProducts();

                for (int idNumber = 1; idNumber <= maxProductsPerPage && productsChecked < maxNumOfProductsToBeChecked && countIssuesInSearchPage < 3; idNumber++) {

                    //3. For each Product url from the search results page, Open the Product page
                    boolean isSuccess = searchPage.openProductById(idNumber, pageNumber);
                    //3a. If there are issues in getting the product Id from search Page then continue
                    if (!isSuccess) { countIssuesInSearchPage++; continue;  }

                    //4. Get the View count for the product
                    int viewCount = productPage.getViewCount();

                    String url = webDriverInstance.getCurrentUrl();

                    //5. Log the ViewCount and the URL for the product.
                    logger.info(productsChecked + ".Views:" + viewCount + " - " + url);
                    writer_products.println(viewCount + " - " + url);
                    writer_products.flush();

                    watches.put(url, viewCount);

                    webDriverInstance.goToUrl(currentUrl);

                    productsChecked++;
                }
            }
            if (countIssuesInSearchPage >= 3) {
                logger.error("SEARCHPAGEISSUE Count: " + countIssuesInSearchPage);
            }
            writer_products.close();
        }
        catch(Exception e)
        {
            logger.error(e);
        }

        utils.sortHashMap2(watches, maxNumOfWatchesToBeDisplayed);
    }
*/

    @Test
    public void testRolexMaxViews() {

        HashMap<String, Integer> watches = new HashMap<>();

        try
        {
            searchPage.setPagesDirectory(pages_directory);

            //Writer to capture ViewCounts for Products in separate report file
            PrintWriter writer_products  = new PrintWriter(new FileWriter(output_directory + "products.txt"));

            for (int pageNumber = 1; pageNumber <= maxNumOfPages && productsChecked < maxNumOfProductsToBeChecked; pageNumber++) {

                //1. Search ebay site for the query keyword string
                searchPage.goToSearchUrl(keyword, pageNumber);

                //2. Get the number of products in the search results page.
                ArrayList<String> productsList = searchPage.getProductsList();

                int idNumber = 0;
                while ( (idNumber < productsList.size() && productsChecked < maxNumOfProductsToBeChecked) )
                {
                    String url = productsList.get(idNumber);

                    //3. Open the Product page by the URL. Archive the page using idNumber and pageNumber
                    searchPage.goToProductPage(url, (idNumber+1), pageNumber);

                    //4. Get the View count for the product
                    int viewCount = productPage.getViewCount();

                    //5. Save the data -> url and viewCount into watches.
                    watches.put(url, viewCount);

                    //6. Log the ViewCount and the URL for the product.
                    logger.info((productsChecked + 1) + ". Views: " + viewCount + " <-> " + url);

                    //7. Write the report.
                    writer_products.println(viewCount + " <-> " + url);
                    writer_products.flush();

                    productsChecked++;
                    idNumber++;
                }
            }
            writer_products.close();
        }
        catch(Exception e)
        {
            logger.error(e);
        }

        //8. Sort the HashMap and
        utils.sortHashMap_Save(watches,maxNumOfWatchesToBeDisplayed);
    }

}
