package dev.emileboucher.blackjackml.gamehandlers;

import dev.emileboucher.blackjackml.api.requests.RequestBuilder;
import dev.emileboucher.blackjackml.models.BlackJackClient;
import dev.emileboucher.blackjackml.models.requests.*;
import dev.emileboucher.blackjackml.models.responses.BlackJackResponse;
import dev.emileboucher.blackjackml.models.responses.Card;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Player's interaction with the api
 */
public class PlayerHandler {
  private final BlackJackClient client = new BlackJackClient("http://localhost:3000");
  private BlackJackResponse response;

  //=======================================================================
  //  Constructors
  //-----------------------------------------------------------------------
  /**
   * Create an class to handle the player's interaction with the api
   */
  public PlayerHandler() {
    response = Optional.ofNullable(send(new Load()))
            .orElse(new BlackJackResponse());
  }

  //=======================================================================
  //  Requests
  //-----------------------------------------------------------------------
  /**
   * Send a request to the api
   * @param builder of the request
   * @return the response from the api
   */
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

  /**
   * A request to initiate the game
   * @param bet for the game
   */
  public void deal(Integer bet) {
    send(new Deal(bet));
  }

  /**
   * A request to get another card from the dealer
   */
  public void hit() {
    send(new Hit());
  }

  /**
   * A request to hold your current card at get if you win or not
   */
  public void hold() {
    send(new Hold());
  }

  /**
   * A request to finish a session if you have won that session
   */
  public void flag() {
    send(new Flag());
    client.resetCookies();
    load();
  }

  /**
   * A request to get the board state
   * @return the response from the api
   */
  public BlackJackResponse load() {
    return send(new Load());
  }

  //=======================================================================
  //  Getters and Setters
  //-----------------------------------------------------------------------
  /**
   * Get the cards that the dealer has
   * @return the cards that the dealer has
   */
  public List<Card> getDealerCards() {
    return Optional.ofNullable(response.dealerHand)
            .orElse(new LinkedList<>());
  }

  /**
   * Get the cards that the player has
   * @return the cards that the player has
   */
  public List<Card> getPlayerCards() {
    return Optional.ofNullable(response.playerHand)
            .orElse(new LinkedList<>());
  }

  /**
   * Tell if the game is still in progress
   * @return if the game is still in progress
   */
  public Boolean isPlaying() {
    return response.isPlaying();
  }

  /**
   * Get the money that the play has at the moment
   * @return the money that the play has at the moment
   */
  public Integer getCash() {
    return Optional.ofNullable(response.cash)
            .orElse(1000);
  }
}
