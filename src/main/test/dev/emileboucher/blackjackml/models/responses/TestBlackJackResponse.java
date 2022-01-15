package dev.emileboucher.blackjackml.models.responses;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestBlackJackResponse {
  private final BlackJackResponse response = new BlackJackResponse("ERROR");
  @BeforeEach
  public void init() {
    response.dealerHand.add(new Card("4","mock"));
    response.playerHand.add(new Card("5","mock"));
    response.playerHand.add(new Card("2","mock"));
    response.playerHand.add(new Card("A","mock"));
    response.playerHand.add(new Card("A","mock"));
  }

  @Test
  public void getToString() {
    Assertions.assertEquals(response.toString(), "4-25AA");
  }

  @Test
  public void getResult() {
    Assertions.assertEquals(response.getResult(), GameState.TIE);
    response.state = null;
    Assertions.assertEquals(response.getResult(), GameState.TIE);
    response.state = "WON";
    Assertions.assertEquals(response.getResult(), GameState.WON);
    response.state = "LOST";
    Assertions.assertEquals(response.getResult(), GameState.LOST);
  }

  @Test
  public void getSessionState() {
    Assertions.assertEquals(response.getSessionState(), SessionState.PLAYING);
    response.cash = null;
    Assertions.assertEquals(response.getSessionState(), SessionState.ERROR);
    response.cash = 2000;
    Assertions.assertEquals(response.getSessionState(), SessionState.WON);
    response.cash = 0;
    Assertions.assertEquals(response.getSessionState(), SessionState.LOST);
  }

  @Test
  public void isPlaying() {
    Assertions.assertEquals(response.isPlaying(), false);
    response.state = null;
    Assertions.assertEquals(response.isPlaying(), false);
    response.state = "IN_GAME";
    Assertions.assertEquals(response.isPlaying(), true);
  }

  @Test
  public void getPlayerHandValue() {
    Assertions.assertEquals(response.getPlayerHandValue(), 19);
  }

  @Test
  public void aceAjustement() {
    Assertions.assertEquals(response.aceAjustement(22, 1), 12);
    Assertions.assertEquals(response.aceAjustement(22, 0), 22);
    Assertions.assertEquals(response.aceAjustement(22, 2), 12);
    Assertions.assertEquals(response.aceAjustement(33, 2), 13);
  }
}
