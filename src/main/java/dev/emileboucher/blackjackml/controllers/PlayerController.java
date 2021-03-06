package dev.emileboucher.blackjackml.controllers;

import dev.emileboucher.blackjackml.gamehandlers.PlayerHandler;
import dev.emileboucher.blackjackml.models.GlobalButtons;
import dev.emileboucher.blackjackml.models.responses.Card;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * The controller of the UI for the player interactions
 */
public class PlayerController extends GlobalButtons implements Initializable {
  /**
   * Name of the scene
   */
  public static final String NAME = "PlayerController";
  private static final String BET_PREFIX = "Bet : ";
  private final PlayerHandler handler = new PlayerHandler();

  //=======================================================================
  //  JavaFX components
  //-----------------------------------------------------------------------
  @FXML
  public Label cash = new Label();
  @FXML
  private Button dealBtn = new Button();
  @FXML
  private Button holdBtn = new Button();
  @FXML
  private Button hitBtn = new Button();
  @FXML
  private Button flagBtn = new Button();
  @FXML
  private Button loadBtn = new Button();
  @FXML
  private Slider betSlider = new Slider();
  @FXML
  private final Label betText = new Label();
  @FXML
  private TextField betAmount = new TextField();
  @FXML
  private HBox dealerCard = new HBox();
  @FXML
  private HBox playerCard = new HBox();

  //=======================================================================
  //  External use function
  //-----------------------------------------------------------------------
  /**
   * Get the scene of this controller
   * @return the scene
   * @throws IOException if the ressource doesn't exist
   */
  public static Scene getScene() throws IOException {
    return createScene("player-view.fxml");
  }

  //=======================================================================
  //  UI updaters
  //-----------------------------------------------------------------------
  /**
   * Update the entire UI
   */
  private void updateUI() {
    dealerCard.getChildren().clear();
    playerCard.getChildren().clear();
    cash.setText(" / " + handler.getCash() + " $");
    for (Card card : handler.getDealerCards()) {
      addCard(card, dealerCard);
    }
    for (Card card : handler.getPlayerCards()) {
      addCard(card, playerCard);
    }
    setButtonState(handler.isPlaying());
  }

  private void addCard(Card card, HBox hBox) {
    VBox container = new VBox();
    container.setPadding(new Insets(5));
    container.getChildren().addAll(new Label(card.rank), new Label(card.suit));
    container.alignmentProperty().setValue(Pos.CENTER);
    hBox.getChildren().add(container);
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
    betText.setText(BET_PREFIX + betSlider.getValue());
    initializeBetSlider();
    initializeBetAmount();
    hitBtn.setDisable(true);
    holdBtn.setDisable(true);
    flagBtn.setDisable(true);
    updateUI();
  }

  /**
   * Initialize the slider
   */
  private void initializeBetSlider() {
    betSlider.setMax(50.4);
    betSlider.setMin(1);
    betSlider.setValue(25);
    betSlider.valueProperty().addListener((observableValue, number, t1) -> {
      if (Integer.parseInt(betAmount.getText()) != number.intValue()) {
        betAmount.setText("" + number.intValue());
      }
    });
  }

  /**
   * Initialize the betAmount field
   */
  private void initializeBetAmount() {
    betAmount.setText("25");
    betAmount.textProperty().addListener((observable, oldValue, newValue) -> {
      if (Objects.equals(oldValue, newValue)) return;
      if (!newValue.matches("\\d*")) {
        betAmount.setText(newValue.replaceAll("[^\\d]", ""));
      }
      if (betAmount.getText().length() > 0) {
        int value = Integer.parseInt(betAmount.getText());
        if (value > 50) betAmount.setText("50");
        else if (value < 1) betAmount.setText("1");
        else betAmount.setText("" + value);
        return;
      }
      betAmount.setText("1");
    });
  }

  //=======================================================================
  //  JavaFX callbacks
  //-----------------------------------------------------------------------
  /**
   * Send a request to initiate the game
   */
  @FXML
  private void dealBtn() {
    disableAllBtn();
    handler.deal(Integer.parseInt(betAmount.getText()));
    updateUI();
  }

  /**
   * Send a request to get another card from the dealer
   */
  @FXML
  private void hitBtn() {
    disableAllBtn();
    handler.hit();
    updateUI();
  }

  /**
   * Send a request to hold your current card at get if you win or not
   */
  @FXML
  private void holdBtn() {
    disableAllBtn();
    handler.hold();
    updateUI();
  }

  /**
   * Send a request to finish a session if you have won that session
   */
  @FXML
  private void flagBtn() {
    disableAllBtn();
    handler.flag();
    updateUI();
  }

  /**
   * Send a request to get the board state
   */
  @FXML
  private void loadBtn() {
    disableAllBtn();
    handler.load();
    updateUI();
  }

  //=======================================================================
  //  Utility functions
  //-----------------------------------------------------------------------
  /**
   * Set the right buttons enable or disable depending on if the game
   *    is still active
   * @param isPlaying at the moment of the button setting
   */
  private void setButtonState(Boolean isPlaying) {
    boolean hasWon = handler.getCash() >= 2000;
    dealBtn.setDisable(hasWon || isPlaying);
    hitBtn.setDisable(hasWon || !isPlaying);
    holdBtn.setDisable(hasWon || !isPlaying);
    loadBtn.setDisable(false);
    flagBtn.setDisable(!hasWon);
  }

  /**
   * Disable all button
   */
  private void disableAllBtn() {
    dealBtn.setDisable(true);
    hitBtn.setDisable(true);
    flagBtn.setDisable(true);
    holdBtn.setDisable(true);
    loadBtn.setDisable(true);
  }
}
