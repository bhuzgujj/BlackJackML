package dev.emileboucher.blackjackml.api.requests;

import dev.emileboucher.blackjackml.api.models.Body;
import dev.emileboucher.blackjackml.api.models.EmptyBody;

import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.URI;
import java.net.http.HttpRequest;
import java.util.List;

/**
 * The builder of a request
 */
public abstract class RequestBuilder {
  /**
   * The request builder
   */
  public final HttpRequest.Builder builder = HttpRequest.newBuilder();
  /**
   * The base url of the request
   */
  public URI baseURL;
  /**
   * The body object
   */
  public Body body = new EmptyBody();

  //=======================================================================
  //  Function to override
  //-----------------------------------------------------------------------
  /**
   * Add the ip of the server to the request
   * @param ip of the server
   * @return the RequestBuilder updated
   */
  public abstract RequestBuilder addIp(String ip);

  /**
   * Set the type of request
   * @return the RequestBuilder updated
   */
  public abstract RequestBuilder addType();

  //=======================================================================
  //  Function with a default behavior
  //-----------------------------------------------------------------------
  /**
   * Add cookies to the request if there's one
   * @param manager cookies managers
   * @return the RequestBuilder updated
   */
  public RequestBuilder addCookie(CookieManager manager) {
    List<HttpCookie> cookies = manager.getCookieStore().get(baseURL);
    if (cookies.size() > 0) {
      builder.header("Cookie", cookies.get(0).toString());
    }
    return this;
  }

  /**
   * BUild the requestbuilder into an [HttpRequest]
   * @return the [HttpRequest]
   */
  public HttpRequest build() {
    return builder.build();
  }

  /**
   * Set the body manually
   * @param body of the request
   * @return the RequestBuilder updated
   */
  public RequestBuilder setBody(Body body) {
    this.body = body;
    return this;
  }
}
