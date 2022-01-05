package dev.emileboucher.blackjackml.controllers;

import dev.emileboucher.blackjackml.singletons.AiSingleton;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.ResourceBundle;

public class AiController implements Initializable {
    private Integer lastVal = 0;
    @FXML
    private TableView<ModelRow> modelData = new TableView<>();
    @FXML
    private TableView<ReportRow> sessionsResults = new TableView<>();

    public AiController() {

    }

    @FXML
    protected void onAddDataClick() {
        sessionsResults.getItems().add(0, new ReportRow(
                lastVal,
                1,
                3,
                lastVal + 1 + lastVal % 7,
                lastVal + 1,
                lastVal / 7
            )
        );
        lastVal++;
    }

    @FXML
    protected void refreshBtn() {
        AiSingleton.getInstance().notification();
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
