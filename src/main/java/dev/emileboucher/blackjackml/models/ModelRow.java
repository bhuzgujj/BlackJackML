package dev.emileboucher.blackjackml.models;

public class ModelRow {
    private final String key;
    private final Integer value;

    public ModelRow(String p_key, Integer p_value) {
        key = p_key;
        value = p_value;
    }

    public String getKey() {
        return key;
    }

    public Integer getValue() {
        return value;
    }
}
