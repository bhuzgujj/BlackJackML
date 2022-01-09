package dev.emileboucher.blackjackml.singletons;

import dev.emileboucher.blackjackml.models.ReportRow;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Singleton use to keep the data needed for the AI
 */
public class AiSingleton {
    private static AiSingleton instance = null;
    private final HashMap<String, Integer> model;
    private final List<ReportRow> reports;
    private Boolean isPlaying = false;
    private AiSingleton() {
        model = new HashMap<>();
        reports = new LinkedList<>();
    }

    /**
     * Get the instance
     * @return instance of [AiSingleton]
     */
    public static AiSingleton getInstance() {
        if (instance == null) {
            instance = new AiSingleton();
        }
        return instance;
    }

    /**
     * Get the model of the Reinformencent Learning
     * @return [HashMap<String, Integer>]
     */
    public HashMap<String, Integer> getModel() {
        return model;
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
        var vals = model.get(key);
        if (vals == null) vals = 0;
        if (vals <= 2000000000 && vals >= -2000000000) {
            model.put(key, vals + value);
        }
    }
}
