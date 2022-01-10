package dev.emileboucher.blackjackml.gamehandlers;

import dev.emileboucher.blackjackml.api.models.Response;
import dev.emileboucher.blackjackml.api.requests.abstracts.RequestBuilder;
import dev.emileboucher.blackjackml.api.requests.concretes.Hit;
import dev.emileboucher.blackjackml.api.requests.concretes.Hold;
import dev.emileboucher.blackjackml.singletons.AiSingleton;

import java.util.Random;

/**
 * Static class making a decision depending on the type of AI chosen
 */
public class DecisionMaker {
    private static final Random random = new Random();

    /**
     * Use the reinforcement learning methodologie to pick
     * @param response of the api to the last request
     * @param isExploring if the ai is exploring random stuff
     * @return the [RequestBuilder] to send to the api
     */
    public static RequestBuilder reinforcementLearning(Response response, Boolean isExploring) {
        if (!isExploring) {
            var value = AiSingleton.getInstance().getModel().get(response.toString());
            if (value != null) {
                if (value > 10) {
                    return new Hold();
                } else if (value < -10) {
                    return new Hit();
                }
            }
        }
        return (random.nextInt(2) == 0) ? new Hold() : new Hit();
    }
}
