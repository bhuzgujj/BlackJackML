package dev.emileboucher.blackjackml.singletons;

import java.util.HashMap;
import java.util.Map;

public class AiSingleton {
    private static AiSingleton instance = null;
    private final HashMap<String, Integer> model;
    private Boolean isPlaying = false;
    private final Map<String, Runnable> listeners;
    private AiSingleton() {
        model = new HashMap<>();
        listeners = new HashMap<>();
    }

    public static AiSingleton getInstance() {
        if (instance == null) {
            instance = new AiSingleton();
        }
        return instance;
    }

    public HashMap<String, Integer> getModel() {
        return model;
    }

    public Boolean getPlaying() {
        return isPlaying;
    }

    public void setPlaying(Boolean exiting) {
        isPlaying = exiting;
    }

    public void setListener(Runnable p_listener) {
        listeners.put(p_listener.toString(), p_listener);
    }

    public void notification() {
        for (var runnable: listeners.values()) {
            runnable.run();
        }
    }

    public void clearNotificationList() {
        listeners.clear();
    }

    public void reward(String key, Integer value) {
        var vals = model.get(key);
        if (vals == null) vals = 0;
        if (vals <= 2000000000 && vals >= -2000000000) {
            model.put(key, vals + value);
        }
    }
}
