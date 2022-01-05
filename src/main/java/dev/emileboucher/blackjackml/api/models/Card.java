package dev.emileboucher.blackjackml.api.models;

/**
 * Cards object from the [Response]
 */
public class Card {
    public String rank;
    public String suit;

    @Override
    public String toString() {
        return "ApiCard{" +
                "rank='" + rank + '\'' +
                ", suit='" + suit + '\'' +
                '}';
    }
}
