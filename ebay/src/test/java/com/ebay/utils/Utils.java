package com.ebay.utils;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
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
     * @return - first value found otherwise empty string
     */
    public String getByRegEx(String pattern, String matcher) {
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(matcher);
        if (m.find()) {
            //logger.debug("getByRegex=" + m.group(1));
            return m.group(1);
        } else {
            return "";
        }
    }

    /**
     * Save to html file
     * @param html - regex pattern
     * @param location  - regex matcher (text)
     * @param filename  - regex matcher (text)
     */
    public void saveToFile(String html, String location, String filename)
    {
        File directory = new File(location);
        if (!directory.exists())
            directory.mkdirs();

        String filepath = location + filename;

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
    public void sortHashMap_Save(HashMap<String, Integer> hashMap, int maxValues) {
        try{
            String output_directory = System.getProperty("user.dir") + "/output/";
            new File(output_directory).mkdirs();

            PrintWriter writer_top5  = new PrintWriter(new FileWriter(output_directory + "top5.txt"));
            LinkedHashMap<String, Integer> reverseSortedMap = new LinkedHashMap<>();

            hashMap.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));

            int iterator = 0;
            logger.info("\nTop " + maxValues + " most watched watches:");
            for(Map.Entry<String, Integer> entryMap : reverseSortedMap.entrySet()) {
                if(iterator < maxValues) {
                    logger.info(entryMap.getValue() + " - " + entryMap.getKey());
                    writer_top5.println(entryMap.getValue() + " - " + entryMap.getKey());
                    writer_top5.flush();
                    iterator++;
                } else {
                    break;
                }
            }
            writer_top5.close();
        }
        catch(Exception e){
            logger.error(e);
        }
    }
}
