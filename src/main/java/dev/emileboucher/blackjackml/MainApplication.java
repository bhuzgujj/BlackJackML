package dev.emileboucher.blackjackml;

import dev.emileboucher.blackjackml.singletons.GlobalSingleton;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
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
        GlobalSingleton.getInstance().addScene(NAME, mainMenu(stage));
        GlobalSingleton.getInstance().setStage(stage);
        GlobalSingleton.getInstance().loadScene(NAME);
        stage.show();
    }

    public Scene mainMenu(Stage stage) {
        VBox container = new VBox();
        container.getChildren().add(createMainBtns(stage));
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

    private VBox createMainBtns(Stage stage) {
        VBox topButtons = new VBox();
        topButtons.setPadding(new Insets(40));
        createMenuButton("Play", stage, topButtons, "player-view.fxml");
        createMenuButton("Reinforcement Learning", stage, topButtons, "ai-view.fxml");
        return topButtons;
    }

    private void createMenuButton(String title, Stage stage, Pane menu, String ressourceString) {
        Button btn = new Button(title);
        btn.setFont(new Font(14));
        btn.setOnMouseClicked(mouseEvent -> {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource(ressourceString));
            try {
                stage.setScene(new Scene(fxmlLoader.load()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        menu.getChildren().add(btn);
    }

    public static void main(String[] args) {
        launch();
    }
}