package dev.emileboucher.blackjackml.api.models;

import java.util.List;

/**
 * The response object structure from the api
 */
public class Response {
    public String state;
    public Integer cash;
    public List<Card> dealerHand;
    public List<Card> playerHand;

    @Override
    public String toString() {
        return "ApiResponse{" +
                "state='" + state + '\'' +
                ", cash=" + cash +
                ", dealerHand=" + dealerHand +
                ", playerHand=" + playerHand +
                '}';
    }
}
