package dev.emileboucher.blackjackml.gamehandlers;

import dev.emileboucher.blackjackml.models.BlackJackClient;
import dev.emileboucher.blackjackml.models.tables.ReportRow;

/**
 * Template for an AI interaction with the api
 */
public abstract class AiHandling {
  ReportRow row = new ReportRow(0, 0);
  BlackJackClient client = new BlackJackClient("http://localhost:3000");

  /**
   * Let the AI play
   * @param amountSessions played before ending
   * @return [ReportRow]
   */
  public abstract ReportRow play(int amountSessions);
}
