package dev.emileboucher.blackjackml.singleton;

import java.util.HashMap;
import java.util.Map;

public class BlackJackSingleton {
    private static BlackJackSingleton instance = null;
    private final HashMap<String, Integer> model;
    private final Map<String, Runnable> listeners;
    private BlackJackSingleton() {
        model = new HashMap<>();
        listeners = new HashMap<>();
    }

    public static BlackJackSingleton getInstance() {
        if (instance == null) {
            instance = new BlackJackSingleton();
        }
        return instance;
    }

    public HashMap<String, Integer> getModel() {
        return model;
    }

    public void setListener(Runnable p_listener) {
        listeners.put(p_listener.toString(), p_listener);
    }

    public void addData(String key, Integer value) {
        model.put(key, value);
        notification();
    }

    public void notification() {
        for (var runnable: listeners.values()) {
            runnable.run();
        }
    }
}
