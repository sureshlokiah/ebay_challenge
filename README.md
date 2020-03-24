# eBay coding challenge

## Scope:
The scope of the checked-in code is kept (strictly) limited to the requested challenge and constraints :
* Starting on the ebay category page for Rolex watches (https://www.ebay.com/b/Rolex-Wristwatches/31387/bn_2989578), please find the five watches with the most views in the last hour.

## Documentation
See the java doc under ebay/docs to learn about the classes and methods used in this implemenation.

## Coding
The code is developed in Java and Selenium and uses Maven, TestNG. It follows OOP principles to creates Page objects and Utilities.  

Please review the code and structure under the directory ebay/src/test/java/. Also, see how the the eBay search page url is constructed(SearchPage.java) using the parameters - nkw, dmd, ipg, pgn.

## Steps to run
1. Download the project and open the pom.xml using IntelliJ.
2. Configure the "DRIVERPATH" in Constants.java  to point to the location of your chrome driver. (Tested with Chrome driver 80.0.3987.106)
3. Run the Test Class: com.ebay.tests.ViewsTests.EbayTest  Method: testRolexMaxViews.
4. --> See the reports in the output directory.

## Output
* Detail log messages showing the View count for the Product(url) are printed during the test execution. After the test execution the code will generate 2 test report files  - products.txt, top5.txt
* The file ebay/output/products.txt  - Shows the View count for each Watch Product.
* The file ebay/output/top5.txt  - Shows the Top 5 Watches having the most view counts.

## Known Issues
1. When running using Chrome driver ver:80.0.3987.106 the message  "[1585007104.349][SEVERE]: Timed out receiving message from renderer: 0.100" would be displayed on the terminal. This issue does not impact the test and the solution is to try with another driver version. 

## Next Steps
1. Expand the code to generalize and test with new keywords like iPhone, cars.
2. Handle negative test case keywords e.g. "THIS IS AN INVALID KEYWORD TEST" (eBay returns a message "No exact matches found" for invalid characters which the code could handle).
3. Package it to run from the command line.
4. Run in headless mode, and with simultaneous threads
5. Creating the code using Kotlin.
