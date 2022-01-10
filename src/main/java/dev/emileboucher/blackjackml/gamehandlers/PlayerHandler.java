package dev.emileboucher.blackjackml.gamehandlers;

import dev.emileboucher.blackjackml.api.RestClient;
import dev.emileboucher.blackjackml.api.models.Card;
import dev.emileboucher.blackjackml.api.models.Response;

public class PlayerHandler {
  private final RestClient client = new RestClient("http://localhost:3000");
  private Response response = new Response();

  public PlayerHandler() {
    response.cash = 1000;
    /*
    Card card = new Card();
    card.suits = "MDR";
    card.rank = "4";
    response.playerHand.add(card);
    response.playerHand.add(card);
    response.dealerHand.add(card);
    */
  }

  public void deal(Integer bet) {

  }

  public void hit() {

  }

  public void hold() {

  }

  public void flag() {

  }

  public Response getResponse() {
    return response;
  }

  public void setResponse(Response response) {
    this.response = response;
  }
}
