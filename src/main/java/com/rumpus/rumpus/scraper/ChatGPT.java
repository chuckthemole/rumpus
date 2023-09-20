package com.rumpus.rumpus.scraper;

import java.util.List;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class ChatGPT extends AbstractRumpusScraper {

    private static final String URI = "https://openai.com";
    private final static List<String> parameterList = List.of( // parameters in the relative path, using default values but they can be set below
            "cc1=us",
            "pagename=residence-inn-by-marriott-norwalk",
            "r_lang=",
            "review_topic_category_id=",
            "type=total",
            "score=",
            "sort=",
            "time_of_year=",
            "dist=1",
            "offset=0",
            "rows=10",
            "rurl=",
            "text=",
            "translate=",
            "_=1687292000524");

    private ChatGPT() {
        super(BrowserVersion.CHROME.toString(), URI, null);
    }

    public static ChatGPT getInstance() {
        return new ChatGPT();
    }

    @Override
    public void scrape() {
        HtmlPage htmlPage = this.getHtmlPage();
        this.body = htmlPage.getBody().asNormalizedText();
        this.head = htmlPage.getHead().asNormalizedText();
        this.page = htmlPage.asNormalizedText();
    }
    
}
