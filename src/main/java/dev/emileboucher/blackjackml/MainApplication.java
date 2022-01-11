package dev.emileboucher.blackjackml;

import dev.emileboucher.blackjackml.controllers.AiController;
import dev.emileboucher.blackjackml.controllers.PlayerController;
import dev.emileboucher.blackjackml.singletons.GlobalSingleton;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
  public static final String NAME = "MainMenu";
  @Override
  public void start(Stage stage) throws IOException {
    stage.setTitle("BlackJack MachineLearning");
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
    VBox container = new VBox();
    container.setPadding(new Insets(40));

    Button exit = new Button("Exit");
    exit.setFont(new Font(14));
    exit.setOnMouseClicked(mouseEvent -> {
      Platform.exit();
    });

    container.getChildren().add(exit);
    VBox.setVgrow(container, Priority.ALWAYS);
    VBox.setVgrow(exit, Priority.ALWAYS);
    return container;
  }

  private VBox createMainBtns() {
    VBox topButtons = new VBox();
    topButtons.setPadding(new Insets(40));
    createMenuButton("Play", topButtons, PlayerController.NAME);
    createMenuButton("Reinforcement Learning", topButtons, AiController.NAME);
    return topButtons;
  }

  private void createMenuButton(String title, Pane menu, String sceneName) {
    Button btn = new Button(title);
    btn.setFont(new Font(14));
    btn.setOnMouseClicked(mouseEvent -> {
      GlobalSingleton.getInstance().loadScene(sceneName);
    });
    menu.getChildren().add(btn);
  }

  public static void main(String[] args) {
    launch();
  }
}