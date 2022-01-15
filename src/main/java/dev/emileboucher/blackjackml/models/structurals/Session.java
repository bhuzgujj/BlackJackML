package dev.emileboucher.blackjackml.models.structurals;

import dev.emileboucher.blackjackml.models.responses.BlackJackResponse;
import dev.emileboucher.blackjackml.models.responses.SessionState;

import java.util.LinkedList;
import java.util.List;

public class Session {
  private long  sessionNumber;
  private SessionState sessionResult;
  private int gamesWon;
  private int gamesLost;
  private List<BlackJackResponse> gamesResults = new LinkedList<>();
}
