package dev.emileboucher.blackjackml.controllers;

import com.google.gson.Gson;
import dev.emileboucher.blackjackml.api.models.bodies.*;
import dev.emileboucher.blackjackml.intelligence.ArtificialIntelligence;
import dev.emileboucher.blackjackml.singletons.AiSingleton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class AiController implements Initializable {
    @FXML
    private Button start = new Button();
    @FXML
    private Button refresher = new Button();
    @FXML
    private TableView<ModelRow> modelData = new TableView<>();
    @FXML
    private TableView<ReportRow> sessionsResults = new TableView<>();

    public AiController() {

    }

    @FXML
    protected void startBtn() {
        System.out.println("BTN PRESSED");
        if (AiSingleton.getInstance().getPlaying()) {
            AiSingleton.getInstance().setPlaying(false);
            start.setDisable(true);
        } else {
            AiSingleton.getInstance().setPlaying(true);
            Thread game = new Thread(this::useAi);
            try {
                game.join();
                game.start();
                start.setText("Stop");
            } catch (InterruptedException e) {
                AiSingleton.getInstance().setPlaying(false);
            }
        }
    }

    private void useAi() {
        ArtificialIntelligence ai = new ArtificialIntelligence();
        try {
            ai.play(this::reportHandling);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            AiSingleton.getInstance().setPlaying(false);
        } finally {
            start.setDisable(false);
            start.setText("Start");
        }
    }

    @FXML
    protected void refreshBtn() {
        try {
            System.out.println(new Gson().toJson(new EmptyBody()));
            // updateUI();
         } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void reportHandling(ReportRow reportRow) {

    }

    @Override
    public void initialize(URL p_url, ResourceBundle p_resourceBundle) {
        AiSingleton.getInstance().clearNotificationList();
        initializeGamesDonesTable();
        initializeModelDataTable();
        updateUI();
        AiSingleton.getInstance().setListener(this::updateUI);
    }

    private void updateUI() {
        updateModelData();
        updateSessionResults();
    }

    private void updateSessionResults() {
        sessionsResults.getItems().clear();
    }

    private void updateModelData() {
        modelData.getItems().clear();
        for (var row : AiSingleton.getInstance().getModel().entrySet()) {
            modelData.getItems().add(new ModelRow(row.getKey(), row.getValue()));
        }
    }

    private void initializeModelDataTable() {
        modelData.setEditable(true);
        modelData.getColumns().add(createColumnsFromClass("Board states", "key", modelData.getPrefWidth()/2));
        modelData.getColumns().add(createColumnsFromClass("Weight", "value", modelData.getPrefWidth()/2));
    }

    private void initializeGamesDonesTable() {
        sessionsResults.setEditable(true);
        double width = sessionsResults.getPrefWidth() / 7;
        sessionsResults.getColumns().add(createColumnsFromClass("Session number", "sessionNumber", width));
        sessionsResults.getColumns().add(createColumnsFromClass("Session won", "sessionsWon", width));
        sessionsResults.getColumns().add(createColumnsFromClass("Session lost", "sessionsLost", width));
        sessionsResults.getColumns().add(createColumnsFromClass("Games played", "gamesPlayed", width));
        sessionsResults.getColumns().add(createColumnsFromClass("Games won", "gamesWon", width));
        sessionsResults.getColumns().add(createColumnsFromClass("Games lost", "gamesLost", width));
        sessionsResults.getColumns().add(createColumnsFromClass("Win/Loss ratio", "winLostRatio", width));
    }

    private <Model, Type> TableColumn<Model, Type> createColumnsFromClass(String title, String name, double width) {
        TableColumn<Model, Type> col = new TableColumn<>(title);
        col.setCellValueFactory(new PropertyValueFactory<>(name));
        col.setPrefWidth(width);
        return col;
    }
}
