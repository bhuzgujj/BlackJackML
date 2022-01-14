package dev.emileboucher.blackjackml.controllers.ai;

import dev.emileboucher.blackjackml.models.GlobalButtons;
import javafx.fxml.Initializable;
import javafx.scene.Scene;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The controller of the UI for the neural network AI training/play
 */
public class NeuroController extends GlobalButtons implements Initializable {
  //=======================================================================
  //  External use function
  //-----------------------------------------------------------------------
  /**
   * Get the scene of this controller
   * @return the scene
   * @throws IOException if the ressource doesn't exist
   */
  public static Scene getScene() throws IOException {
    return createScene("neuro-view.fxml");
  }

  @Override
  public String getName() {
    return getClass().getName();
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
  }
}
