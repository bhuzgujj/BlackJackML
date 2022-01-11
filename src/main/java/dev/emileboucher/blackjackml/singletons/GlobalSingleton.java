package dev.emileboucher.blackjackml.singletons;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;

public class GlobalSingleton {
  private static GlobalSingleton instance = null;
  private Stage stage = null;
  private final HashMap<String, Scene> scenes = new HashMap<>();

  private GlobalSingleton() {

  }

  /**
   * Get the instance
   * @return instance of [AiSingleton]
   */
  public static GlobalSingleton getInstance() {
    if (instance == null) {
      synchronized (GlobalSingleton.class) {
        instance = new GlobalSingleton();
      }
    }
    return instance;
  }

  /**
   * Add a scene to the
   * @param name of the scene
   * @param scene to the name
   */
  public void addScene(String name, Scene scene) {
    scenes.put(name, scene);
  }

  /**
   * Set the scene to show
   * @param name of the scene
   */
  public void loadScene(String name) {
    stage.setScene(scenes.get(name));
  }

  /**
   * Store the stage to be used globally
   * @param stage stored
   */
  public void setStage(Stage stage) {
    this.stage = stage;
  }
}
