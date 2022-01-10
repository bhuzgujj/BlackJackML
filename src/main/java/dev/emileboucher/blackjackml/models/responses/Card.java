package dev.emileboucher.blackjackml.models.responses;

/**
 * Cards object from the [Response]
 */
public class Card {
    public String rank;
    public String suit;

    /**
     * Get the character representing the card for the singleton
     * @return [String]
     */
    @Override
    public String toString() {
        return switch (rank) {
            case "J", "Q", "K" -> "F";
            default -> rank;
        };
    }
}
