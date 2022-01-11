package dev.emileboucher.blackjackml.models;

import dev.emileboucher.blackjackml.MainApplication;
import dev.emileboucher.blackjackml.singletons.SceneSingleton;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

/**
 * Class to put the exit button logic in all controllers
 */
public abstract class GlobalButtons {
  /**
   * Create a scene from a ressource string
   * @param ressource string
   * @return the scene
   * @throws IOException if the ressource doesn't exist
   */
  public static Scene createScene(String ressource) throws IOException {
    return new Scene(
            new FXMLLoader(
                    MainApplication.class.getResource(ressource)
            ).load()
    );
  }
  /**
   * Return to the main menu
   */
  public void backToMainMenu() {
    SceneSingleton.getInstance().loadScene(MainApplication.NAME);
  }
}
