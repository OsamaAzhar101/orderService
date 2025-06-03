package com.oasys.news_summary_automation.model;

public class NewsArticle {

    private String date;
    private String category;
    private String headline;
    private String source;
    private String url;
    private String summary;

    public NewsArticle() {
        // Default constructor
    }

    public NewsArticle(String date, String category, String headline, String source, String url, String summary) {
        this.date = date;
        this.category = category;
        this.headline = headline;
        this.source = source;
        this.url = url;
        this.summary = summary;
    }

    // Getters and Setters

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
