package dev.emileboucher.blackjackml.singletons;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;

/**
 * Singleton to handle the scene swapping
 */
public class SceneSingleton {
  private static SceneSingleton instance = null;
  private final HashMap<String, Scene> scenes = new HashMap<>();
  private Stage stage = null;

  //=======================================================================
  //  Singleton
  //-----------------------------------------------------------------------
  /**
   * Private constructor for the singleton
   */
  private SceneSingleton() { }

  /**
   * Get the instance
   * @return instance of [AiSingleton]
   */
  public static SceneSingleton getInstance() {
    if (instance == null) {
      synchronized (SceneSingleton.class) {
        instance = new SceneSingleton();
      }
    }
    return instance;
  }

  //=======================================================================
  //  Scene
  //-----------------------------------------------------------------------
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
