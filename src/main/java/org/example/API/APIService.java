package org.example.API;

public class APIService {
    private static final String API_KEY = "";
    private static final String BASE_URL = "https://newsapi.org/v2/top-headlines";

    // Change method to public to allow other classes to access it
    public String buildRequestUrl() {
        return BASE_URL + "?apiKey=" + API_KEY + "&language=en&pageSize=3";
    }
}
