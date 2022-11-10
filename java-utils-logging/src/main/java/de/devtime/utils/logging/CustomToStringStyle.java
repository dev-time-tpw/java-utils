package de.devtime.utils.logging;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.reflect.FieldUtils;

import de.devtime.utils.logging.annotations.LoggingId;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class CustomToStringStyle extends ToStringStyle {

  private static final long serialVersionUID = 1L;

  public static final ToStringStyle PARAMETRIZED_STYLE = new ParametrizedToStringStyle();

  private static final class ParametrizedToStringStyle extends CustomToStringStyle {

    private static final long serialVersionUID = 1L;

    ParametrizedToStringStyle() {
      setFieldSeparator(", ");
      setUseShortClassName(true);
      setUseIdentityHashCode(false);
      setUseLoggingIdAnnotationForReferencedObjects(true);
      setMaxItemPrintAmount(2);
    }

    private Object readResolve() {
      return PARAMETRIZED_STYLE;
    }
  }

  private boolean useLoggingIdAnnotationForReferencedObjects;
  private int maxItemPrintAmount;

  public boolean isUseLoggingIdAnnotationForReferencedObjects() {
    return this.useLoggingIdAnnotationForReferencedObjects;
  }

  public void setUseLoggingIdAnnotationForReferencedObjects(final boolean useLoggingIdAnnotationForReferencedObjects) {
    this.useLoggingIdAnnotationForReferencedObjects = useLoggingIdAnnotationForReferencedObjects;
  }

  public int getMaxItemPrintAmount() {
    return this.maxItemPrintAmount;
  }

  public void setMaxItemPrintAmount(int maxItemPrintAmount) {
    this.maxItemPrintAmount = maxItemPrintAmount;
  }

  @Override
  protected void appendDetail(final StringBuffer buffer, final String fieldName, final Object value) {
    if (isUseLoggingIdAnnotationForReferencedObjects()) {
      handleReferencedObjects(buffer, fieldName, value);
    } else {
      buffer.append(value);
    }
  }

  @Override
  protected void appendDetail(StringBuffer buffer, String fieldName, Collection<?> coll) {
    if (coll.size() > getMaxItemPrintAmount()) {
      Collection<Object> subset = new ArrayList<>();
      int counter = 0;
      Iterator<?> iterator = coll.iterator();
      while (iterator.hasNext() && counter < getMaxItemPrintAmount()) {
        subset.add(iterator.next());
        counter++;
      }
      appendCollectionDetails(buffer, fieldName, coll.size(), subset, false);
    } else {
      appendCollectionDetails(buffer, fieldName, coll.size(), coll, false);
    }
  }

  @Override
  protected void appendDetail(StringBuffer buffer, String fieldName, Map<?, ?> map) {
    int originSize = map.size();
    if (originSize > getMaxItemPrintAmount()) {
      Map<Object, Object> submap = new HashMap<>();
      List<Object> keys = new ArrayList<>(map.keySet());
      Collections.sort(keys, Comparator.comparing(Object::toString));
      for (int i = 0; i < getMaxItemPrintAmount(); i++) {
        Object key = keys.get(i);
        Object value = map.get(key);
        submap.put(key, value);
      }
      appendMapDetails(buffer, fieldName, originSize, submap);
    } else {
      appendMapDetails(buffer, fieldName, originSize, map);
    }
  }

  @Override
  protected void appendDetail(StringBuffer buffer, String fieldName, long[] array) {
    int originSize = array.length;
    List<Long> itemsToLog;
    if (originSize > getMaxItemPrintAmount()) {
      itemsToLog = Arrays.stream(array, 0, getMaxItemPrintAmount())
          .boxed()
          .toList();
    } else {
      itemsToLog = Arrays.stream(array, 0, originSize)
          .boxed()
          .toList();
    }
    appendCollectionDetails(buffer, fieldName, originSize, itemsToLog, true);
  }

  @Override
  protected void appendDetail(StringBuffer buffer, String fieldName, int[] array) {
    int originSize = array.length;
    List<Integer> itemsToLog;
    if (originSize > getMaxItemPrintAmount()) {
      itemsToLog = Arrays.stream(array, 0, getMaxItemPrintAmount())
          .boxed()
          .toList();
    } else {
      itemsToLog = Arrays.stream(array, 0, originSize)
          .boxed()
          .toList();
    }
    appendCollectionDetails(buffer, fieldName, originSize, itemsToLog, true);
  }

  @Override
  protected void appendDetail(StringBuffer buffer, String fieldName, short[] array) {
    int originSize = array.length;
    List<Short> itemsToLog = new ArrayList<>();
    if (originSize > getMaxItemPrintAmount()) {
      for (int i = 0; i < getMaxItemPrintAmount(); i++) {
        itemsToLog.add(array[i]);
      }
    } else {
      for (int i = 0; i < originSize; i++) {
        itemsToLog.add(array[i]);
      }
    }
    appendCollectionDetails(buffer, fieldName, originSize, itemsToLog, true);
  }

  @Override
  protected void appendDetail(StringBuffer buffer, String fieldName, byte[] array) {
    int originSize = array.length;
    List<Byte> itemsToLog = new ArrayList<>();
    if (originSize > getMaxItemPrintAmount()) {
      for (int i = 0; i < getMaxItemPrintAmount(); i++) {
        itemsToLog.add(array[i]);
      }
    } else {
      for (int i = 0; i < originSize; i++) {
        itemsToLog.add(array[i]);
      }
    }
    appendCollectionDetails(buffer, fieldName, originSize, itemsToLog, true);
  }

  @Override
  protected void appendDetail(StringBuffer buffer, String fieldName, char[] array) {
    int originSize = array.length;
    List<Character> itemsToLog = new ArrayList<>();
    if (originSize > getMaxItemPrintAmount()) {
      for (int i = 0; i < getMaxItemPrintAmount(); i++) {
        itemsToLog.add(array[i]);
      }
    } else {
      for (int i = 0; i < originSize; i++) {
        itemsToLog.add(array[i]);
      }
    }
    appendCollectionDetails(buffer, fieldName, originSize, itemsToLog, true);
  }

  @Override
  protected void appendDetail(StringBuffer buffer, String fieldName, double[] array) {
    int originSize = array.length;
    List<Double> itemsToLog = new ArrayList<>();
    if (originSize > getMaxItemPrintAmount()) {
      for (int i = 0; i < getMaxItemPrintAmount(); i++) {
        itemsToLog.add(array[i]);
      }
    } else {
      for (int i = 0; i < originSize; i++) {
        itemsToLog.add(array[i]);
      }
    }
    appendCollectionDetails(buffer, fieldName, originSize, itemsToLog, true);
  }

  @Override
  protected void appendDetail(StringBuffer buffer, String fieldName, float[] array) {
    int originSize = array.length;
    List<Float> itemsToLog = new ArrayList<>();
    if (originSize > getMaxItemPrintAmount()) {
      for (int i = 0; i < getMaxItemPrintAmount(); i++) {
        itemsToLog.add(array[i]);
      }
    } else {
      for (int i = 0; i < originSize; i++) {
        itemsToLog.add(array[i]);
      }
    }
    appendCollectionDetails(buffer, fieldName, originSize, itemsToLog, true);
  }

  @Override
  protected void appendDetail(StringBuffer buffer, String fieldName, boolean[] array) {
    int originSize = array.length;
    List<Boolean> itemsToLog = new ArrayList<>();
    if (originSize > getMaxItemPrintAmount()) {
      for (int i = 0; i < getMaxItemPrintAmount(); i++) {
        itemsToLog.add(array[i]);
      }
    } else {
      for (int i = 0; i < originSize; i++) {
        itemsToLog.add(array[i]);
      }
    }
    appendCollectionDetails(buffer, fieldName, originSize, itemsToLog, true);
  }

  @Override
  protected void appendDetail(StringBuffer buffer, String fieldName, Object[] array) {
    int originSize = array.length;
    List<Object> itemsToLog = new ArrayList<>();
    if (originSize > getMaxItemPrintAmount()) {
      for (int i = 0; i < getMaxItemPrintAmount(); i++) {
        itemsToLog.add(array[i]);
      }
    } else {
      for (int i = 0; i < originSize; i++) {
        itemsToLog.add(array[i]);
      }
    }
    appendCollectionDetails(buffer, fieldName, originSize, itemsToLog, true);
  }

  private void handleReferencedObjects(final StringBuffer buffer, final String fieldName, final Object value) {
    Field[] loggingIdFields = FieldUtils.getFieldsWithAnnotation(value.getClass(), LoggingId.class);
    if (loggingIdFields.length > 0) {
      appendClassName(buffer, value);
      appendContentStart(buffer);
      appendReferencedObjectContent(buffer, loggingIdFields, value);
      appendContentEnd(buffer);
    } else {
      buffer.append(value);
    }
  }

  private void appendReferencedObjectContent(StringBuffer buffer, Field[] loggingIdFields, Object value) {
    Iterator<Field> iterator = Arrays.asList(loggingIdFields).iterator();
    while (iterator.hasNext()) {
      Field loggingIdField = iterator.next();
      try {
        Object valueOfLoggingIdField = FieldUtils.readField(loggingIdField, value, true);
        buffer.append(loggingIdField.getName());
        buffer.append(getFieldNameValueSeparator());
        buffer.append(valueOfLoggingIdField == null ? null : valueOfLoggingIdField.toString());
        if (iterator.hasNext()) {
          buffer.append(getFieldSeparator());
        }
      } catch (IllegalAccessException e) {
        LOG.error(e.getMessage(), e);
        buffer.append(loggingIdField.getName());
        buffer.append(getFieldNameValueSeparator());
        buffer.append("ERROR ").append(e.getMessage());
      }
    }
  }

  private void appendCollectionDetails(StringBuffer buffer, String fieldName, int originSize,
      Collection<?> collectionToLog, boolean basedOnArray) {
    buffer.append(getSizeStartText());
    buffer.append(originSize);
    buffer.append(getSizeEndText());
    Iterator<?> iterator = collectionToLog.iterator();
    buffer.append(basedOnArray ? getArrayStart() : getContentStart());
    while (iterator.hasNext()) {
      handleReferencedObjects(buffer, fieldName, iterator.next());
      if (iterator.hasNext()) {
        buffer.append(getFieldSeparator());
      }
    }
    if (originSize > getMaxItemPrintAmount()) {
      buffer.append(getFieldSeparator());
      buffer.append("...");
    }
    buffer.append(basedOnArray ? getArrayEnd() : getContentEnd());
  }

  private void appendMapDetails(StringBuffer buffer, String fieldName, int originSize, Map<?, ?> map) {
    buffer.append(getSizeStartText());
    buffer.append(originSize);
    buffer.append(getSizeEndText());
    List<Object> keys = new ArrayList<>(map.keySet());
    Collections.sort(keys, Comparator.comparing(Object::toString));
    Iterator<?> iterator = keys.iterator();
    buffer.append(getArrayStart());
    while (iterator.hasNext()) {
      Object key = iterator.next();
      Object value = map.get(key);
      buffer.append(key);
      buffer.append(getFieldNameValueSeparator());
      handleReferencedObjects(buffer, fieldName, value);
      if (iterator.hasNext()) {
        buffer.append(getFieldSeparator());
      }
    }
    if (originSize > getMaxItemPrintAmount()) {
      buffer.append(getFieldSeparator());
      buffer.append("...");
    }
    buffer.append(getArrayEnd());
  }
}
