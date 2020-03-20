package com.ebay.utils;

import org.apache.log4j.Logger;

import java.io.FileWriter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.HashMap;

public class Utils {

    final static Logger logger = Logger.getLogger(Utils.class);

    private static Utils utils = null;

    private Utils() {

    }

    public static Utils getUtils() {
        if(utils == null)
            utils = new Utils();
        return utils;
    }

    /**
     * Get text using regex
     * @param pattern - regex pattern
     * @param matcher - regex matcher (text)
     * @return - first value found
     */
    public String getByRegEx(String pattern, String matcher) {
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(matcher);
        if (m.find()) {
            logger.debug("getByRegex=" + m.group(1));
            return m.group(1);
        } else {
            return "";
        }
    }

    /**
     * Save HTML page source to a file (
     * @param html - html data
     * @param filepath - full name regex matcher (text)
     */
    public void saveToFile(String html, String filepath)
    {
        try {
            FileWriter writer = new FileWriter(filepath);
            writer.write(html);
            writer.close();
        }
        catch(Exception e)
        {
            logger.error("Unable to write to file" + filepath + e);
        }
    }

    /**
     * Method used to sort a HashMap in descending order and print first maxValues elements
     * @param hashMap - HashMap to be sorted
     * @param maxValues - first maxValues elements to be printed
     */
    public void sortHashMap2(HashMap<String, Integer> hashMap, int maxValues) {
        LinkedHashMap<String, Integer> reverseSortedMap = new LinkedHashMap<>();

        hashMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));

        int iterator = 0;
        logger.info("Top " + maxValues + " most watched watches:");
        for(Map.Entry<String, Integer> entryMap : reverseSortedMap.entrySet()) {
            if(iterator < maxValues) {
                logger.info(entryMap.getValue() + " - " + entryMap.getKey());
                iterator++;
            } else {
                break;
            }
        }
    }
}
