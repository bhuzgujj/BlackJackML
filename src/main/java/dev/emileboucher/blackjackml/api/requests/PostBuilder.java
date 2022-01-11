package dev.emileboucher.blackjackml.api.requests;

import com.google.gson.Gson;

import java.net.http.HttpRequest;

/**
 * Builder for a classic POST request
 */
public abstract class PostBuilder extends RequestBuilder {
  /**
   * Add a body to the request with the request type
   * @return the RequestBuilder updated
   */
  @Override
  public RequestBuilder addType() {
    String string = new Gson().toJson(body);
    builder.header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(string));
    return this;
  }
}
