package dev.emileboucher.blackjackml;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Priority;
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

        VBox topButtons = new VBox();
        topButtons.setPadding(new Insets(40));
        createMenuButton("Play", stage, topButtons, "player-view.fxml");
        createMenuButton("Reinforcement Learning", stage, topButtons, "ai-view.fxml");
        container.getChildren().add(topButtons);


        VBox bottomBtns = new VBox();
        bottomBtns.setPadding(new Insets(40));

        Button exit = new Button("Exit");
        exit.setFont(new Font(14));
        exit.setOnMouseClicked(mouseEvent -> {
            Platform.exit();
        });
        bottomBtns.getChildren().add(exit);

        container.getChildren().add(bottomBtns);

        VBox.setVgrow(bottomBtns, Priority.ALWAYS);
        VBox.setVgrow(exit, Priority.ALWAYS);
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