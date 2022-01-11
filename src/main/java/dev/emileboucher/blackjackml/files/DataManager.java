package dev.emileboucher.blackjackml.files;

public interface DataManager<Model> {
  void save(Model model);
  Model load();
}
