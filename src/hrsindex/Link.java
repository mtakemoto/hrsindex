/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hrsindex;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

/**
 *
 * @author Matt
 */
public class Link {

    private String statuteName;
    private String url;
    private String section;

    Link(Element rawData) {
        this.url = rawData.attr("abs:href");
        this.section = parseSection(rawData.text());
        /*if(fileName.contains("_.") || fileName.contains("-.")) {
         section = "Table of Contents";
         }
         else {
         this.section = parseSection(fileName); 
         }*/
    }

    public String getURL() {
        return url;
    }

    public String getSection() {
        return section;
    }

    public String getName() {
        if (getSection().equals("Table of Contents")) {
            return statuteName + " " + getSection();
        }
        else {
            return statuteName + " " + "Section " + getSection();
        }
    }

    private String parseSection(String fileName) {
        //Initial processing and statute name setting
        int startIdx;
        int endIdx = fileName.indexOf('.');
        if (fileName.contains("-")) {
            startIdx = fileName.indexOf('-') + 1;
        }
        else {
            startIdx = fileName.indexOf('_') + 1;
        }
        statuteName = fileName.substring(0, startIdx - 1);
        
        //Table of contents detection and statute# extraction
        if (fileName.contains("_.") || fileName.contains("-.")) {
            return "Table of Contents";
        }
        else {
            String trimmedName = fileName.substring(startIdx, endIdx);
            trimmedName = trimmedName.replaceFirst("^0+(?!$)", ""); //regex removes leading zeros

            if (trimmedName.contains("_")) {
                int idx = trimmedName.indexOf('_');
                String beforeDec = trimmedName.substring(0, idx);
                String afterDec = trimmedName.substring(idx + 1);
                trimmedName = beforeDec + "." + afterDec.replaceFirst("^0+(?!$)", "");
            }
            return trimmedName;
        }
    }
}
