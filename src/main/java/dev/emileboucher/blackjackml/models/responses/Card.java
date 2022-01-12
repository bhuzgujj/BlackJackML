package dev.emileboucher.blackjackml.models.responses;

/**
 * Cards object from the [Response]
 */
public class Card implements Comparable<Card> {
  public String rank;
  public String suit;

  /**
   * Get the character representing the card for the singleton
   * @return [String]
   */
  @Override
  public String toString() {
    return switch (rank) {
      case "10", "J", "Q", "K" -> "F";
      default -> rank;
    };
  }

  /**
   * Get the maximum value of a card
   * @return the maximum value of a card
   */
  public int getValue() {
    return switch (rank) {
      case "10", "J", "Q", "K" -> 10;
      case "A" -> 11;
      default -> Integer.parseInt(rank);
    };
  }

  /**
   * Compare this card to another card
   * @param card compared with
   * @return 1,0,-1 depending on how it compares to
   */
  @Override
  public int compareTo(Card card) {
    return this.toString().compareTo(card.toString());
  }
}
