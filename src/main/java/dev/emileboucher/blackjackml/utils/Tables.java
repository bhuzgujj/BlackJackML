package dev.emileboucher.blackjackml.utils;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Utils to handle [TableView]s
 */
public class Tables {
  /**
   * Set up the table with fields name and columns name
   * @param table to add the fields in
   * @param fields name and variables linked
   * @param <M> model used
   */
  public static <M> void setupColumnsOf(TableView<M> table, String[][] fields) {
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
   * @param <M> model used
   * @param <T> type of the field in question
   * @return [TableColumn] to add to the table
   */
  private static <M, T> TableColumn<M, T> createColumnsFromClass(String title, String name, double width) {
    TableColumn<M, T> col = new TableColumn<>(title);
    col.setCellValueFactory(new PropertyValueFactory<>(name));
    col.setPrefWidth(width);
    return col;
  }

  /**
   * Replace the data in a table of the same model
   * @param table to update
   * @param data to show in
   * @param invert the order the data will show
   * @param <M> model used
   */
  public static <M> void replaceDataOf(TableView<M> table, M[] data, Boolean invert) {
    if (table.getItems().size() > 0) {
      table.getItems().clear();
    }
    if (invert) {
      for (M model : data) {
        table.getItems().add(0, model);
      }
    } else {
      for (M model : data) {
        table.getItems().add(model);
      }
    }
  }
}
