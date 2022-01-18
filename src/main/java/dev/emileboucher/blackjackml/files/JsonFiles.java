package dev.emileboucher.blackjackml.files;

import com.google.gson.Gson;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Save and load as json locally
 */
public class JsonFiles<T> implements DataManager<T> {
  private final String filepath;
  private final Gson parser;
  private final Class<T> modelClass;

  /**
   * DataManager to save/load locally in the json format
   * @param filepath to load or save
   */
  public JsonFiles(String filepath, Class<T> modelClass) {
    if (filepath.endsWith(".json")) {
      this.filepath = filepath;
    } else {
      this.filepath = filepath + ".json";
    }
    this.modelClass = modelClass;
    parser = new Gson();
  }

  /**
   * Load the model from a json file stored locally
   * @return the [Model]
   */
  public T load() {
    try {
      return parser.fromJson(Files.readString(Paths.get(filepath)), modelClass);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return null;
  }

  /**
   * Save the model locally as json
   * @param model of the data
   */
  @Override
  public void save(T model) {
    try {
      Files.writeString(Paths.get(filepath), parser.toJson(model), StandardOpenOption.CREATE);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}
