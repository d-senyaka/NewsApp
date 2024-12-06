package org.example.API;

import org.testng.annotations.Test;

import static org.junit.Assert.*;

public class ArticleFetcherTest {

    @Test
    public void testFetchAndProcessArticles() {
        ArticleFetcher fetcher = new ArticleFetcher();
        try {
            fetcher.fetchAndProcessArticles();
            System.out.println("ArticleFetcherTest: Articles fetched and processed successfully.");
        } catch (Exception e) {
            fail("ArticleFetcherTest: Fetch and process failed. " + e.getMessage());
        }
    }
}
