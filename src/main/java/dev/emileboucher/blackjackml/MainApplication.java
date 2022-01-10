package dev.emileboucher.blackjackml;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("BlackJack MachineLearning");
        stage.setScene(mainMenu(stage));
        stage.show();
    }

    public Scene mainMenu(Stage stage) {
        VBox container = new VBox();
        container.setPadding(new Insets(50));

        VBox menu = new VBox();
        createMenuButton("Play", stage, menu, "player-view.fxml");
        createMenuButton("Reinforcement Learning", stage, menu, "ai-view.fxml");
        container.getChildren().add(menu);

        Button exit = new Button("Exit");
        exit.setFont(new Font(14));
        exit.setOnMouseClicked(mouseEvent -> {
            Platform.exit();
        });
        container.getChildren().add(exit);

        return new Scene(container);
    }

    private void createMenuButton(String title, Stage stage, VBox menu, String ressourceString) {
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