package dev.emileboucher.blackjackml.controllers.utils;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Utils to handle [TableView]s
 */
public class Tables {
  /**
   * Setup the table with fields name and columns name
   * @param table to add the fields in
   * @param fields name and variables linked
   * @param <Model> linked with the table
   */
  public static <Model> void setupColumnsOf(TableView<Model> table, String[][] fields) {
    table.setEditable(true);
    double width = table.getPrefWidth() / fields.length;
    for (var col : fields) {
      table.getColumns().add(createColumnsFromClass(col[0], col[1], width));
    }
  }
  /**
   * Create a column from a model
   * @param title of the column
   * @param name of the model field associated with it
   * @param width of the column
   * @param <Model> class base on
   * @param <Type> of the field in question
   * @return [TableColumn] to add to the table
   */
  private static <Model, Type> TableColumn<Model, Type> createColumnsFromClass(String title, String name, double width) {
    TableColumn<Model, Type> col = new TableColumn<>(title);
    col.setCellValueFactory(new PropertyValueFactory<>(name));
    col.setPrefWidth(width);
    return col;
  }

  public static <Model> void updateDataOf(TableView<Model> table, Model[] data, Boolean invert) {
    if (table.getItems().size() > 0) {
      table.getItems().clear();
    }
    if (invert) {
      for (Model model : data) {
        table.getItems().add(0, model);
      }
    } else {
      for (Model model : data) {
        table.getItems().add(model);
      }
    }
  }
}
