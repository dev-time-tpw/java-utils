package de.devtime.utils.persistence;

import org.hibernate.proxy.HibernateProxy;

/**
 * Contains some helper methods in the context of entities.
 *
 * @author dev|time
 * @since 0.0.1
 */
public class EntityHelper {

  /**
   * Checks if the given objects are instances of the same class considering {@link HibernateProxy}.
   *
   * @param left first instance
   * @param right second instance
   * @return {@code true} if classes are equal, otherwise {@code false}.
   * @since 0.0.1
   */
  public static boolean isClassEquals(Object left, Object right) {
    if (left == right) {
      return true;
    }
    if (left == null || right == null) {
      return false;
    }

    Class<?> leftClass = left.getClass();
    Class<?> rightClass = right.getClass();
    if (left instanceof HibernateProxy) {
      leftClass = leftClass.getSuperclass();
    }
    if (right instanceof HibernateProxy) {
      rightClass = rightClass.getSuperclass();
    }
    return leftClass == rightClass;
  }

  private EntityHelper() {
    // private utility class constructor
  }
}
