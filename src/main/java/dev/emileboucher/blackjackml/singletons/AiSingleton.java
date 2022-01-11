package dev.emileboucher.blackjackml.singletons;

import dev.emileboucher.blackjackml.files.DataManager;
import dev.emileboucher.blackjackml.models.datamodels.ReinforcementLearning;
import dev.emileboucher.blackjackml.files.JsonFiles;
import dev.emileboucher.blackjackml.models.ReportRow;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Singleton use to keep the data needed for the AI
 */
public class AiSingleton {
    private static AiSingleton instance = null;
    private final ReinforcementLearning model;
    private final List<ReportRow> reports = new LinkedList<>();
    private final DataManager<ReinforcementLearning> dataManager;
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
    private AiSingleton() {
        dataManager = new JsonFiles<>(
            "./model.json",
            ReinforcementLearning.class
        );
        model = Optional.ofNullable(dataManager.load())
                    .orElse(new ReinforcementLearning());
    }

    /**
     * Get the instance
     * @return instance of [AiSingleton]
     */
    public static AiSingleton getInstance() {
        if (instance == null) {
            synchronized (AiSingleton.class) {
                instance = new AiSingleton();
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
        dataManager.save(model);
    }

    /**
     * Get the actual session number
     * @return session number
     */
    public long getSessionNumber() {
        return model.getSessionNumber();
    }

    /**
     * Get the pourcentage of progression toward the next update
     * @param maximum session number per updates
     * @return pourcentage of progression toward the next update
     */
    public double getProgression(int maximum) {
        return (double) (model.getSessionNumber() % maximum) / maximum;
    }

    /**
     * Incremente the session number and trigger an update
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
     * Add a callback for everytime the gamestate changes
     * @param callback executed everytime the gamestate changes
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
