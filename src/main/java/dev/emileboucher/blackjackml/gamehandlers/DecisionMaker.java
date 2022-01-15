package dev.emileboucher.blackjackml.gamehandlers;

import dev.emileboucher.blackjackml.models.responses.BlackJackResponse;
import dev.emileboucher.blackjackml.api.requests.RequestBuilder;
import dev.emileboucher.blackjackml.models.requests.Hit;
import dev.emileboucher.blackjackml.models.requests.Hold;
import dev.emileboucher.blackjackml.singletons.RLSingleton;

import java.util.Optional;
import java.util.Random;

/**
 * Static class making a decision depending on the type of AI chosen
 */
public class DecisionMaker {
  protected static final Random random = new Random();

  /**
   * Use the reinforcement learning methodologie to pick
   * @param response of the api to the last request
   * @param isExploring if the ai is exploring random stuff
   * @return the [RequestBuilder] to send to the api
   */
  public static RequestBuilder reinforcementLearning(BlackJackResponse response, Boolean isExploring) {
    if (response.getPlayerHandValue() == 21) return new Hold();
    if (isExploring) return (random.nextBoolean()) ? new Hold() : new Hit();
    Integer value = Optional.ofNullable(RLSingleton.getInstance().getModel().get(response.toString()))
            .orElse(0);
    return (value < 0) ? new Hit() : new Hold();
  }
}
