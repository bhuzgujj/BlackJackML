package dev.emileboucher.blackjackml.gamehandlers;

import dev.emileboucher.blackjackml.api.RestClient;
import dev.emileboucher.blackjackml.models.ReportRow;

/**
 * Template for an AI interaction with the api
 */
public abstract class AiHandling {
  ReportRow row = new ReportRow(0, 0);
  RestClient client = new RestClient("http://localhost:3000");

  /**
   * Let the AI play
   * @param amountSessions played before ending
   * @return [ReportRow]
   */
  public abstract ReportRow play(int amountSessions);
}
