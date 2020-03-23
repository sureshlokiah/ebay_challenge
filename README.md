# eBay coding challenge

## Scope:
The scope of the checked-in code is kept strictly limited to the requested challenge and constraints :
* Starting on the ebay category page for Rolex watches (https://www.ebay.com/b/Rolex-Wristwatches/31387/bn_2989578), please find the five watches with the most views in the last hour.

## Coding
The code is developed in Java and Selenium and uses Maven, TestNG. It follows OOP principles to creates Page objects and Utilities.  

Please review the code and structure under directory src/test/java/. Also, see how the the eBay search page url is constructed using the parameters - nkw, dmd, ipg, pgn.

## Steps to run
1. Download the project and open the pom.xml it in IntelliJ
2. Configure the path DRIVERPATH in Constants.java  to point to the location of your chrome driver
3. Run the Test Class: com.ebay.tests.ViewsTests.EbayTest  Method: testRolexMaxViews

## Output
* Log messages will show the View count for the Product URL. Also, after the test completes it would generate 2 test report files  - products.txt, top5.txt
* The file ebay/output/products.txt will capture the View count for each Watch Product
* The file ebay/output/top5.txt will show the Top 5 Watches having the most view counts.

## Next Steps
1. Expand the code to handle new keywords like iPhone, cars and, negative test case keywords (THIS IS AN INVALID KEYWORD TEST). eBay returns a message "No exact matches found" for invalid characters which the code could handle.
2. Run in headless mode, simultaneous threads
3. Package it to run from the command line.
