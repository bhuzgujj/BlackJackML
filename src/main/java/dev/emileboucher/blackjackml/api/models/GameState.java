package dev.emileboucher.blackjackml.api.models;

/**
 * State of the game
 */
public enum GameState {
    WON,
    LOST,
    TIE;

    /**
     * Get the reward of a GameState
     * @return Integer representing the reward
     */
    public Integer reward() {
        return switch (this) {
            case WON -> 1;
            case LOST -> -1;
            default -> 0;
        };
    }
}
