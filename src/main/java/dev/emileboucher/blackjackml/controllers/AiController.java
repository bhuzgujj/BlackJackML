package dev.emileboucher.blackjackml.controllers;

import dev.emileboucher.blackjackml.singleton.AiSingleton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class AiController implements Initializable {
    private Integer lastVal = 0;
    public AiController() {

    }

    @FXML
    private TableView<ModelRow> modelData = new TableView<>();
    @FXML
    private TableView<ReportRow> gamesDones = new TableView<>();

    @FXML
    protected void onAddDataClick() {
        AiSingleton.getInstance().addData("lastVal", lastVal);
        gamesDones.getItems().add(0, new ReportRow(
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

    @Override
    public void initialize(URL p_url, ResourceBundle p_resourceBundle) {
        initializeGamesDonesTable();
        initializeModelDataTable();
        updateUI();
        AiSingleton.getInstance().setListener(this::updateUI);
    }

    private void updateUI() {
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
        gamesDones.setEditable(true);
        double width = gamesDones.getPrefWidth() / 7;
        gamesDones.getColumns().add(createColumnsFromClass("Session number", "sessionNumber", width));
        gamesDones.getColumns().add(createColumnsFromClass("Session won", "sessionsWon", width));
        gamesDones.getColumns().add(createColumnsFromClass("Session lost", "sessionsLost", width));
        gamesDones.getColumns().add(createColumnsFromClass("Games played", "gamesPlayed", width));
        gamesDones.getColumns().add(createColumnsFromClass("Games won", "gamesWon", width));
        gamesDones.getColumns().add(createColumnsFromClass("Games lost", "gamesLost", width));
        gamesDones.getColumns().add(createColumnsFromClass("Win/Loss ratio", "winLostRatio", width));
    }

    private <Model, Type> TableColumn<Model, Type> createColumnsFromClass(String title, String name, double width) {
        TableColumn<Model, Type> col = new TableColumn<>(title);
        col.setCellValueFactory(new PropertyValueFactory<>(name));
        col.setPrefWidth(width);
        return col;
    }
}
