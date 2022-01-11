package dev.emileboucher.blackjackml.models;

import dev.emileboucher.blackjackml.api.RestClient;
import dev.emileboucher.blackjackml.api.requests.RequestBuilder;
import dev.emileboucher.blackjackml.models.responses.BlackJackResponse;

import java.io.IOException;

/**
 * RestClient for the blackjack api
 */
public class BlackJackClient {
  RestClient client;

  /**
   * Create a RestClient linked with the ip
   * @param ip of the api
   */
  public BlackJackClient(String ip) {
    this.client = new RestClient(ip);
  }

  /**
   * Reset the cookies of the client
   */
  public void resetCookies() {
    client.resetCookies();
  }

  /**
   * Send a request to the server et receives it.
   * @param requestBuilder builder to send
   * @return [Response] from the api queried
   * @throws IOException from the [HttpClient]
   * @throws InterruptedException from the [HttpClient]
   */
  public BlackJackResponse send(RequestBuilder requestBuilder) throws IOException, InterruptedException {
    return client.send(requestBuilder, BlackJackResponse.class);
  }
}
