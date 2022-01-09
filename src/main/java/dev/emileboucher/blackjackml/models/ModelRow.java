package dev.emileboucher.blackjackml.models;

/**
 * An object to show a row of the model
 * @param key   of the row
 * @param value of the weight of the state
 */
public record ModelRow(String key, Integer value) {
    public String getKey() {
        return key;
    }

    public Integer getValue() {
        return value;
    }
}
