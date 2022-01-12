package dev.emileboucher.blackjackml.models.responses;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * The response object structure from the api
 */
public class BlackJackResponse {
  public String state = "";
  public Integer cash = 1000;
  public List<Card> dealerHand = new LinkedList<>();
  public List<Card> playerHand = new LinkedList<>();

  /**
   * Get the string representing the state of the board
   * @return [String] of the state of the board
   */
  @Override
  public String toString() {
    if (dealerHand == null || dealerHand.size() < 1) return "EMPTY";
    StringBuilder val = new StringBuilder(dealerHand.get(0).toString() + "-");
    for (var card : playerHand) {
        val.append(card.toString());
    }
    return val.toString();
  }

  /**
   * Get the state of the game
   * @return [GameState]
   */
  public GameState getResult() {
    return Optional.ofNullable(state)
            .map((gstate) -> switch (gstate) {
                case "WON" -> GameState.WON;
                case "LOST" -> GameState.LOST;
                default -> GameState.TIE;
            })
            .orElse(GameState.TIE);
  }

  /**
   * Get the state of the session
   * @return [SessionState]
   */
  public SessionState getSessionState() {
    return Optional.ofNullable(cash)
            .map((money) -> {
                if (cash >= 2000) return SessionState.WON;
                if (cash <= 0) return SessionState.LOST;
                else return SessionState.PLAYING;
            })
            .orElse(SessionState.ERROR);
  }

  /**
   * Get if you can still do action in the game
   * @return [Boolean]
   */
  public Boolean isPlaying() {
    return Optional.ofNullable(state)
            .map((gstate) -> gstate.equals("IN_GAME"))
            .orElse(false);
  }

  /**
   * Get the current value of the player's hand
   * @return the current value of the player's hand
   */
  public int getPlayerHandValue() {
    int value = 0;
    int nbAces = 0;
    for (Card card : playerHand) {
      int cardVal = card.getValue();
      value += cardVal;
      if (cardVal > 10) {
        nbAces++;
      }
    }
    return aceAjustement(value, nbAces);
  }

  /**
   * Ajuste the value for aces if the current value is higher than 21
   * @param value of the hand currently
   * @param nbAces number of aces
   * @return ajusted valued for aces
   */
  private int aceAjustement(int value, int nbAces) {
    for (int i = 0; i < nbAces; i++) {
      if (value <= 21) return value;
      value -= 10;
    }
    return value;
  }
}
