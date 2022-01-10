package dev.emileboucher.blackjackml.controllers;

import dev.emileboucher.blackjackml.api.RestClient;
import dev.emileboucher.blackjackml.api.requests.concretes.Deal;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;
public class PlayerController implements Initializable {
  public static final String betPrefix = "Bet : ";
  @FXML
  private Slider bet = new Slider();
  @FXML
  private final Label betText = new Label();
  @FXML
  private TextField betAmount = new TextField();

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    bet.setMax(50);
    bet.setMin(1);
    bet.setValue(25);
    bet.valueProperty().addListener((observableValue, number, t1) -> {
      betAmount.setText("" + bet.valueProperty().intValue());
      System.out.println(bet.valueProperty().intValue());
    });
    betText.setText(betPrefix + bet.getValue());
    betAmount.setText("25");
    betAmount.textProperty().addListener((observable, oldValue, newValue) -> {
      if (!newValue.matches("\\d*")) {
        betAmount.setText(newValue.replaceAll("[^\\d]", ""));
      }
      int value = 1;
      if (betAmount.getText().length() > 0) {
        value = Integer.parseInt(betAmount.getText());
        if (value <= 0) {
          value = 1;
        }
        if (value >= 50) {
          value = 50;
        }
      }
      betAmount.setText("" + value);
      bet.setValue(value);
    });
  }

  @FXML
  private void dealBtn() {

  }
  @FXML
  private void hitBtn() {

  }
  @FXML
  private void holdBtn() {

  }
  @FXML
  private void flagBtn() {

  }

}
