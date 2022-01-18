package dev.emileboucher.blackjackml.utils.async;

import javafx.concurrent.Service;

/**
 * Handle async runnable that callbacks a function modifying javafx stuff
 */
public class Async {
  /**
   * Run one runnable with one callback
   * @param runnable to execute
   * @param callback to do when the runnable are done
   */
  public static void Run(Runnable runnable, Runnable callback) {
    Create(runnable, callback).start();
  }

  /**
   * Create a service that runs one runnable with one callback
   * @param runnable to execute
   * @param callback to do when the runnable are done
   */
  public static<T> Service<T> Create(Runnable runnable, Runnable callback) {
    return new AsyncRun<>(runnable, callback);
  }
}
