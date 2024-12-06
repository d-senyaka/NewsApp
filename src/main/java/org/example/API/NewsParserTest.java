package org.example.API;

import org.junit.Test;
import java.util.List;

import static org.junit.Assert.*;
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertNotNull;

public class NewsParserTest {

    @Test
    public void testParseNews() {
        String sampleJson = "{ \"articles\": [" +
                "{ \"title\": \"Title 1\", \"description\": \"Description 1\", \"url\": \"http://example.com/1\", \"urlToImage\": \"\", \"publishedAt\": \"2023-12-01T10:00:00Z\", \"source\": {\"name\": \"Source 1\"}, \"author\": \"Author 1\", \"content\": \"Content 1\" }" +
                "] }";

        NewsParser parser = new NewsParser();
        List<ArticleAPI> articles = parser.parseNews(sampleJson);

        assertNotNull("Articles should not be null.", articles);
        assertFalse("Articles list should not be empty.", articles.isEmpty());
        System.out.println("NewsParserTest: Parsed articles: " + articles);
    }
}
