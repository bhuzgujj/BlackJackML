package dev.emileboucher.blackjackml.api;

import com.google.gson.Gson;
import dev.emileboucher.blackjackml.api.models.Response;
import dev.emileboucher.blackjackml.api.requests.abstracts.RequestBuilder;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.function.Consumer;

/**
 * A rest client that sends [RequestBuilder]
 */
public class RestClient {
    private final HttpClient client;
    private final String ip;
    private final Gson jsonParser = new Gson();
    private final CookieManager cookieManager = new CookieManager();

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
        client = HttpClient.newBuilder().build();
        CookieHandler.setDefault(cookieManager);
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ORIGINAL_SERVER);
    }

    /**
     * Send a request to the server et receives it.
     * @param request builder to send
     * @param callback function to execute went successful
     * @param catcher of exceptions
     */
    public void send(RequestBuilder request, Consumer<Response> callback, Consumer<Exception> catcher) {
        try {
            HttpRequest req = request
                    .addIp(ip)
                    .addCookie(cookieManager)
                    .addType()
                    .build();
            HttpResponse<String> response = client.send(req, HttpResponse.BodyHandlers.ofString());
            updateCookies(response);
            callback.accept(Parse(response.body()));
        } catch (IOException | InterruptedException ex) {
            catcher.accept(ex);
        }
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
     * @return the [Response]
     */
    private Response Parse(String body) {
        return jsonParser.fromJson(body, Response.class);
    }
}
