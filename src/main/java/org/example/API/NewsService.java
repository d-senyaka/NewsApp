package org.example.API;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class NewsService {

    public String fetchNewsData() throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        APIService apiService = new APIService();
        String requestUrl = apiService.buildRequestUrl();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(requestUrl))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}
