package dev.emileboucher.blackjackml.singletons;

import dev.emileboucher.blackjackml.models.datamodel.ReinforcementLearning;
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
    private final JsonFiles dataManager;
    private List<Runnable> callbacks = new LinkedList<>();

    private AiSingleton() {
        dataManager = new JsonFiles("./model.json");
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
     * @return [HashMap<String, Integer>]
     */
    public HashMap<String, Integer> getModel() {
        return model.getData();
    }

    /**
     * Get the list of the reports row
     * @return [List<ReportRow>]
     */
    public List<ReportRow> getReports() {
        return reports;
    }

    /**
     * Tell the ai if it needs to stop
     * @return [Boolean]
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
        model.setGamePlayed(gamePlayed + model.getGamePlayed());
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
}
