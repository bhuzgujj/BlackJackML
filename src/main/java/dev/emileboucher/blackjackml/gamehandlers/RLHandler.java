package dev.emileboucher.blackjackml.gamehandlers;

import dev.emileboucher.blackjackml.models.responses.BlackJackResponse;
import dev.emileboucher.blackjackml.models.requests.Deal;
import dev.emileboucher.blackjackml.models.requests.Flag;
import dev.emileboucher.blackjackml.models.ReportRow;
import dev.emileboucher.blackjackml.singletons.RLSingleton;

import java.io.IOException;

/**
 * Ai interaction with the api using the Reinforcement Learning methodology
 */
public class RLHandler extends AiHandling {
  private int nbReport = 0;
  private int money = 1000;

  /**
   * Constructor of the class handling the interaction between the AI and the api
   *    using the Reinforcement Learning methodology
   */
  public RLHandler() {
    row = new ReportRow(0,0);
    row.setTotalGamesPlayed(RLSingleton.getInstance().getGamePlayed());
    row.setSessionNumber(RLSingleton.getInstance().getSessionNumber());
  }

  /**
   * Let the AI play
   * @param amountSessions played before ending
   * @return [ReportRow]
   */
  public ReportRow play(int amountSessions) {
    row.reset();
    Boolean exploration = (nbReport + 1) % 5 == 0;
    do {
      session(exploration);
      row.increaseSessionNumber();
      if (!RLSingleton.getInstance().getPlaying()) break;
      RLSingleton.getInstance().incrementeSessionNumber();
      RLSingleton.getInstance().setGamePlayed(row.getTotalGamesPlayed());
    } while (row.getSessionNumber() % amountSessions != 0);
    nbReport++;
    return row.copy();
  }

  /**
   * Play until the AI has either won or lost
   * @param isExploring other solution possible (choice random)
   */
  protected void session(Boolean isExploring) {
    BlackJackResponse response = new BlackJackResponse();
    int gamePlayed = 0;
    do {
      response = game(isExploring);
      gamePlayed++;
      if (!RLSingleton.getInstance().getPlaying()) break;
    } while (!recordSession(response));
    client.resetCookies();
    row.setTotalGamesPlayed(row.getTotalGamesPlayed() + gamePlayed);
  }

  /**
   * Add the information of a session into the global [ReportRow] object
   * @param response from the api
   * @return if the session is done
   */
  private boolean recordSession(BlackJackResponse response) {
    switch (response.getSessionState()) {
      case WON -> {
        row.setSessionsWon(row.getSessionsWon() + 1);
        try {
          client.send(new Flag());
        } catch (IOException | InterruptedException e) {
          e.getStackTrace();
          System.out.println(e.getMessage());
        }
        return true;
      }
      case LOST -> {
        row.setSessionsLost(row.getSessionsLost() + 1);
        return true;
      }
    }
    return false;
  }

  /**
   * AI plays a game with the API
   * @param isExploring other solution possible (choice random)
   * @return The last [Response] from the api
   */
  protected BlackJackResponse game(Boolean isExploring) {
    BlackJackResponse response = null;
    try {
      response = deal();
      while (response.isPlaying()) {
        response = client.send(DecisionMaker.reinforcementLearning(response, isExploring));
      }
      recordResults(response, isExploring);
    }  catch (IOException | InterruptedException e) {
      System.out.println("game(): " + e.getMessage());
    }
    return response;
  }

  /**
   * Do a deal command to the api
   * @return [Response] from the api
   * @throws IOException from the [HttpClient]
   * @throws InterruptedException from the [HttpClient]
   */
  private BlackJackResponse deal() throws IOException, InterruptedException {
    int bet = Math.min(money, 50);
    BlackJackResponse response = client.send(new Deal(bet));
    while (response == null) {
      response = client.send(new Deal(bet));
    }
    money = response.cash;
    return response;
  }

  /**
   * Add the response to the [ReportRow]
   * @param response from the api
   * @param isExploring other solution possible (choice random)
   */
  private void recordResults(BlackJackResponse response, Boolean isExploring) {
    if (!isExploring) {
      switch (response.getResult()) {
        case WON -> {
          row.setGamesWon(row.getGamesWon() + 1);
        }
        case LOST -> {
          row.setGamesLost(row.getGamesLost() + 1);
        }
      }
    }
    if (response.getPlayerHandValue() < 21) {
      RLSingleton.getInstance().reward(response.toString(), response.getResult().reward());
    }
  }
}
