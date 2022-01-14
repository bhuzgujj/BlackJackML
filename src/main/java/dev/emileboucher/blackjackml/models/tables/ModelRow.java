package dev.emileboucher.blackjackml.models.tables;

import java.util.HashMap;

/**
 * An object to show a row of the model
 * @param key   of the row
 * @param value of the weight of the state
 */
public record ModelRow(String key, Integer value) {
  /**
   * Get the key of the model row
   * @return the key of the model row
   */
  public String getKey() {
    return key;
  }

  /**
   * Get the value of the model row
   * @return the value of the model row
   */
  public Integer getValue() {
    return value;
  }

  /**
   * Get the name of the variables and the name of the columns
   * @return the name of the variables and the name of the columns
   */
  public static String[][] getFieldInfos() {
    return new String[][] {
            {"Board states", "key"},
            {"Weight", "value"},
    };
  }

  public static ModelRow[] toArray(HashMap<String, Integer> map) {
    ModelRow[] array = new ModelRow[map.size()];
    int i = 0;
    for (var item : map.entrySet()) {
      array[i] = new ModelRow(item.getKey(), item.getValue());
      i++;
    }
    return array;
  }
}
