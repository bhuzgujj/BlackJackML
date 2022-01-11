package dev.emileboucher.blackjackml.singletons;

import dev.emileboucher.blackjackml.files.DataManager;
import dev.emileboucher.blackjackml.models.datamodels.ReinforcementLearning;
import dev.emileboucher.blackjackml.files.JsonFiles;
import dev.emileboucher.blackjackml.models.ReportRow;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Singleton use to keep the data needed for the AI
 */
public class AiSingleton {
    private static AiSingleton instance = null;
    private final ReinforcementLearning model;
    private final List<ReportRow> reports;
    private Boolean isPlaying = false;
    private final DataManager<ReinforcementLearning> dataManager;
    private List<Runnable> callbacks = new LinkedList<>();
    private Runnable onGamestateChange = null;
    private Runnable updateSessionResults = null;

    private AiSingleton() {
        dataManager = new JsonFiles<>("./model.json", ReinforcementLearning.class);
        model = dataManager.load();
        reports = new LinkedList<>();
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

    /**
     * Get the model of the Reinformencent Learning
     * @return the model of Reinformencent Learning
     */
    public HashMap<String, Integer> getModel() {
        return model.getData();
    }

    /**
     * Get the list of the reports row
     * @return the list of all reports
     */
    public List<ReportRow> getReports() {
        return reports;
    }

    public void addReport(ReportRow report) {
        reports.add(report);
        if (updateSessionResults != null) {
            updateSessionResults.run();
        }
    }

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
        if (onGamestateChange != null) {
            onGamestateChange.run();
        }
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
        callbacks.forEach(Runnable::run);
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

    /**
     * Get the list of callbacks
     * @return the list of callbacks
     */
    public List<Runnable> getCallbacks() {
        return callbacks;
    }

    /**
     * Empty the list of callbacks
     */
    public void EmptyCallbacks() {
        callbacks = new LinkedList<>();
    }

    public void setOnGamestateChange(Runnable onGamestateChange) {
        this.onGamestateChange = onGamestateChange;
    }

    public void setOnSessionStateChange(Runnable updateSessionResults) {
        this.updateSessionResults = updateSessionResults;
    }
}
