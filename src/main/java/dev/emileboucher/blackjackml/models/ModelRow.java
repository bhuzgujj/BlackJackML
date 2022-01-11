package dev.emileboucher.blackjackml.models;

/**
 * An object to show a row of the model
 * @param key   of the row
 * @param value of the weight of the state
 */
public record ModelRow(String key, Integer value) {
  /**
   * Get the key of the model row
   * @return the key of the model row
   */
  public String getKey() {
    return key;
  }

  /**
   * Get the value of the model row
   * @return the value of the model row
   */
  public Integer getValue() {
    return value;
  }
}
