package de.devtime.test.utils.logging.helper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import de.devtime.utils.logging.CustomToStringStyle;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoggingTestClass {

  private ReferencedClass referencedClass;
  private List<String> listWith1Item;
  private List<String> listWith2Items;
  private List<String> listWith3Items;
  private List<ReferencedClass> objectList;
  private Map<String, String> mapWith1Item;
  private Map<String, String> mapWith2Items;
  private Map<String, String> mapWith3Items;
  private Map<String, ReferencedClass> objectMap;
  private long[] longArray;
  private int[] intArray;
  private short[] shortArray;
  private byte[] byteArray;
  private char[] charArray;
  private double[] doubleArray;
  private float[] floatArray;
  private boolean[] booleanArray;
  private ReferencedClass[] objectArray;

  public LoggingTestClass() {
    this.referencedClass = new ReferencedClass("Ref");
    this.listWith1Item = Arrays.asList("a");
    this.listWith2Items = Arrays.asList("a", "b");
    this.listWith3Items = Arrays.asList("a", "b", "c");
    this.mapWith1Item = new HashMap<>();
    this.mapWith1Item.put("1", "a");
    this.mapWith2Items = new HashMap<>();
    this.mapWith2Items.put("1", "a");
    this.mapWith2Items.put("2", "b");
    this.mapWith3Items = new HashMap<>();
    this.mapWith3Items.put("1", "a");
    this.mapWith3Items.put("2", "b");
    this.mapWith3Items.put("3", "c");
    this.longArray = new long[] {
        1, 2, 3
    };
    this.intArray = new int[] {
        1, 2, 3
    };
    this.shortArray = new short[] {
        1, 2, 3
    };
    this.byteArray = new byte[] {
        1, 2, 3
    };
    this.charArray = new char[] {
        '1', '2', '3'
    };
    this.doubleArray = new double[] {
        1.0, 2.0, 3.0
    };
    this.floatArray = new float[] {
        1.0f, 2.0f, 3.0f
    };
    this.booleanArray = new boolean[] {
        true, false, true
    };
    this.objectArray = new ReferencedClass[] {
        new ReferencedClass("Ref1"), new ReferencedClass("Ref2"), new ReferencedClass("Ref3")
    };
    this.objectList = Arrays.asList(
        new ReferencedClass("Ref1"),
        new ReferencedClass("Ref2"),
        new ReferencedClass("Ref3"));
    this.objectMap = new HashMap<>();
    this.objectMap.put("ref1", new ReferencedClass("Ref1"));
    this.objectMap.put("ref2", new ReferencedClass("Ref2"));
    this.objectMap.put("ref3", new ReferencedClass("Ref3"));
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, CustomToStringStyle.PARAMETRIZED_STYLE);
  }
}
