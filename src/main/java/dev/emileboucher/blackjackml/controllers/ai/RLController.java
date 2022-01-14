package dev.emileboucher.blackjackml.controllers.ai;

import dev.emileboucher.blackjackml.controllers.utils.Tables;
import dev.emileboucher.blackjackml.gamehandlers.AiHandling;
import dev.emileboucher.blackjackml.gamehandlers.RLHandler;
import dev.emileboucher.blackjackml.models.GlobalButtons;
import dev.emileboucher.blackjackml.models.tables.ModelRow;
import dev.emileboucher.blackjackml.models.tables.ReportRow;
import dev.emileboucher.blackjackml.singletons.RLSingleton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The controller of the UI for the reinforcement learning AI training/play
 */
public class RLController extends GlobalButtons implements Initializable {
  private final AiHandling ai = new RLHandler();

  //=======================================================================
  //  JavaFX components
  //-----------------------------------------------------------------------
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
  private TableView<ReportRow> resultData = new TableView<>();

  //=======================================================================
  //  External use function
  //-----------------------------------------------------------------------
  /**
   * Get the scene of this controller
   * @return the scene
   * @throws IOException if the ressource doesn't exist
   */
  public static Scene getScene() throws IOException {
    return createScene("ai-view.fxml");
  }

  @Override
  public String getName() {
    return getClass().getName();
  }

  //=======================================================================
  //  UI updaters
  //-----------------------------------------------------------------------
  /**
   * Update the entire UI
   */
  private void updateUI() {
    updateModelData();
    updateResultData();
  }

  /**
   * Update the data in session results table
   */
  private void updateResultData() {
    Tables.updateDataOf(resultData, RLSingleton.getInstance().getReports().toArray(ReportRow[]::new), true);
  }

  /**
   * Update the data in model data table
   */
  private void updateModelData() {
    Tables.updateDataOf(modelData, ModelRow.toArray(RLSingleton.getInstance().getModel()), false);
  }

  //=======================================================================
  //  Initialization
  //-----------------------------------------------------------------------
  /**
   * Initialize the controller
   * @param url of the controller
   * @param resourceBundle of the controller
   */
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    initializeControlBtns();
    initializeCallbacks();
    setupIntergerTextField(sessionToDo, 100);
    Tables.setupColumnsOf(resultData, ReportRow.getFieldInfos());
    Tables.setupColumnsOf(modelData, ModelRow.getFieldInfos());
    updateUI();
  }

  /**
   * Setup a textfield to only accept integer
   * @param field to setup
   * @param defaultValue to set
   */
  private void setupIntergerTextField(TextField field, Integer defaultValue) {
    field.setText(defaultValue.toString());
    field.textProperty().addListener((observable, oldValue, newValue) -> {
      if (!newValue.matches("\\d*")) {
        field.setText(newValue.replaceAll("[^\\d]", ""));
      }
    });
  }

  private void initializeCallbacks() {
    RLSingleton.getInstance().EmptyCallbacks();
    RLSingleton.getInstance().addOnSessionNumberChange(this::progressBarUpdate);
    RLSingleton.getInstance().addOnGamestateChange(this::sessionDone);
    RLSingleton.getInstance().addOnSessionStateChange(this::updateUI);
  }

  //=======================================================================
  //  JavaFX callbacks
  //-----------------------------------------------------------------------
  /**
   * Button to start and stop the game
   */
  @FXML
  protected void startBtn() {
    back.setDisable(true);
    if (RLSingleton.getInstance().getPlaying()) {
      RLSingleton.getInstance().setPlaying(false);
      back.setDisable(false);
      sessionDone();
    } else {
      if (sessionToDo.getText().length() == 0) return;
      RLSingleton.getInstance().setPlaying(true);
      Thread game = new Thread(this::useAi);
      sessionToDo.setDisable(true);
      try {
        game.join();
        game.start();
        start.setText("Stop");
      } catch (InterruptedException e) {
        e.getStackTrace();
        RLSingleton.getInstance().setPlaying(false);
      }
    }
  }

  /**
   * Button to refresh the UI
   */
  @FXML
  protected void refreshBtn() {
    RLSingleton.getInstance().saveModel();
    updateUI();
  }

  //=======================================================================
  //  Callbacks functions
  //-----------------------------------------------------------------------
  /**
   * update the UI if the game is not playing
   */
  private void sessionDone() {
    if (!RLSingleton.getInstance().getPlaying()) {
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
            RLSingleton.getInstance().getProgression(
                    Integer.parseInt(sessionToDo.getText())
            )
    );
  }

  //=======================================================================
  //  Utility functions
  //-----------------------------------------------------------------------
  /**
   * Use an AI to play and start playing
   */
  private void useAi() {
    try {
      while (RLSingleton.getInstance().getPlaying()) {
        RLSingleton.getInstance().addReport(ai.play(Integer.parseInt(sessionToDo.getText())));
        RLSingleton.getInstance().saveModel();
      }
    } catch (Exception exception) {
      System.out.println(exception.getMessage());
      exception.getStackTrace();
      RLSingleton.getInstance().setPlaying(false);
    }
  }
}
