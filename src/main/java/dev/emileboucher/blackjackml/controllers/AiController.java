package dev.emileboucher.blackjackml.controllers;

import dev.emileboucher.blackjackml.MainApplication;
import dev.emileboucher.blackjackml.gamehandlers.AiHandling;
import dev.emileboucher.blackjackml.gamehandlers.ReinforcementLearningHandling;
import dev.emileboucher.blackjackml.models.GlobalButtons;
import dev.emileboucher.blackjackml.models.ModelRow;
import dev.emileboucher.blackjackml.models.ReportRow;
import dev.emileboucher.blackjackml.singletons.AiSingleton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the AI training/play
 */
public class AiController extends GlobalButtons implements Initializable {
  public static final String NAME = "AiController";
  private final AiHandling ai = new ReinforcementLearningHandling();
  @FXML
  private Button start = new Button();
  @FXML
  private Button back = new Button();
  @FXML
  private TextField sessionToDo = new TextField();
  @FXML
  private ProgressBar progress = new ProgressBar();
  @FXML
  private TableView<ModelRow> modelData = new TableView<>();
  @FXML
  private TableView<ReportRow> sessionsResults = new TableView<>();

  /**
   * Initialize the controller
   * @param url of the controller
   * @param resourceBundle of the controller
   */
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    AiSingleton.getInstance().EmptyCallbacks();
    AiSingleton.getInstance().addOnSessionNumberChange(this::progressBarUpdate);
    AiSingleton.getInstance().addOnGamestateChange(this::sessionDone);
    AiSingleton.getInstance().addOnSessionStateChange(this::updateSessionResults);
    sessionToDo.setText("100");
    sessionToDo.textProperty().addListener((observable, oldValue, newValue) -> {
      if (!newValue.matches("\\d*")) {
        sessionToDo.setText(newValue.replaceAll("[^\\d]", ""));
      }
    });
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
   * Button to start and stop the game
   */
  @FXML
  protected void startBtn() {
    back.setDisable(true);
    if (AiSingleton.getInstance().getPlaying()) {
      AiSingleton.getInstance().setPlaying(false);
      back.setDisable(false);
      sessionDone();
    } else {
      if (sessionToDo.getText().length() == 0) return;
      AiSingleton.getInstance().setPlaying(true);
      Thread game = new Thread(this::useAi);
      sessionToDo.setDisable(true);
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
   * Button to refresh the UI
   */
  @FXML
  protected void refreshBtn() {
    AiSingleton.getInstance().saveModel();
    updateUI();
  }

  /**
   * Use an AI to play and start playing
   */
  private void useAi() {
    try {
      while (AiSingleton.getInstance().getPlaying()) {
        AiSingleton.getInstance().addReport(ai.play(Integer.parseInt(sessionToDo.getText())));
        AiSingleton.getInstance().saveModel();
      }
    } catch (Exception exception) {
      System.out.println(exception.getMessage());
      AiSingleton.getInstance().setPlaying(false);
    }
  }

  /**
   * update the UI if the game is not playing
   */
  private void sessionDone() {
    if (!AiSingleton.getInstance().getPlaying()) {
      updateUI();
      start.setDisable(false);
      start.setText("Start");
    }
  }

  /**
   * Update the progress bar for the amount of session done
   */
  private void progressBarUpdate() {
    progress.setProgress(
            AiSingleton.getInstance().getProgression(
                    Integer.parseInt(sessionToDo.getText())
            )
    );
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
    String[][] columns = {
            {"Session number", "sessionNumber"},
            {"Session won", "sessionsWon"},
            {"Session lost", "sessionsLost"},
            {"Total games played", "totalGamesPlayed"},
            {"Games played", "gamesPlayed"},
            {"Games won", "gamesWon"},
            {"Games lost", "gamesLost"},
            {"Win/Loss ratio", "winLostRatio"},
    };
    double width = sessionsResults.getPrefWidth() / columns.length;
    for (var col : columns) {
      sessionsResults.getColumns().add(createColumnsFromClass(col[0], col[1], width));
    }
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

  /**
   * Get the scene of this controller
   * @return the scene
   */
  public static Scene getScene() throws IOException {
    return new Scene(
            new FXMLLoader(
                    MainApplication.class.getResource("ai-view.fxml")
            ).load()
    );
  }
}
