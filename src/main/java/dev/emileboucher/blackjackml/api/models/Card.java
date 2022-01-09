package dev.emileboucher.blackjackml.api.models;

/**
 * Cards object from the [Response]
 */
public class Card {
    public String rank;

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
