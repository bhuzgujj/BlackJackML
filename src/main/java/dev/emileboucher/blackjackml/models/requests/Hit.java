package dev.emileboucher.blackjackml.models.requests;

import dev.emileboucher.blackjackml.api.requests.PostBuilder;

import java.net.URI;

/**
 * A POST request on the /hit
 */
public class Hit extends PostBuilder {

  /**
   * Add the ip of the server to the request
   * @param ip of the server
   * @return the RequestBuilder updated
   */
  @Override
  public Hit addIp(String ip) {
    builder.uri(URI.create(ip + "hit"));
    super.baseURL = URI.create(ip);
    return this;
  }
}
