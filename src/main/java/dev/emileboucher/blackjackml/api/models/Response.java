package dev.emileboucher.blackjackml.api.models;

import java.util.List;
import java.util.Optional;

/**
 * The response object structure from the api
 */
public class Response {
    public String state;
    public Integer cash;
    public List<Card> dealerHand;
    public List<Card> playerHand;

    /**
     * Get the string representing the state of the board
     * @return [String] of the state of the board
     */
    @Override
    public String toString() {
        if (dealerHand == null || dealerHand.size() < 1) return "EMPTY";
        StringBuilder val = new StringBuilder(dealerHand.get(0).toString() + "-");
        for (var card : playerHand) {
            val.append(card.toString());
        }
        return val.toString();
    }

    /**
     * Get the state of the game
     * @return [GameState]
     */
    public GameState getResult() {
        return Optional.ofNullable(state)
                .map((gstate) -> switch (gstate) {
                    case "WON" -> GameState.WON;
                    case "LOST" -> GameState.LOST;
                    default -> GameState.TIE;
                })
                .orElse(GameState.TIE);
    }

    /**
     * Get the state of the session
     * @return [SessionState]
     */
    public SessionState getSessionState() {
        return Optional.ofNullable(cash)
                .map((money) -> {
                    if (cash >= 2000) return SessionState.WON;
                    if (cash <= 0) return SessionState.LOST;
                    else return SessionState.PLAYING;
                })
                .orElse(SessionState.ERROR);
    }

    /**
     * Get if you can still do action in the game
     * @return [Boolean]
     */
    public Boolean isPlaying() {
        return Optional.ofNullable(state)
                .map((gstate) -> gstate.equals("IN_GAME"))
                .orElse(false);
    }
}
