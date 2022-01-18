package dev.emileboucher.blackjackml.utils.async;

import javafx.application.Platform;
import javafx.concurrent.Task;

import java.util.concurrent.CountDownLatch;

/**
 * Execute runnable async and then callback
 * @param <T> the type
 */
public class AsyncTask<T> extends Task<T> {
  private final Runnable runnable;
  private final Runnable callback;

  /**
   * Execute runnable async and then callback
   * @param runnable to execute
   * @param callback after the runnable
   */
  public AsyncTask(Runnable runnable, Runnable callback) {
    this.runnable = runnable;
    this.callback = callback;
  }

  /**
   * Execution of the task
   * @return the type
   * @throws Exception for the callbacks
   */
  @Override
  protected T call() throws Exception {
    runnable.run();
    final CountDownLatch latch = new CountDownLatch(1);
    Platform.runLater(() -> {
      try {
        callback.run();
      } finally {
        latch.countDown();
      }
    });
    latch.await();
    return null;
  }
}
