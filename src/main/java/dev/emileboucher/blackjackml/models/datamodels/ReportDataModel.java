package dev.emileboucher.blackjackml.models.datamodels;

import dev.emileboucher.blackjackml.models.tables.ReportRow;

import java.math.BigDecimal;

/**
 * The model to save in a .csv
 */
public class ReportDataModel {
  public Long sessionsDone;
  public Integer sessionsWon;
  public Integer  sessionsLost;
  public Long totalGamesPlayed;
  public Integer gamesPlayed;
  public Integer gamesWon;
  public Integer gamesLost;
  public BigDecimal winrate;

  /**
   * Creation of the data modell from a ReportRow
   * @param report to save
   */
  public ReportDataModel(ReportRow report) {
    this.sessionsDone = report.getSessionNumber();
    this.sessionsWon = report.getSessionsWon();
    this.sessionsLost = report.getSessionsLost();
    this.totalGamesPlayed = report.getTotalGamesPlayed();
    this.gamesPlayed = report.getGamesPlayed();
    this.gamesWon = report.getGamesWon();
    this.gamesLost = report.getGamesLost();
    this.winrate = report.getWinrate();
  }

  /**
   * Title row
   * @return the title row
   */
  public static String getTitle() {
    return "Sessions Done," +
            "Sessions Won," +
            "Sessions Lost," +
            "Total Games Played," +
            "Games Played," +
            "Games Won," +
            "Games Lost," +
            "Winrate (%)\n";
  }

  /**
   * The data in a csv line format
   * @return the data in a csv line format
   */
  @Override
  public String toString() {
    return sessionsDone + "," +
            sessionsWon + "," +
            sessionsLost + "," +
            totalGamesPlayed + "," +
            gamesPlayed + "," +
            gamesWon + "," +
            gamesLost + "," +
            winrate + "\n";
  }
}
