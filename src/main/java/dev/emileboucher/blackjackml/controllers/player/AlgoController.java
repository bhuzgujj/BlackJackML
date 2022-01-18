package dev.emileboucher.blackjackml.controllers.player;

import dev.emileboucher.blackjackml.utils.Menu;
import dev.emileboucher.blackjackml.utils.Tables;
import dev.emileboucher.blackjackml.models.GlobalButtons;
import dev.emileboucher.blackjackml.models.tables.ReportRow;
import dev.emileboucher.blackjackml.singletons.RLSingleton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The controller of the UI for the creation and test of player made
 *    algorithm
 */
public class AlgoController extends GlobalButtons implements Initializable {
  @FXML
  public TableView<ReportRow> results = new TableView<>();
  @FXML
  public VBox algobar = new VBox();

  //=======================================================================
  //  External use function
  //-----------------------------------------------------------------------
  /**
   * Get the scene of this controller
   * @return the scene
   * @throws IOException if the ressource doesn't exist
   */
  public static Scene getScene() throws IOException {
    return createScene("algo-view.fxml");
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
    updateResultData();
  }

  /**
   * Update the data in session results table
   */
  private void updateResultData() {
    Tables.replaceDataOf(results, RLSingleton.getInstance().getReports().toArray(ReportRow[]::new), true);
  }

  //=======================================================================
  //  Initialization
  //-----------------------------------------------------------------------
  /**
   * Initialize the controller
   * @param url of the controller
   * @param resourceBundle sent to the controller
   */
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    initializeControlBtns();
    initializeAlgoBar();
    Tables.setupColumnsOf(results, ReportRow.getFieldInfos());
    updateUI();
  }

  private void initializeAlgoBar() {
    Menu.createMenuButton(algobar);
    Menu.createMenuButton(algobar);
    Menu.createMenuButton(algobar);
    Menu.createMenuButton(algobar);
    Menu.createMenuButton(algobar);
  }
}
