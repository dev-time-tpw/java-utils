package de.devtime.test.utils.logging;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

import org.junit.jupiter.api.Test;

import de.devtime.test.utils.logging.helper.LoggingTestClass;

class CustomToStringStyleTest {

  @Test
  void test_toString_should_handle_referenced_object_with_LoggingId_annotation() {
    LoggingTestClass testClass = new LoggingTestClass();
    String toStringResult = testClass.toString();
    String expectedId = testClass.getReferencedClass().getId();
    String expectedIdentityId = testClass.getReferencedClass().getIdentityId();
    String expectedPart = new StringBuilder()
        .append("referencedClass=ReferencedClass[id=")
        .append(expectedId)
        .append(", identityId=")
        .append(expectedIdentityId)
        .toString();
    assertThat(toStringResult, containsString(expectedPart));
  }

  @Test
  void test_toString_should_handle_list_with_one_item() {
    LoggingTestClass testClass = new LoggingTestClass();
    String toStringResult = testClass.toString();
    String expectedPart = "listWith1Item=<size=1>[a]";
    assertThat(toStringResult, containsString(expectedPart));
  }

  @Test
  void test_toString_should_handle_list_with_two_items() {
    LoggingTestClass testClass = new LoggingTestClass();
    String toStringResult = testClass.toString();
    String expectedPart = "listWith2Items=<size=2>[a, b]";
    assertThat(toStringResult, containsString(expectedPart));
  }

  @Test
  void test_toString_should_handle_list_with_three_items() {
    LoggingTestClass testClass = new LoggingTestClass();
    String toStringResult = testClass.toString();
    String expectedPart = "listWith3Items=<size=3>[a, b, ...]";
    assertThat(toStringResult, containsString(expectedPart));
  }

  @Test
  void test_toString_should_handle_objectlist() {
    LoggingTestClass testClass = new LoggingTestClass();
    String toStringResult = testClass.toString();
    String expectedPart = new StringBuilder()
        .append("objectList=<size=3>[ReferencedClass[id=")
        .append(testClass.getObjectList().get(0).getId())
        .append(", identityId=")
        .append(testClass.getObjectList().get(0).getIdentityId())
        .append("], ReferencedClass[id=")
        .append(testClass.getObjectList().get(1).getId())
        .append(", identityId=")
        .append(testClass.getObjectList().get(1).getIdentityId())
        .append("], ...]")
        .toString();
    assertThat(toStringResult, containsString(expectedPart));
  }

  @Test
  void test_toString_should_handle_map_with_one_item() {
    LoggingTestClass testClass = new LoggingTestClass();
    String toStringResult = testClass.toString();
    String expectedPart = "mapWith1Item=<size=1>{1=a}";
    assertThat(toStringResult, containsString(expectedPart));
  }

  @Test
  void test_toString_should_handle_map_with_two_items() {
    LoggingTestClass testClass = new LoggingTestClass();
    String toStringResult = testClass.toString();
    String expectedPart = "mapWith2Items=<size=2>{1=a, 2=b}";
    assertThat(toStringResult, containsString(expectedPart));
  }

  @Test
  void test_toString_should_handle_map_with_three_items() {
    LoggingTestClass testClass = new LoggingTestClass();
    String toStringResult = testClass.toString();
    String expectedPart = "mapWith3Items=<size=3>{1=a, 2=b, ...}";
    assertThat(toStringResult, containsString(expectedPart));
  }

  @Test
  void test_toString_should_handle_objectmap() {
    LoggingTestClass testClass = new LoggingTestClass();
    String toStringResult = testClass.toString();
    String expectedPart = new StringBuilder()
        .append("objectMap=<size=3>{ref1=ReferencedClass[id=")
        .append(testClass.getObjectMap().get("ref1").getId())
        .append(", identityId=")
        .append(testClass.getObjectMap().get("ref1").getIdentityId())
        .append("], ref2=ReferencedClass[id=")
        .append(testClass.getObjectMap().get("ref2").getId())
        .append(", identityId=")
        .append(testClass.getObjectMap().get("ref2").getIdentityId())
        .append("], ...}")
        .toString();
    assertThat(toStringResult, containsString(expectedPart));
  }

  @Test
  void test_toString_should_handle_long_array() {
    LoggingTestClass testClass = new LoggingTestClass();
    String toStringResult = testClass.toString();
    String expectedPart = "longArray=<size=3>{1, 2, ...}";
    assertThat(toStringResult, containsString(expectedPart));
  }

  @Test
  void test_toString_should_handle_int_array() {
    LoggingTestClass testClass = new LoggingTestClass();
    String toStringResult = testClass.toString();
    String expectedPart = "intArray=<size=3>{1, 2, ...}";
    assertThat(toStringResult, containsString(expectedPart));
  }

  @Test
  void test_toString_should_handle_short_array() {
    LoggingTestClass testClass = new LoggingTestClass();
    String toStringResult = testClass.toString();
    String expectedPart = "shortArray=<size=3>{1, 2, ...}";
    assertThat(toStringResult, containsString(expectedPart));
  }

  @Test
  void test_toString_should_handle_byte_array() {
    LoggingTestClass testClass = new LoggingTestClass();
    String toStringResult = testClass.toString();
    String expectedPart = "byteArray=<size=3>{1, 2, ...}";
    assertThat(toStringResult, containsString(expectedPart));
  }

  @Test
  void test_toString_should_handle_char_array() {
    LoggingTestClass testClass = new LoggingTestClass();
    String toStringResult = testClass.toString();
    String expectedPart = "charArray=<size=3>{1, 2, ...}";
    assertThat(toStringResult, containsString(expectedPart));
  }

  @Test
  void test_toString_should_handle_double_array() {
    LoggingTestClass testClass = new LoggingTestClass();
    String toStringResult = testClass.toString();
    String expectedPart = "doubleArray=<size=3>{1.0, 2.0, ...}";
    assertThat(toStringResult, containsString(expectedPart));
  }

  @Test
  void test_toString_should_handle_float_array() {
    LoggingTestClass testClass = new LoggingTestClass();
    String toStringResult = testClass.toString();
    String expectedPart = "floatArray=<size=3>{1.0, 2.0, ...}";
    assertThat(toStringResult, containsString(expectedPart));
  }

  @Test
  void test_toString_should_handle_boolean_array() {
    LoggingTestClass testClass = new LoggingTestClass();
    String toStringResult = testClass.toString();
    String expectedPart = "booleanArray=<size=3>{true, false, ...}";
    assertThat(toStringResult, containsString(expectedPart));
  }

  @Test
  void test_toString_should_handle_objectarray() {
    LoggingTestClass testClass = new LoggingTestClass();
    String toStringResult = testClass.toString();
    String expectedPart = new StringBuilder()
        .append("objectArray=<size=3>{ReferencedClass[id=")
        .append(testClass.getObjectArray()[0].getId())
        .append(", identityId=")
        .append(testClass.getObjectArray()[0].getIdentityId())
        .append("], ReferencedClass[id=")
        .append(testClass.getObjectArray()[1].getId())
        .append(", identityId=")
        .append(testClass.getObjectArray()[1].getIdentityId())
        .append("], ...}")
        .toString();
    assertThat(toStringResult, containsString(expectedPart));
  }
}