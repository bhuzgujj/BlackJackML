package dev.emileboucher.blackjackml;

import dev.emileboucher.blackjackml.controllers.MainMenu;
import dev.emileboucher.blackjackml.controllers.ai.NeuroController;
import dev.emileboucher.blackjackml.controllers.ai.RLController;
import dev.emileboucher.blackjackml.controllers.player.AlgoController;
import dev.emileboucher.blackjackml.controllers.player.PlayerController;
import dev.emileboucher.blackjackml.gamehandlers.singletons.SceneSingleton;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The application to play or to use AI to play blackjack
 * @author Émile Boucher
 */
public class MainApplication extends Application {
  public static final String TITLE = "BlackJack";

  /**
   * The function starting the app
   * @param stage of the main scene
   * @throws IOException when files are missing
   */
  @Override
  public void start(Stage stage) throws IOException {
    stage.setTitle(TITLE);
    initializeScenes(stage);
    SceneSingleton.getInstance().discardLoadScene(
            null,
            MainMenu.class.getName()
    );
    stage.show();
  }

  /**
   * Add all scene to the Global singleton
   * @param stage of the main scene
   */
  private void initializeScenes(Stage stage) {
    SceneSingleton.getInstance().addScene(PlayerController.class.getName(), PlayerController::getScene);
    SceneSingleton.getInstance().addScene(RLController.class.getName(), RLController::getScene);
    SceneSingleton.getInstance().addScene(NeuroController.class.getName(), NeuroController::getScene);
    SceneSingleton.getInstance().addScene(AlgoController.class.getName(), AlgoController::getScene);
    SceneSingleton.getInstance().addScene(MainMenu.class.getName(), MainMenu::createScene);
    SceneSingleton.getInstance().setStage(stage);
  }

  /**
   * The function called when the app is started
   * @param args in commandline
   */
  public static void main(String[] args) {
    launch();
  }
}