package dev.emileboucher.blackjackml.files;

import com.google.gson.Gson;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Save and load as json locally
 */
public class JsonFiles <Model> implements DataManager<Model> {
  private final String filepath;
  private final Gson parser;
  private final Class<Model> modelClass;

  /**
   * DataManager to save/load locally in the json format
   * @param filepath to load or save
   */
  public JsonFiles(String filepath, Class<Model> modelClass) {
    this.filepath = filepath;
    this.modelClass = modelClass;
    parser = new Gson();
  }

  /**
   * load the model locally
   * @return  model of the ai for reinforcement learning
   */
  public Model load() {
    try {
      return parser.fromJson(Files.readString(Paths.get(filepath)), modelClass);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return null;
  }

  @Override
  public void save(Model model) {
    try {
      Files.writeString(Paths.get(filepath), parser.toJson(model), StandardOpenOption.CREATE);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}
