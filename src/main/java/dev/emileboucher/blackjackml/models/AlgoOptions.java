package dev.emileboucher.blackjackml.models;

/**
 * The type of option for the Algo menu
 */
public enum AlgoOptions {
  EQUAL("=="),
  NOT_EQUAL("!="),
  GREATER(">"),
  GREATER_EQUAL(">="),
  LESS("<"),
  LESS_EQUAL("<=");

  public final String text;
  AlgoOptions(String text) {
    this.text = text;
  }
}
