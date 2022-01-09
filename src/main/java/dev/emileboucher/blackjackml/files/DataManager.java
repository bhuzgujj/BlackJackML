package dev.emileboucher.blackjackml.files;

import dev.emileboucher.blackjackml.models.datamodel.ReinforcementLearning;

public interface DataManager {
  public ReinforcementLearning load();
  public void save(ReinforcementLearning model);
}
