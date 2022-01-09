package dev.emileboucher.blackjackml.models.datamodel;

import java.util.HashMap;

public class ReinforcementLearning {
  public HashMap<String, Integer> data = new HashMap<>();

  public HashMap<String, Integer> getData() {
    return data;
  }

  public void setData(HashMap<String, Integer> data) {
    this.data = data;
  }
}
