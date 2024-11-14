package com.rumpus.rumpus.scraper;

import java.util.List;

import com.rumpus.common.Web.AbstractScraper;

abstract public class AbstractRumpusScraper extends AbstractScraper {

    public AbstractRumpusScraper(String browserVersion, String uri, List<String> params) {
        super(browserVersion, uri, params);
    }

    @Override
    public void run() {
        this.scrape();
    }
}
