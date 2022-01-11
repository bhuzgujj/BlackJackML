package dev.emileboucher.blackjackml.models;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * An object to show a row of X sessions played
 */
public class ReportRow {
  private long  sessionNumber;
  private int sessionsWon;
  private int  sessionsLost;
  private long totalGamesPlayed;
  private int  gamesWon;
  private int  gamesLost;
  private int precisions = 2;

  /**
   * Full constructor if we need to change any value
   * @param sessionNumber of the row
   * @param sessionsWon of the row
   * @param sessionsLost of the row
   * @param totalGamesPlayed in total
   * @param gamesWon in the row
   * @param gamesLost in the row
   */
  public ReportRow(long sessionNumber, int sessionsWon, int sessionsLost, long totalGamesPlayed, int gamesWon, int gamesLost) {
    this.sessionNumber = sessionNumber;
    this.sessionsWon = sessionsWon;
    this.sessionsLost = sessionsLost;
    this.totalGamesPlayed = totalGamesPlayed;
    this.gamesWon = gamesWon;
    this.gamesLost = gamesLost;
  }

  /**
   * Partial constructor if we need to change any value
   * @param sessionNumber of the row
   * @param totalGamesPlayed in total
   */
  public ReportRow(long sessionNumber, long totalGamesPlayed) {
    this.sessionNumber = sessionNumber;
    this.sessionsWon = 0;
    this.sessionsLost = 0;
    this.totalGamesPlayed = totalGamesPlayed;
    this.gamesWon = 0;
    this.gamesLost = 0;
  }

  /**
   * Increase the session number by 1
   */
  public void increaseSessionNumber() {
    sessionNumber++;
  }

  /**
   * Reset infos that are linked to the row
   */
  public void reset() {
    sessionsWon = 0;
    sessionsLost = 0;
    gamesWon = 0;
    gamesLost = 0;
  }

  /**
   * Get the win rate as a string with 2 decimale precision
   * @return XX.XX % or Exploration
   */
  public String getWinLostRatio() {
    if (gamesWon + gamesLost < 1) return "Exploration";
    BigDecimal fractionaryNumber = BigDecimal.valueOf(
            ((double) gamesWon / (gamesWon + gamesLost)) * 100
    ).setScale(precisions, RoundingMode.HALF_UP);
    return fractionaryNumber + " %";
  }

  /**
   * Get the session number
   * @return session number
   */
  public long getSessionNumber() {
    return sessionNumber;
  }

  /**
   * Get the amount of session won
   * @return session won
   */
  public int getSessionsWon() {
    return sessionsWon;
  }

  /**
   * Get the amount of session lost
   * @return session lost
   */
  public int getSessionsLost() {
    return sessionsLost;
  }

  /**
   * Get the total of games played
   * @return total of games played
   */
  public long getTotalGamesPlayed() {
    return totalGamesPlayed;
  }

  /**
   * Get the total of games played since the last reset
   * @return total of games played
   */
  public int getGamesPlayed() {
    return gamesLost + gamesWon;
  }

  /**
   * Get games won
   * @return games won
   */
  public int getGamesWon() {
    return gamesWon;
  }

  /**
   * Get games lost
   * @return games lost
   */
  public int getGamesLost() {
    return gamesLost;
  }

  /**
   * Set the session number
   * @param sessionNumber at the moment
   */
  public void setSessionNumber(long sessionNumber) {
    this.sessionNumber = sessionNumber;
  }

  /**
   * Set the session won
   * @param sessionsWon at that row
   */
  public void setSessionsWon(int sessionsWon) {
    this.sessionsWon = sessionsWon;
  }

  /**
   * Set the session lost
   * @param sessionsLost at that row
   */
  public void setSessionsLost(int sessionsLost) {
    this.sessionsLost = sessionsLost;
  }

  /**
   * Set the total of games played
   * @param totalGamesPlayed at the moment
   */
  public void setTotalGamesPlayed(long totalGamesPlayed) {
    this.totalGamesPlayed = totalGamesPlayed;
  }

  /**
   * Set the games won
   * @param gamesWon at that row
   */
  public void setGamesWon(int gamesWon) {
    this.gamesWon = gamesWon;
  }

  /**
   * Set the games lost
   * @param gamesLost at that row
   */
  public void setGamesLost(int gamesLost) {
    this.gamesLost = gamesLost;
  }

  /**
   * Get the decimal precision of the win rate
   * @return the number of digit post comas
   */
  public int getPrecisions() {
    return precisions;
  }

  /**
   * Get the decimal precision of the win rate
   * @param number of digit post comas
   */
  public void setPrecisions(int number) {
    this.precisions = number;
  }

  /**
   * Create a full copy of the row
   * @return [ReportRow]
   */
  public ReportRow copy() {
    return new ReportRow(sessionNumber, sessionsWon, sessionsLost, totalGamesPlayed, gamesWon, gamesLost);
  }
}
