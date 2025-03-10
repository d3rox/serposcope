/* 
 * Serposcope - SEO rank checker https://serposcope.serphacker.com/
 * 
 * Copyright (c) 2016 SERP Hacker
 * @author Pierre Nogues <support@serphacker.com>
 * @license https://opensource.org/licenses/MIT MIT License
 */
package com.serphacker.serposcope.scraper.google;

import java.util.Random;


public class GoogleScrapSearch {
    
    private final static Random random = new Random();

    public GoogleScrapSearch() {
    }
    
    int resultPerPage = 10;
    int pages = 5;
    long minPauseBetweenPageMS = 0l;
    long maxPauseBetweenPageMS = 0l;
    String keyword;
    String tld = "com";
    String datacenter;
    GoogleDevice device = GoogleDevice.DESKTOP;
    String local;
    String customParameters;

    public int getResultPerPage() {
        return resultPerPage;
    }

    public void setResultPerPage(int resultPerPage) {
        this.resultPerPage = resultPerPage;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getTld() {
        return tld;
    }

    public void setTld(String tld) {
        this.tld = tld;
    }

    public String getDatacenter() {
        return datacenter;
    }

    public void setDatacenter(String datacenter) {
        this.datacenter = datacenter;
    }

    public GoogleDevice getDevice() {
        return device;
    }

    public void setDevice(GoogleDevice device) {
        this.device = device;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getCustomParameters() {
        return customParameters;
    }

    public void setCustomParameters(String customParameters) {
        this.customParameters = customParameters;
    }
    
    public void setPagePauseMS(long minMs, long maxMs){
        if(minMs > maxMs){
            throw new IllegalArgumentException("minMs > maxMs");
        }
        minPauseBetweenPageMS = minMs;
        maxPauseBetweenPageMS = maxMs;
    }
    
    public long getRandomPagePauseMS(){
        if(minPauseBetweenPageMS == maxPauseBetweenPageMS){
            return maxPauseBetweenPageMS;
        }
        return minPauseBetweenPageMS + (random.nextLong()%(maxPauseBetweenPageMS-minPauseBetweenPageMS));
    }

}