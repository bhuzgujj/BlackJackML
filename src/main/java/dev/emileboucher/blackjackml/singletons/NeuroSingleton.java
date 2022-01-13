package dev.emileboucher.blackjackml.singletons;

import dev.emileboucher.blackjackml.files.CsvFiles;
import dev.emileboucher.blackjackml.files.DataManager;
import dev.emileboucher.blackjackml.files.JsonFiles;
import dev.emileboucher.blackjackml.models.ReportRow;
import dev.emileboucher.blackjackml.models.datamodels.RLDataModel;
import dev.emileboucher.blackjackml.models.datamodels.ReportDataModel;

import java.math.BigDecimal;
import java.util.*;

/**
 * Singleton use to keep the data needed for the AI
 */
public class NeuroSingleton {
  private static NeuroSingleton instance = null;
  private final RLDataModel model;
  private final DataManager<RLDataModel> jsonManager;
  private final DataManager<ReportDataModel> csvManager;
  private final List<ReportRow> reports = new LinkedList<>();
  private final List<Runnable> onSessionNumberChange = new LinkedList<>();
  private final List<Runnable> onGamestateChange = new LinkedList<>();
  private final List<Runnable> updateSessionResults = new LinkedList<>();
  private Boolean isPlaying = false;

  //=======================================================================
  //  Singleton
  //-----------------------------------------------------------------------
  /**
   * Singleton constructor
   */
  private NeuroSingleton() {
    jsonManager = new JsonFiles<>(
            "./data/model.json",
            RLDataModel.class
    );
    csvManager = new CsvFiles<>("./data/reports.csv");
    model = Optional.ofNullable(jsonManager.load())
              .orElse(new RLDataModel());
  }

  /**
   * Get the instance
   * @return instance of [AiSingleton]
   */
  public static NeuroSingleton getInstance() {
    if (instance == null) {
      synchronized (NeuroSingleton.class) {
        instance = new NeuroSingleton();
      }
    }
    return instance;
  }

  //=======================================================================
  //  Model
  //-----------------------------------------------------------------------
  /**
   * Get the model of the Reinformencent Learning
   * @return the model of Reinformencent Learning
   */
  public HashMap<String, Integer> getModel() {
    return model.getData();
  }

  /**
   * Reward the model
   * @param key of the reward
   * @param value of the reward
   */
  public void reward(String key, Integer value) {
    var vals = model.getData().get(key);
    if (vals == null) vals = 0;
    if (vals <= 2000000000 && vals >= -2000000000) {
      model.getData().put(key, vals + value);
    }
  }

  /**
   * Save the model
   */
  public void saveModel() {
    jsonManager.save(model);
  }

  /**
   * Get the actual session number
   * @return session number
   */
  public long getSessionNumber() {
    return model.getSessionNumber();
  }

  /**
   * Get the percentage of progression toward the next update
   * @param maximum session number per updates
   * @return percentage of progression toward the next update
   */
  public double getProgression(int maximum) {
    return (double) (model.getSessionNumber() % maximum) / maximum;
  }

  /**
   * Increment the session number and trigger an update
   *      on the progression bar
   */
  public void incrementeSessionNumber() {
    model.setSessionNumber(model.getSessionNumber() + 1);
    onSessionNumberChange.forEach(Runnable::run);
  }

  /**
   * Get the actual session number
   * @return session number
   */
  public long getGamePlayed() {
    return model.getGamePlayed();
  }

  /**
   * Add game played
   * @param gamePlayed in total
   */
  public void setGamePlayed(long gamePlayed) {
    model.setGamePlayed(gamePlayed);
  }

  //=======================================================================
  //  Reports
  //-----------------------------------------------------------------------
  /**
   * Get the list of the reports row
   * @return the list of all reports
   */
  public List<ReportRow> getReports() {
    return reports;
  }

  /**
   * Add a report to the list of reports
   * @param report for X amount of sessions
   */
  public void addReport(ReportRow report) {
    if (reports.size() > 100) {
      reports.remove(0);
    }
    if (!Objects.equals(report.getWinrate(), BigDecimal.ZERO)) {
      csvManager.save(new ReportDataModel(report));
    }
    reports.add(report);
    updateSessionResults.forEach(Runnable::run);
  }

  //=======================================================================
  //  Is playing
  //-----------------------------------------------------------------------
  /**
   * Tell the ai if it needs to stop
   * @return isPlaying with the api
   */
  public Boolean getPlaying() {
    return isPlaying;
  }

  /**
   * Set if the ai should play
   * @param isPlaying with the api
   */
  public void setPlaying(Boolean isPlaying) {
    this.isPlaying = isPlaying;
    onGamestateChange.forEach(Runnable::run);
  }

  //=======================================================================
  //  Callbacks
  //-----------------------------------------------------------------------
  /**
   * Empty the lists of callbacks
   */
  public void EmptyCallbacks() {
    onSessionNumberChange.clear();
    onGamestateChange.clear();
    updateSessionResults.clear();
  }

  /**
   * Add a callback for everytime the session number changes
   * @param callback executed everytime the session number changes
   */
  public void addOnSessionNumberChange(Runnable callback) {
    this.onSessionNumberChange.add(callback);
  }

  /**
   * Add a callback for everytime the game-state changes
   * @param callback executed everytime the game-state changes
   */
  public void addOnGamestateChange(Runnable callback) {
    this.onGamestateChange.add(callback);
  }

  /**
   * Add a callback for everytime the session state changes
   * @param callback executed everytime the session state changes
   */
  public void addOnSessionStateChange(Runnable callback) {
    this.updateSessionResults.add(callback);
  }
}
