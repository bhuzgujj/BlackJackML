package dev.emileboucher.blackjackml.models.responses;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

public class TestCard {
  private final List<Map.Entry<Card, Integer>> card = new LinkedList<>();

  @BeforeEach
  public void init() {
    card.add(new AbstractMap.SimpleEntry<>(new Card("2", "mock"), 2));
    card.add(new AbstractMap.SimpleEntry<>(new Card("K", "mock"), 10));
    card.add(new AbstractMap.SimpleEntry<>(new Card("10", "mock"), 10));
    card.add(new AbstractMap.SimpleEntry<>(new Card("A", "mock"), 11));
  }

  @Test
  public void getValue() {
    for (var set : card) {
      Assertions.assertEquals((int) set.getValue(), set.getKey().getValue());
    }
  }

  @Test
  public void getToString() {
    List<String> answers = new ArrayList<>();
    answers.add("2");
    answers.add("F");
    answers.add("F");
    answers.add("A");
    for (int i = 0; i < answers.size(); i++) {
      Assertions.assertEquals(answers.get(i), card.get(i).getKey().toString());
    }
  }

  @Test
  public void compareTo() {
    Assertions.assertTrue(card.get(0).getKey().compareTo(card.get(1).getKey()) < 0);
    Assertions.assertEquals(card.get(1).getKey().compareTo(card.get(2).getKey()), 0);
    Assertions.assertTrue(card.get(2).getKey().compareTo(card.get(3).getKey()) > 0);
  }
}
