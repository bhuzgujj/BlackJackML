package dev.emileboucher.blackjackml.controllers;

import dev.emileboucher.blackjackml.api.models.Card;
import dev.emileboucher.blackjackml.gamehandlers.PlayerHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
public class PlayerController implements Initializable {
  public static final String betPrefix = "Bet : ";
  private final PlayerHandler handler = new PlayerHandler();
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
    betText.setText(betPrefix + betSlider.getValue());
    initializeBetSlider();
    initializeBetAmount();
    updateUI();
  }

  private void updateUI() {
    dealerCard.getChildren().add(new Label("someting"));
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
    handler.deal(Integer.parseInt(betAmount.getText()));
    updateUI();
  }

  @FXML
  private void hitBtn() {
    handler.hit();
    updateUI();
  }

  @FXML
  private void holdBtn() {
    handler.hold();
    updateUI();
  }

  @FXML
  private void flagBtn() {
    handler.flag();
    updateUI();
  }
}
