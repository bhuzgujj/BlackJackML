package dev.emileboucher.blackjackml.controllers;

import dev.emileboucher.blackjackml.MainApplication;
import dev.emileboucher.blackjackml.models.GlobalButtons;
import dev.emileboucher.blackjackml.models.responses.Card;
import dev.emileboucher.blackjackml.gamehandlers.PlayerHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class PlayerController extends GlobalButtons implements Initializable {
  public static final String NAME = "PlayerController";
  public static final String BET_PREFIX = "Bet : ";
  private final PlayerHandler handler = new PlayerHandler();
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
  private final HBox dealerCard = new HBox();
  @FXML
  private final HBox playerCard = new HBox();

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

  private void updateUI() {
    dealerCard.getChildren().clear();
    playerCard.getChildren().clear();
    cash.setText(" / " + handler.getCash() + " $");
    for (Card card : handler.getDealerCards()) {
      dealerCard.getChildren().add(new Label(card.rank + " " + card.suit));
      System.out.println(card.rank + " " + card.suit);
    }
    for (Card card : handler.getPlayerCards()) {
      playerCard.getChildren().add(new Label(card.rank + " " + card.suit));
      System.out.println(card.rank + " " + card.suit);
    }
  }

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

  @FXML
  private void dealBtn() {
    dealBtn.setDisable(true);
    handler.deal(Integer.parseInt(betAmount.getText()));
    setButtonState(handler.isPlaying());
    updateUI();
  }

  @FXML
  private void hitBtn() {
    hitBtn.setDisable(true);
    handler.hit();
    setButtonState(handler.isPlaying());
    updateUI();
  }

  @FXML
  private void holdBtn() {
    holdBtn.setDisable(true);
    handler.hold();
    setButtonState(handler.isPlaying());
    updateUI();
  }

  @FXML
  private void flagBtn() {
    flagBtn.setDisable(true);
    handler.flag();
    updateUI();
  }

  @FXML
  private void loadBtn() {
    loadBtn.setDisable(true);
    handler.load();
    updateUI();
  }

  private void setButtonState(Boolean isPlaying) {
    dealBtn.setDisable(isPlaying);
    hitBtn.setDisable(!isPlaying);
    holdBtn.setDisable(!isPlaying);
  }

  /**
   * Get the scene of this controller
   * @return the scene
   */
  public static Scene getScene() throws IOException {
    return new Scene(
            new FXMLLoader(
                    MainApplication.class.getResource("player-view.fxml")
            ).load()
    );
  }
}
