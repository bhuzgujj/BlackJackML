package dev.emileboucher.blackjackml.gamehandlers;

import dev.emileboucher.blackjackml.models.requests.Hit;
import dev.emileboucher.blackjackml.models.requests.Hold;
import dev.emileboucher.blackjackml.models.responses.BlackJackResponse;
import dev.emileboucher.blackjackml.models.responses.Card;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestDecisionMaker {
  private final BlackJackResponse response = new BlackJackResponse();
  @BeforeEach
  public void init() {
    DecisionMaker.random.setSeed(0);
    response.dealerHand.add(new Card("A","mock"));
    response.playerHand.add(new Card("A","mock"));
    response.playerHand.add(new Card("K","mock"));
  }

  @Test
  public void reinforcementLearning() {
    // Test of value 21
    Assertions.assertEquals(
            Hold.class.getName(),
            DecisionMaker.reinforcementLearning(response, false).getClass().getName()
    );
    response.playerHand.remove(0);

    // Test of random
    Assertions.assertEquals(
            Hold.class.getName(),
            DecisionMaker.reinforcementLearning(response, true).getClass().getName()
    );
    DecisionMaker.reinforcementLearning(response, true);
    Assertions.assertEquals(
            Hit.class.getName(),
            DecisionMaker.reinforcementLearning(response, true).getClass().getName()
    );

    // Test of value not in model.json
    for (int i = 0; i < 10; i++) {
      response.playerHand.add(new Card("A", "mock"));
    }
    Assertions.assertEquals(
            Hold.class.getName(),
            DecisionMaker.reinforcementLearning(response, false).getClass().getName()
    );
  }
}
