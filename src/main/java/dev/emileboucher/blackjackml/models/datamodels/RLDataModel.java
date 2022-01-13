package dev.emileboucher.blackjackml.models.datamodels;

import java.util.HashMap;

/**
 * Model of reinforcement learning
 */
public class RLDataModel {
  public Long sessionNumber = 0L;
  public Long gamePlayed = 0L;
  public HashMap<String, Integer> data = new HashMap<>();

  /**
   * The data of the model
   * @return the model
   */
  public HashMap<String, Integer> getData() {
    return data;
  }

  /**
   * Get the session number
   * @return session number
   */
  public Long getSessionNumber() {
    return sessionNumber;
  }

  /**
   * Get the total of games played since the last reset
   * @return total of games played
   */
  public Long getGamePlayed() {
    return gamePlayed;
  }

  /**
   * Set the amount of games played
   * @param gamePlayed at the moment
   */
  public void setGamePlayed(Long gamePlayed) {
    this.gamePlayed = gamePlayed;
  }

  /**
   * Set the session number
   * @param sessionNumber at the moment
   */
  public void setSessionNumber(Long sessionNumber) {
    this.sessionNumber = sessionNumber;
  }

  /**
   * Set the data of the model
   * @param data of the model
   */
  public void setData(HashMap<String, Integer> data) {
    this.data = data;
  }

}
