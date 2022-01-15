package dev.emileboucher.blackjackml;

import dev.emileboucher.blackjackml.controllers.ai.NeuroController;
import dev.emileboucher.blackjackml.controllers.ai.RLController;
import dev.emileboucher.blackjackml.controllers.player.AlgoController;
import dev.emileboucher.blackjackml.controllers.player.PlayerController;
import dev.emileboucher.blackjackml.gamehandlers.singletons.SceneSingleton;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The application to play or to use AI to play blackjack
 * @author Émile Boucher
 */
public class MainApplication extends Application {
  public static final String TITLE = "BlackJack";
  public static final int GENERAL_MARGIN = 20;
  public static final int FONT_SIZE = 14;
  public VBox errors = new VBox();

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
            this.getClass().getName()
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
    SceneSingleton.getInstance().addScene(this.getClass().getName(), this::mainMenu);
    SceneSingleton.getInstance().setStage(stage);
  }

  /**
   * Create the scene of the main menu
   * @return the scene of the main menu
   */
  public Scene mainMenu() {
    VBox container = new VBox();
    container.getChildren().add(createErrorContainer());
    container.getChildren().add(createMainBtns());
    container.getChildren().add(createOptionBtns());
    return new Scene(container);
  }

  private VBox createErrorContainer() {
    errors.setAlignment(Pos.BOTTOM_CENTER);
    VBox.setVgrow(errors, Priority.ALWAYS);
    return errors;
  }

  /**
   * Create the general options buttons
   * @return the general options buttons
   */
  private VBox createOptionBtns() {
    Button exit = new Button("Exit");
    exit.setMaxWidth(Double.POSITIVE_INFINITY);
    exit.setFont(new Font(FONT_SIZE));
    exit.setOnMouseClicked(mouseEvent -> {
      Platform.exit();
    });

    VBox container = new VBox();
    container.setPadding(new Insets(GENERAL_MARGIN));
    container.getChildren().add(exit);
    VBox.setVgrow(container, Priority.ALWAYS);
    VBox.setVgrow(exit, Priority.ALWAYS);
    return container;
  }

  /**
   * Create the buttons that access the other scenes
   * @return the buttons that access the other scenes
   */
  private VBox createMainBtns() {
    VBox topButtons = new VBox();
    topButtons.setPadding(new Insets(GENERAL_MARGIN));
    createLabel("Player's interactions options:", topButtons);
    createMenuButton("Play", topButtons, PlayerController.class.getName(), false);
    createMenuButton("Algorithm builder", topButtons, AlgoController.class.getName(), false);
    createLabel("Artificial intelligences options:", topButtons);
    createMenuButton("Reinforcement Learning", topButtons, RLController.class.getName(), false);
    createMenuButton("Neural Network", topButtons, NeuroController.class.getName(), true);
    return topButtons;
  }

  /**
   * Create a label
   * @param title of the label
   * @param menu to add the label to
   */
  private void createLabel(String title, Pane menu) {
    Label btn = new Label(title);
    btn.setFont(new Font(FONT_SIZE));

    menu.getChildren().add(btn);
  }

  /**
   * Create a button to change scene
   * @param title of the button
   * @param menu to add the label to
   * @param name of the scene
   * @param disable the button
   */
  private void createMenuButton(String title, Pane menu, String name, Boolean disable) {
    Button btn = new Button(title);
    btn.setMaxWidth(Double.POSITIVE_INFINITY);
    btn.setFont(new Font(FONT_SIZE));
    btn.setOnMouseClicked(mouseEvent -> {
      if (errors.getChildren().size() > 0) {
        errors.getChildren().clear();
      }
      if (!SceneSingleton.getInstance().discardLoadScene(this.getClass().getName(), name)) {
        errors.getChildren().add(new Label("The option \"" + title + "\" failed to load..."));
      }
    });
    btn.setDisable(disable);

    HBox container = new HBox(btn);
    HBox.setHgrow(container, Priority.ALWAYS);
    HBox.setHgrow(btn, Priority.ALWAYS);

    menu.getChildren().add(container);
  }

  /**
   * The function called when the app is started
   * @param args in commandline
   */
  public static void main(String[] args) {
    launch();
  }
}