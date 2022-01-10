package dev.emileboucher.blackjackml.gamehandlers;

import dev.emileboucher.blackjackml.api.requests.RequestBuilder;
import dev.emileboucher.blackjackml.models.requests.*;
import dev.emileboucher.blackjackml.models.responses.BlackJackResponse;
import dev.emileboucher.blackjackml.models.responses.Card;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class PlayerHandler {
  private final BlackJackClient client = new BlackJackClient("http://localhost:3000");
  private BlackJackResponse response = new BlackJackResponse();

  public PlayerHandler() {
    response.cash = 1000;
    response.playerHand = new ArrayList<>();
    response.dealerHand = new ArrayList<>();
  }

  public BlackJackResponse deal(Integer bet) {
    client.resetCookies();
    return send(new Deal(bet));
  }

  public BlackJackResponse hit() {
    return send(new Hit());
  }

  public BlackJackResponse hold() {
    return send(new Hold());
  }

  public BlackJackResponse flag() {
    send(new Flag());
    client.resetCookies();
    return load();
  }

  public BlackJackResponse load() {
    return send(new Load());
  }

  private BlackJackResponse send(RequestBuilder builder) {
    BlackJackResponse resp = null;
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
