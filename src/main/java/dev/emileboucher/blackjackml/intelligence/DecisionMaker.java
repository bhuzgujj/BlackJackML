package dev.emileboucher.blackjackml.intelligence;

import dev.emileboucher.blackjackml.api.models.Response;
import dev.emileboucher.blackjackml.api.requests.abstracts.RequestBuilder;
import dev.emileboucher.blackjackml.api.requests.concretes.Hit;
import dev.emileboucher.blackjackml.api.requests.concretes.Hold;
import dev.emileboucher.blackjackml.singletons.AiSingleton;

import java.util.Random;

public class DecisionMaker {
    public static Random random = new Random();
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
