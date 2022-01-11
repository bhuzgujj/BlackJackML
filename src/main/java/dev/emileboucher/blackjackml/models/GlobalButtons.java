package dev.emileboucher.blackjackml.models;

import dev.emileboucher.blackjackml.MainApplication;
import dev.emileboucher.blackjackml.singletons.GlobalSingleton;

public abstract class GlobalButtons {
  /**
   * Return to the main menu
   */
  public void backToMainMenu() {
    GlobalSingleton.getInstance().loadScene(MainApplication.NAME);
  }
}
