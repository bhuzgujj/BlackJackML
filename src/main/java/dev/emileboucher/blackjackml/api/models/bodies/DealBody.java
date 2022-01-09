package dev.emileboucher.blackjackml.api.models.bodies;

/**
 * A json with a bet field
 */
public class DealBody extends Body {
    public final Integer bet;

    /**
     * A json with a bet field
     * @param bet for the game played
     */
    public DealBody(int bet) {
        this.bet = bet;
    }
}
