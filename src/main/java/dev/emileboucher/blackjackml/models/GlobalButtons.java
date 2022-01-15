package dev.emileboucher.blackjackml.models;

import dev.emileboucher.blackjackml.MainApplication;
import dev.emileboucher.blackjackml.controllers.MainMenu;
import dev.emileboucher.blackjackml.singletons.SceneSingleton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import java.io.IOException;

/**
 * Class to put the globals buttons and logic in all controllers
 */
public abstract class GlobalButtons {
  @FXML
  private HBox controlBtns = new HBox();

  /**
   * Get the name of the instanced class within the abstract
   * @return the name of the class
   */
  public abstract String getName();

  //=======================================================================
  //  Button setups
  //-----------------------------------------------------------------------
  /**
   * Add buttons to save a scene and/or go to another scene
   *    to a HBox with fx:id="controlBtns"
   */
  public void initializeControlBtns() {
    createAndAddButtonToBox("To background task", this::saveAndBack);
    createAndAddButtonToBox("Exit to menu", this::backToMainMenu);
  }

  /**
   * Create buttons with a name and an action on click
   * @param name of the button
   * @param onAction of the button
   */
  private void createAndAddButtonToBox(String name, EventHandler<ActionEvent> onAction) {
    Button back = new Button(name);
    back.onActionProperty().setValue(onAction);
    controlBtns.getChildren().add(back);
  }


  //=======================================================================
  //  External use function
  //-----------------------------------------------------------------------
  /**
   * Create a scene from a ressource string
   * @param ressource string
   * @return the scene
   * @throws IOException if the ressource doesn't exist
   */
  public static Scene createScene(String ressource) throws IOException {
    return new Scene(
            new FXMLLoader(MainApplication.class.getResource(ressource)).load()
    );
  }

  //=======================================================================
  //  On Action functions
  //-----------------------------------------------------------------------
  /**
   * Return to the main menu and save the current scene
   */
  private void saveAndBack(ActionEvent actionEvent) {
    SceneSingleton.getInstance().saveLoadScene(getName(), MainMenu.class.getName());
  }

  /**
   * Return to the main menu
   */
  private void backToMainMenu(ActionEvent actionEvent) {
    SceneSingleton.getInstance().discardLoadScene(getName(), MainMenu.class.getName());
  }
}
