package dev.emileboucher.blackjackml.controllers;

import dev.emileboucher.blackjackml.intelligence.AiInteractions;
import dev.emileboucher.blackjackml.models.ModelRow;
import dev.emileboucher.blackjackml.models.ReportRow;
import dev.emileboucher.blackjackml.singletons.AiSingleton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the AI training/play
 */
public class AiController implements Initializable {
    private final AiInteractions ai = new AiInteractions();
    @FXML
    private Button start = new Button();
    @FXML
    private Button refresher = new Button();
    @FXML
    private TableView<ModelRow> modelData = new TableView<>();
    @FXML
    private TableView<ReportRow> sessionsResults = new TableView<>();

    /**
     * Button to start and stop the game
     */
    @FXML
    protected void startBtn() {
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

    /**
     * Use an AI to play and start playing
     */
    private void useAi() {
        try {
            while (AiSingleton.getInstance().getPlaying()) {
                AiSingleton.getInstance().getReports().add(ai.play(10));
                updateUI();
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            AiSingleton.getInstance().setPlaying(false);
        } finally {
            start.setDisable(false);
            start.setText("Start");
        }
    }

    /**
     * Button to refresh the UI
     */
    @FXML
    protected void refreshBtn() {
        modelData.getItems().add(new ModelRow("mdr", 2));
        updateUI();
    }

    /**
     * Initialize the controller
     * @param url of the controller
     * @param resourceBundle of the controller
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeSessionResultsTable();
        initializeModelDataTable();
        updateUI();
    }

    /**
     * Update the entire UI
     */
    private void updateUI() {
        updateModelData();
        updateSessionResults();
    }

    /**
     * Update the data in session results table
     */
    private void updateSessionResults() {
        sessionsResults.getItems().clear();
        for (var row : AiSingleton.getInstance().getReports()) {
            sessionsResults.getItems().add(0, row);
        }
    }

    /**
     * Update the data in model data table
     */
    private void updateModelData() {
        modelData.getItems().clear();
        for (var row : AiSingleton.getInstance().getModel().entrySet()) {
            modelData.getItems().add(new ModelRow(row.getKey(), row.getValue()));
        }
    }

    /**
     * Initialize the model data table
     */
    private void initializeModelDataTable() {
        modelData.setEditable(true);
        modelData.getColumns().add(createColumnsFromClass("Board states", "key", modelData.getPrefWidth()/2));
        modelData.getColumns().add(createColumnsFromClass("Weight", "value", modelData.getPrefWidth()/2));
    }

    /**
     * Initialize the session results table
     */
    private void initializeSessionResultsTable() {
        sessionsResults.setEditable(true);
        double width = sessionsResults.getPrefWidth() / 8;
        sessionsResults.getColumns().add(createColumnsFromClass("Session number", "sessionNumber", width));
        sessionsResults.getColumns().add(createColumnsFromClass("Session won", "sessionsWon", width));
        sessionsResults.getColumns().add(createColumnsFromClass("Session lost", "sessionsLost", width));
        sessionsResults.getColumns().add(createColumnsFromClass("Total games played", "totalGamesPlayed", width));
        sessionsResults.getColumns().add(createColumnsFromClass("Games played", "gamesPlayed", width));
        sessionsResults.getColumns().add(createColumnsFromClass("Games won", "gamesWon", width));
        sessionsResults.getColumns().add(createColumnsFromClass("Games lost", "gamesLost", width));
        sessionsResults.getColumns().add(createColumnsFromClass("Win/Loss ratio", "winLostRatio", width));
    }

    /**
     * Create a column from a model
     * @param title of the column
     * @param name of the model field associated with it
     * @param width of the column
     * @param <Model> class base on
     * @param <Type> of the field in question
     * @return [TableColumn] to add to the table
     */
    private <Model, Type> TableColumn<Model, Type> createColumnsFromClass(String title, String name, double width) {
        TableColumn<Model, Type> col = new TableColumn<>(title);
        col.setCellValueFactory(new PropertyValueFactory<>(name));
        col.setPrefWidth(width);
        return col;
    }
}
