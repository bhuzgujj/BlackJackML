package dev.emileboucher.blackjackml.models.datamodels;

import dev.emileboucher.blackjackml.models.ReportRow;

import java.math.BigDecimal;

public class ReportDataModel {
  public Long sessionsDone;
  public Integer sessionsWon;
  public Integer  sessionsLost;
  public Long totalGamesPlayed;
  public Integer gamesPlayed;
  public Integer gamesWon;
  public Integer gamesLost;
  public BigDecimal winrate;

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

  public Long getSessionsDone() {
    return sessionsDone;
  }

  public Integer getSessionsWon() {
    return sessionsWon;
  }

  public Integer getSessionsLost() {
    return sessionsLost;
  }

  public Long getTotalGamesPlayed() {
    return totalGamesPlayed;
  }

  public Integer getGamesPlayed() {
    return gamesPlayed;
  }

  public Integer getGamesWon() {
    return gamesWon;
  }

  public Integer getGamesLost() {
    return gamesLost;
  }

  public BigDecimal getWinrate() {
    return winrate;
  }
}
