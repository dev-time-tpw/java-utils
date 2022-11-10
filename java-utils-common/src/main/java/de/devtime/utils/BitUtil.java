package de.devtime.utils;

import org.apache.commons.lang3.Validate;

/**
 * Provides various helper methods for bitwise manipulation of {@code long} values.
 *
 * @author morrigan
 * @since 0.0.1
 */
public class BitUtil {

  /**
   * Sets the bit at the specified position to 0 for the passed value.
   *
   * <pre>
   * BitUtils.removeLongBit(-1, 0)  = 0b1111 ... 1110
   * BitUtils.removeLongBit(-1, 63) = 0b0111 ... 1111
   * BitUtils.removeLongBit(-1, -1) = IllegalArgumentException
   * BitUtils.removeLongBit(-1, 64) = IllegalArgumentException
   * </pre>
   *
   * @param value a {@code long} value
   * @param position a position where the bit is to be set to 0 <i>([0;63])</i>
   * @return passed value with corrected bit
   * @throws IllegalArgumentException if {@code position} is invalid
   * @since 0.0.1
   */
  public static long removeLongBit(long value, int position) {
    Validate.inclusiveBetween(0, 63, position);
    return value & ~setLongBit(position);
  }

  /**
   * Sets bits to 0 at the passed value according to the specified bit mask.
   *
   * <pre>
   * BitUtils.removeLongBitsByMask(-1, 0b1000 ... 0001L) = 0b0111 ... 1110
   * </pre>
   *
   * @param value a {@code long} value
   * @param mask a bit mask with which single bits can be set to 0
   * @return passed value at which bits have been set to 0 according to the mask
   * @since 0.0.1
   */
  public static long removeLongBitsByMask(long value, long mask) {
    return value & ~mask;
  }

  /**
   * Sets all bits in a {@code long} value to 1.
   *
   * @return a value at which all bits are set to 1
   * @since 0.0.1
   */
  public static long setAllBits() {
    return -1L;
  }

  /**
   * Sets a bit at the specified position to 1.
   *
   * <pre>
   * BitUtils.setLongBit(0)  = 0b0000 ... 0001
   * BitUtils.setLongBit(63) = 0b1000 ... 0000
   * BitUtils.setLongBit(-1) = IllegalArgumentException
   * BitUtils.setLongBit(64) = IllegalArgumentException
   * </pre>
   *
   * @param position Position at which the bit is to be set <i>([0;63])</i>
   * @return {@code long} value at which exactly one bit is set to 1
   * @throws IllegalArgumentException if {@code position} is invalid
   * @since 0.0.1
   */
  public static long setLongBit(int position) {
    Validate.inclusiveBetween(0, 63, position);
    return 1L << position;
  }

  private BitUtil() {
    super();
  }
}
