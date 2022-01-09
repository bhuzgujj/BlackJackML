package dev.emileboucher.blackjackml.files;

import com.google.gson.Gson;
import dev.emileboucher.blackjackml.models.datamodel.ReinforcementLearning;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class JsonFiles implements DataManager {
  private final String filepath;
  private final Gson parser;

  public JsonFiles(String filepath) {
    this.filepath = filepath;
    parser = new Gson();
  }

  public void save(ReinforcementLearning model) {
    try {
      Files.writeString(Paths.get(filepath), parser.toJson(model), StandardOpenOption.CREATE);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public ReinforcementLearning load() {
    try {
      return parser.fromJson(Files.readString(Paths.get(filepath)), ReinforcementLearning.class);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return new ReinforcementLearning();
  }
}
