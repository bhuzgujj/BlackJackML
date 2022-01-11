package dev.emileboucher.blackjackml.models.requests;

import dev.emileboucher.blackjackml.models.requests.bodies.DealBody;
import dev.emileboucher.blackjackml.api.requests.PostBuilder;

import java.net.URI;

/**
 * A POST request on the /deal
 */
public class Deal extends PostBuilder {
  /**
   * Create a request for a POST /deal
   * @param bet for the game
   */
  public Deal(int bet) {
    super.body = new DealBody(bet);
  }

  /**
   * Add the ip of the server to the request
   * @param ip of the server
   * @return the RequestBuilder updated
   */
  @Override
  public Deal addIp(String ip) {
    builder.uri(URI.create(ip + "deal"));
    super.baseURL = URI.create(ip);
    return this;
  }
}
