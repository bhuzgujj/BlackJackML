package dev.emileboucher.blackjackml.api;

import com.google.gson.Gson;
import dev.emileboucher.blackjackml.api.requests.RequestBuilder;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;

/**
 * A rest client that sends [RequestBuilder]
 */
public class RestClient {
    private final HttpClient client = HttpClient.newBuilder().build();
    private final String ip;
    private final Gson jsonParser = new Gson();
    private CookieManager cookieManager = new CookieManager();

    /**
     * Creation of a restclient
     * @param ip of the server
     */
    public RestClient(String ip) {
        if (!ip.endsWith("/")) {
            this.ip = ip + "/";
        } else {
            this.ip = ip;
        }
        CookieHandler.setDefault(cookieManager);
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ORIGINAL_SERVER);
    }

    /**
     * Send a request to the server et receives it.
     * @param requestBuilder builder to send
     * @return [Response] from the api queried
     * @throws IOException from the [HttpClient]
     * @throws InterruptedException from the [HttpClient]
     */
    public <Model> Model send(RequestBuilder requestBuilder, Class<Model> modelClass) throws IOException, InterruptedException {
        HttpResponse<String> response = client.send(
            requestBuilder.addIp(ip)
                .addCookie(cookieManager)
                .addType()
                .build(),
            HttpResponse.BodyHandlers.ofString()
        );
        updateCookies(response);
        return Parse(response.body(), modelClass);
    }

    /**
     * Reset the cookies in the CookieManager
     */
    public void resetCookies() {
        cookieManager = new CookieManager();
    }

    /**
     * Updates the cookies
     * @param response that have the cookies or not
     * @throws IOException from the [CookieManager]
     */
    private void updateCookies(HttpResponse<String> response) throws IOException {
        cookieManager.put(URI.create(ip), response.headers().map());
    }

    /**
     * Create an object from a string formated in json
     * @param body of the request
     * @param modelClass to return
     * @return the [Response]
     */
    private<Model> Model Parse(String body, Class<Model> modelClass) {
        return jsonParser.fromJson(body, modelClass);
    }
}
