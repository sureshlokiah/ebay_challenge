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

    //Product search query
    private final String keyword = "rolex";

    // Number of products to check
    private final int maxNumOfProductsToBeChecked = 250;

    //Top list of sorted watches by View count.
    private final int maxNumOfWatchesToBeDisplayed = 5;

    //Limit the eBay page searches to maxNumOfPages
    private final int maxNumOfPages = 3;


    private static int productsChecked = 0;



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

                //2. Get the products URL from the search results page.
                ArrayList<String> productsList = searchPage.getProductsList();

                int idNumber = 0;   // is the number to identify the  LI row.
                while ( (idNumber < productsList.size() && productsChecked < maxNumOfProductsToBeChecked) )
                {
                    String url = productsList.get(idNumber);

                    //3. Open the Product page by the URL. Archive the page using idNumber and pageNumber.
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

        //8. Sort the HashMap to the Top5 and save the results.
        utils.sortHashMap_Save(watches,maxNumOfWatchesToBeDisplayed);
    }

}
