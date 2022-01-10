package dev.emileboucher.blackjackml.gamehandlers;

import dev.emileboucher.blackjackml.api.RestClient;
import dev.emileboucher.blackjackml.api.requests.RequestBuilder;
import dev.emileboucher.blackjackml.models.responses.BlackJackResponse;

import java.io.IOException;

public class BlackJackClient {
  RestClient client;

  public BlackJackClient(String ip) {
    this.client = new RestClient(ip);
  }

  public void resetCookies() {
    client.resetCookies();
  }

  public BlackJackResponse send(RequestBuilder requestBuilder) throws IOException, InterruptedException {
    return client.send(requestBuilder, BlackJackResponse.class);
  }
}
