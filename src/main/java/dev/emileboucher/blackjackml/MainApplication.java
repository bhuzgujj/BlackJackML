package dev.emileboucher.blackjackml;

import dev.emileboucher.blackjackml.controllers.AiController;
import dev.emileboucher.blackjackml.controllers.PlayerController;
import dev.emileboucher.blackjackml.singletons.GlobalSingleton;
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

public class MainApplication extends Application {
  public static final String NAME = "MainMenu";
  public static final String TITLE = "BlackJack";
  public static final int GENERAL_MARGIN = 20;
  public static final int FONT_SIZE = 14;

  @Override
  public void start(Stage stage) throws IOException {
    stage.setTitle(TITLE);
    initializeScenes(stage);
    GlobalSingleton.getInstance().loadScene(NAME);
    stage.show();
  }

  private void initializeScenes(Stage stage) throws IOException {
    GlobalSingleton.getInstance().addScene(PlayerController.NAME, PlayerController.getScene());
    GlobalSingleton.getInstance().addScene(AiController.NAME, AiController.getScene());
    GlobalSingleton.getInstance().addScene(NAME, mainMenu());
    GlobalSingleton.getInstance().setStage(stage);
  }

  public Scene mainMenu() {
    VBox container = new VBox();
    container.getChildren().add(createMainBtns());
    container.getChildren().add(createOptionBtns());
    return new Scene(container);
  }

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

  private VBox createMainBtns() {
    VBox topButtons = new VBox();
    topButtons.setPadding(new Insets(GENERAL_MARGIN));
    createLabel("Player's interactions options:", topButtons);
    createMenuButton("Play", topButtons, PlayerController.NAME);
    createLabel("Artificial intelligences options:", topButtons);
    createMenuButton("Reinforcement Learning", topButtons, AiController.NAME);
    return topButtons;
  }

  private void createLabel(String title, Pane menu) {
    Label btn = new Label(title);
    btn.setFont(new Font(FONT_SIZE));

    menu.getChildren().add(btn);
  }

  private void createMenuButton(String title, Pane menu, String sceneName) {
    Button btn = new Button(title);
    btn.setMaxWidth(Double.POSITIVE_INFINITY);
    btn.setFont(new Font(FONT_SIZE));
    btn.setOnMouseClicked(mouseEvent -> {
      GlobalSingleton.getInstance().loadScene(sceneName);
    });

    HBox container = new HBox();
    container.getChildren().add(btn);
    HBox.setHgrow(container, Priority.ALWAYS);
    HBox.setHgrow(btn, Priority.ALWAYS);

    menu.getChildren().add(container);
  }

  public static void main(String[] args) {
    launch();
  }
}