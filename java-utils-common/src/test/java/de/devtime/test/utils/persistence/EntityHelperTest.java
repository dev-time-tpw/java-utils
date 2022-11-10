package de.devtime.test.utils.persistence;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import de.devtime.utils.persistence.EntityHelper;

class EntityHelperTest {

  @Test
  void test_isClassEquals_should_return_true_when_left_and_right_are_identical() {
    String left = "Hello";
    String right = left;
    boolean result = EntityHelper.isClassEquals(left, right);
    assertThat(result, is(true));
  }
  
  @ParameterizedTest
  @CsvSource({",,true", ",'Hello',false", "Hello,,false"})
  void test_isClassEquals_with_some_basic_values(String left, String right, boolean expected) {
    boolean result = EntityHelper.isClassEquals(left, right);
    assertThat(result, is(expected));
  }
  
  @Test
  void test_isClassEquals_should_return_true_when_left_is_hibernate_proxy_and_right_is_not() {
    TestClass left = new HibernateProxyOfTestClass();
    TestClass right = new TestClass();
    boolean result = EntityHelper.isClassEquals(left, right);
    assertThat(result, is(true));
  }
  
  @Test
  void test_isClassEquals_should_return_true_when_left_is_no_hibernate_proxy_and_right_is() {
    TestClass left = new TestClass();
    TestClass right = new HibernateProxyOfTestClass();
    boolean result = EntityHelper.isClassEquals(left, right);
    assertThat(result, is(true));
  }

  @Test
  void test_isClassEquals_should_return_true_when_left_and_right_are_same_hibernate_proxy() {
    TestClass left = new HibernateProxyOfTestClass();
    TestClass right = new HibernateProxyOfTestClass();
    boolean result = EntityHelper.isClassEquals(left, right);
    assertThat(result, is(true));
  }
  
  @Test
  void test_isClassEquals_should_return_false_when_left_and_right_are_different_classes() {
    String left = "Hello";
    TestClass right = new TestClass();
    boolean result = EntityHelper.isClassEquals(left, right);
    assertThat(result, is(false));
  }
  
  @Test
  void test_isClassEquals_should_return_false_when_left_and_right_are_different_hibernate_proxies() {
    HibernateProxyOfHashSet left = new HibernateProxyOfHashSet();
    HibernateProxyOfTestClass right = new HibernateProxyOfTestClass();
    boolean result = EntityHelper.isClassEquals(left, right);
    assertThat(result, is(false));
  }
}
