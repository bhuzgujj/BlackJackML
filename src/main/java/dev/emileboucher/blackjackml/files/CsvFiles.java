package dev.emileboucher.blackjackml.files;

import dev.emileboucher.blackjackml.models.datamodels.ReportDataModel;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class CsvFiles<Model> implements DataManager<Model> {
  private final String filepath;

  public CsvFiles(String filepath) {
    if (filepath.endsWith(".csv")) {
      this.filepath = filepath;
    } else {
      this.filepath = filepath + ".csv";
    }
  }

  @Override
  public void save(Model model) {
    try {
      if (!Files.exists(Paths.get(filepath))) {
        Files.writeString(
                Paths.get(filepath),
                ReportDataModel.getTitle(),
                StandardOpenOption.CREATE
        );
      }
      Files.writeString(Paths.get(filepath), model.toString(), StandardOpenOption.APPEND);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  @Override
  public Model load() {
    return null;
  }
}
