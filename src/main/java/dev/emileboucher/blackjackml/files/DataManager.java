package dev.emileboucher.blackjackml.files;

/**
 * Object to store and load data from permanent storage
 * @param <Model> of the data stored
 */
public interface DataManager<Model> {
  /**
   * Save the model in the permanent storage
   * @param model of the data
   */
  void save(Model model);

  /**
   * Load the model from permanent storage
   * @return the [Model]
   */
  Model load();
}
