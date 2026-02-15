package org.shekor.tcgmatcher.api;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TcgPlayerClient {

    private static final String BASE_URL = "https://api.tcgplayer.com";
    private static final String TOKEN_URL = BASE_URL + "/token";

    private final HttpClient httpClient;
    private String accessToken;

    private final String publicKey;
    private final String privateKey;

    public TcgPlayerClient(String publicKey, String privateKey) {
        this.httpClient = HttpClient.newHttpClient();
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    // =============================
    // 1️⃣ Authenticate and get token
    // =============================
    public void authenticate() throws Exception {

        String body = "grant_type=client_credentials"
                + "&client_id=" + publicKey
                + "&client_secret=" + privateKey;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(TOKEN_URL))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> response =
                httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // TODO: Parse JSON properly using Jackson/Gson
        // For now we do a crude extraction
        String responseBody = response.body();

        String token = responseBody
                .split("\"access_token\":\"")[1]
                .split("\"")[0];

        this.accessToken = token;

        System.out.println("Authenticated successfully.");
    }

    // =============================
    // 2️⃣ Search for a product ID by card name
    // =============================
    public String searchProduct(String cardName) throws Exception {

        String url = BASE_URL
                + "/catalog/products?productName="
                + cardName.replace(" ", "%20");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + accessToken)
                .GET()
                .build();

        HttpResponse<String> response =
                httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        String responseBody = response.body();

        // TODO: Proper JSON parsing
        // Extract first productId (simplified)
        String productId = responseBody
                .split("\"productId\":")[1]
                .split(",")[0];

        return productId;
    }

    // =============================
    // 3️⃣ Get sellers for a product
    // =============================
    public String getListings(String productId) throws Exception {

        String url = BASE_URL
                + "/pricing/product/"
                + productId;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + accessToken)
                .GET()
                .build();

        HttpResponse<String> response =
                httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }
}

