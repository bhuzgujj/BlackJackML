package dev.emileboucher.blackjackml.files;

import com.google.gson.Gson;
import dev.emileboucher.blackjackml.models.datamodel.ReinforcementLearning;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Save and load as json locally
 */
public class JsonFiles {
  private final String filepath;
  private final Gson parser;

  /**
   * DataManager to save/load locally in the json format
   * @param filepath to load or save
   */
  public JsonFiles(String filepath) {
    this.filepath = filepath;
    parser = new Gson();
  }

  /**
   * Save the model locally
   * @param model of the ai for reinforcement learning
   */
  public void save(ReinforcementLearning model) {
    try {
      Files.writeString(Paths.get(filepath), parser.toJson(model), StandardOpenOption.CREATE);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * load the model locally
   * @return  model of the ai for reinforcement learning
   */
  public ReinforcementLearning load() {
    try {
      return parser.fromJson(Files.readString(Paths.get(filepath)), ReinforcementLearning.class);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return new ReinforcementLearning();
  }
}
