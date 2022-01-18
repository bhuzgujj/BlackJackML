package dev.emileboucher.blackjackml.utils;

import dev.emileboucher.blackjackml.models.AlgoOptions;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;

public class Menu {
  public static void createMenuItem(String title, MenuButton menu) {
    MenuItem item = new MenuItem(title);
    item.onActionProperty().setValue((evt) -> {
      menu.setText(title);
    });
    menu.getItems().add(item);
  }

  public static void createMenuButton(VBox algobar) {
    MenuButton btn = new MenuButton(AlgoOptions.EQUAL.text);
    for (AlgoOptions option: AlgoOptions.values()) {
      Menu.createMenuItem(option.text, btn);
    }
    algobar.getChildren().add(btn);
  }
}
