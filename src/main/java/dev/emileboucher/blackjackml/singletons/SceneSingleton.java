package dev.emileboucher.blackjackml.singletons;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.concurrent.Callable;

/**
 * Singleton to handle the scene swapping
 */
public class SceneSingleton {
  private static SceneSingleton instance = null;
  private final HashMap<String, Callable<Scene>> scenes = new HashMap<>();
  private final HashMap<String, Scene> savedScene = new HashMap<>();
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
   * Add a scene to the referential map
   * @param name of the scene
   * @param scene to the name
   */
  public void addScene(String name, Callable<Scene> scene) {
    scenes.put(name, scene);
  }

  /**
   * Set the scene to show
   * @return if the function has been executed with success
   */
  public Boolean discardLoadScene(String previousScene, String nextScene) {
    try {
      savedScene.remove(previousScene);
      stage.setScene(loadCached(nextScene));
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * Store the stage to be used globally
   * @param stage stored
   */
  public void setStage(Stage stage) {
    this.stage = stage;
  }

  public Boolean saveLoadScene(String previousScene, String nextScene) {
    try {
      savedScene.put(previousScene, stage.getScene());
      stage.setScene(loadCached(nextScene));
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  private Scene loadCached(String name) throws Exception {
    Scene scene = savedScene.get(name);
    if (scene == null) {
      scene = scenes.get(name).call();
    }
    return scene;
  }
}
