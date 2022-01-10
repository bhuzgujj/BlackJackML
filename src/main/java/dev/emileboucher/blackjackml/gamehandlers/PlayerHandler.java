package dev.emileboucher.blackjackml.gamehandlers;

import dev.emileboucher.blackjackml.api.RestClient;
import dev.emileboucher.blackjackml.api.models.Card;
import dev.emileboucher.blackjackml.api.models.Response;
import dev.emileboucher.blackjackml.api.requests.abstracts.RequestBuilder;
import dev.emileboucher.blackjackml.api.requests.concretes.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class PlayerHandler {
  private final RestClient client = new RestClient("http://localhost:3000");
  private Response response = new Response();

  public PlayerHandler() {
    response.cash = 1000;
    response.playerHand = new ArrayList<>();
    response.dealerHand = new ArrayList<>();
  }

  public Response deal(Integer bet) {
    return send(new Deal(bet));
  }

  public Response hit() {
    return send(new Hit());
  }

  public Response hold() {
    return send(new Hold());
  }

  public Response flag() {
    return send(new Flag());
  }

  public Response load() {
    return send(new Load());
  }

  private Response send(RequestBuilder builder) {
    Response resp = null;
    try {
      resp = client.send(builder);
    } catch (IOException | InterruptedException e) {
      System.out.println(e.getMessage());
    }
    response = Optional.ofNullable(resp)
                  .orElse(response);
    return response;
  }

  public List<Card> getDealerCards() {
    return Optional.ofNullable(response.dealerHand)
            .orElse(new LinkedList<>());
  }

  public List<Card> getPlayerCards() {
    return Optional.ofNullable(response.playerHand)
            .orElse(new LinkedList<>());
  }

  public Boolean isPlaying() {
    return response.isPlaying();
  }

  public Integer getCash() {
    return Optional.ofNullable(response.cash)
            .orElse(1000);
  }
}
