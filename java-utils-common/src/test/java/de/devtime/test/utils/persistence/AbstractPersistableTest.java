package de.devtime.test.utils.persistence;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.UUID;

import org.apache.commons.lang3.reflect.MethodUtils;
import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.ConfiguredEqualsVerifier;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

class AbstractPersistableTest {

  @Test
  void test_isNew_should_return_true_after_creation() {
    TestEntity testEntity = TestEntity.builder().build();
    assertThat(testEntity.isNew(), is(true));
  }

  @Test
  void test_isNew_should_return_false_after_persist() throws Exception {
    TestEntity testEntity = TestEntity.builder().build();
    // simulate PostPersist event
    MethodUtils.invokeMethod(testEntity, true, "postPersist");
    assertThat(testEntity.isNew(), is(false));
  }

  @Test
  void test_hashCode_and_equals_work_as_expected() {
    ConfiguredEqualsVerifier verifier = EqualsVerifier.configure()
        .usingGetClass()
        .suppress(Warning.ALL_FIELDS_SHOULD_BE_USED);
    verifier.forClass(TestEntity.class).verify();
  }

  @Test
  void test_toString_work_as_expected() {
    TestEntity testEntity = TestEntity.builder()
        .withName("TestName")
        .build();
    String toStringResult = testEntity.toString();
    assertThat(toStringResult, containsString("TestEntity["));
    assertThat(toStringResult, containsString("name=TestName"));
    assertThat(toStringResult, containsString("isNew=true"));
  }

  @Test
  void test_init_generates_an_id() {
    TestEntity testEntity = TestEntity.builder()
        .withName("TestName")
        .build();
    testEntity.init();
    assertThat(UUID.fromString(testEntity.getId()).version(), is(equalTo(4)));
  }
}
