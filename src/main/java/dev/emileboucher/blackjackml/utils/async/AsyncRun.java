package dev.emileboucher.blackjackml.utils.async;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 * Create a runnable service that callbacks to a javafx component
 * @param <T> the type
 */
public class AsyncRun<T> extends Service<T> {
  private final Task<T> task;

  /**
   * Create a runnable service that callbacks to a javafx component
   * @param runnable to execute
   * @param callback after the runnable
   */
  public AsyncRun(Runnable runnable, Runnable callback) {
    task = new AsyncTask<>(runnable, callback);
  }

  /**
   * Create a task
   * @return a task
   */
  @Override
  protected Task<T> createTask() {
    return task;
  }
}
