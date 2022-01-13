package dev.emileboucher.blackjackml.controllers;

import dev.emileboucher.blackjackml.models.GlobalButtons;
import javafx.fxml.Initializable;
import javafx.scene.Scene;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NeuroController extends GlobalButtons implements Initializable {
  public static final String NAME = "NeuroController";
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

  }
}
