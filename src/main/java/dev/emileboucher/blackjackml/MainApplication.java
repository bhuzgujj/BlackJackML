package dev.emileboucher.blackjackml;

import dev.emileboucher.blackjackml.controllers.NeuroController;
import dev.emileboucher.blackjackml.controllers.RLController;
import dev.emileboucher.blackjackml.controllers.PlayerController;
import dev.emileboucher.blackjackml.singletons.SceneSingleton;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
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
  public static final String NAME = "MainMenu";
  public static final String TITLE = "BlackJack";
  public static final int GENERAL_MARGIN = 20;
  public static final int FONT_SIZE = 14;

  /**
   * The function starting the app
   * @param stage of the main scene
   * @throws IOException when files are missing
   */
  @Override
  public void start(Stage stage) throws IOException {
    stage.setTitle(TITLE);
    initializeScenes(stage);
    SceneSingleton.getInstance().loadScene(NAME);
    stage.show();
  }

  /**
   * Add all scene to the Global singleton
   * @param stage of the main scene
   * @throws IOException when files are missing
   */
  private void initializeScenes(Stage stage) throws IOException {
    SceneSingleton.getInstance().addScene(PlayerController.NAME, PlayerController.getScene());
    SceneSingleton.getInstance().addScene(RLController.NAME, RLController.getScene());
    SceneSingleton.getInstance().addScene(NeuroController.NAME, NeuroController.getScene());
    SceneSingleton.getInstance().addScene(NAME, mainMenu());
    SceneSingleton.getInstance().setStage(stage);
  }

  /**
   * Create the scene of the main menu
   * @return the scene of the main menu
   */
  public Scene mainMenu() {
    VBox container = new VBox();
    container.getChildren().add(createMainBtns());
    container.getChildren().add(createOptionBtns());
    return new Scene(container);
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
    createMenuButton("Play", topButtons, PlayerController.NAME, false);
    createLabel("Artificial intelligences options:", topButtons);
    createMenuButton("Reinforcement Learning", topButtons, RLController.NAME, false);
    createMenuButton("Neural Network", topButtons, NeuroController.NAME, true);
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
      SceneSingleton.getInstance().loadScene(name);
    });
    btn.setDisable(disable);

    HBox container = new HBox();
    container.getChildren().add(btn);
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