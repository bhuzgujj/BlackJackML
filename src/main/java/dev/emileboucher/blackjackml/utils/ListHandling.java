package dev.emileboucher.blackjackml.utils;

import java.util.List;

/**
 * Functions generics for lists
 */
public class ListHandling {
  /**
   * Add an item and if the maximum is reached, remove the first item
   * @param list in question
   * @param item to add
   * @param maximum of item before removing the index 0
   * @param <T> Type of the list and item
   */
  public static<T> void addWithMaximum(List<T> list, T item, int maximum) {
    if (list.size() > maximum) {
      list.remove(0);
    }
    list.add(item);
  }
}
