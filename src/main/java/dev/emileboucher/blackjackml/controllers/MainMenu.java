package dev.emileboucher.blackjackml.controllers;

import dev.emileboucher.blackjackml.controllers.ai.NeuroController;
import dev.emileboucher.blackjackml.controllers.ai.RLController;
import dev.emileboucher.blackjackml.controllers.player.AlgoController;
import dev.emileboucher.blackjackml.controllers.player.PlayerController;
import dev.emileboucher.blackjackml.singletons.SceneSingleton;
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

/**
 * Creation of the main menu
 */
public class MainMenu {
  private static final int FONT_SIZE = 14;
  private static final int GENERAL_MARGIN = 10;
  private static final VBox ERRORS = new VBox();

  /**
   * Create the scene of the main menu
   * @return the scene of the main menu
   */
  public static Scene createScene() {
    VBox container = new VBox();
    container.getChildren().add(createErrorContainer());
    container.getChildren().add(createMainBtns());
    container.getChildren().add(createOptionBtns());
    return new Scene(container);
  }

  /**
   * fill the box with the errors
   * @return the box with the errors
   */
  private static VBox createErrorContainer() {
    ERRORS.setAlignment(Pos.BOTTOM_CENTER);
    VBox.setVgrow(ERRORS, Priority.ALWAYS);
    return ERRORS;
  }

  /**
   * Create the general options buttons
   * @return the general options buttons
   */
  private static VBox createOptionBtns() {
    Button exit = new Button("Exit");
    exit.setMaxWidth(Double.POSITIVE_INFINITY);
    exit.setFont(new Font(FONT_SIZE));
    exit.setOnMouseClicked(mouseEvent -> Platform.exit());

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
  private static VBox createMainBtns() {
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
  private static void createLabel(String title, Pane menu) {
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
  private static void createMenuButton(String title, Pane menu, String name, Boolean disable) {
    Button btn = new Button(title);
    btn.setMaxWidth(Double.POSITIVE_INFINITY);
    btn.setFont(new Font(FONT_SIZE));
    btn.setOnMouseClicked(mouseEvent -> {
      if (ERRORS.getChildren().size() > 0) {
        ERRORS.getChildren().clear();
      }
      if (!SceneSingleton.getInstance().discardLoadScene(MainMenu.class.getName(), name)) {
        ERRORS.getChildren().add(new Label("The option \"" + title + "\" failed to load..."));
      }
    });
    btn.setDisable(disable);

    HBox container = new HBox(btn);
    HBox.setHgrow(container, Priority.ALWAYS);
    HBox.setHgrow(btn, Priority.ALWAYS);

    menu.getChildren().add(container);
  }
}
