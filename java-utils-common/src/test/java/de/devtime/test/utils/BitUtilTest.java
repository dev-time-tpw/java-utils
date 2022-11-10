package de.devtime.test.utils;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import de.devtime.utils.BitUtil;

class BitUtilTest {

  private static final long ALL_BITS_ONE = -1L;

  @Test
  void test_if_removeLongBit_remove_lowest_bit_correct() {
    long expected = -2;
    long actual = BitUtil.removeLongBit(ALL_BITS_ONE, 0);
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  void test_if_removeLongBit_remove_highest_bit_correct() {
    long expected = Long.MAX_VALUE;
    long actual = BitUtil.removeLongBit(ALL_BITS_ONE, 63);
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  void test_if_removeLongBit_throws_IAE_if_position_is_below_range() {
    assertThrows(IllegalArgumentException.class, () -> BitUtil.removeLongBit(ALL_BITS_ONE, -1));
  }

  @Test
  void test_if_removeLongBit_throws_IAE_if_position_is_exceed_range() {
    assertThrows(IllegalArgumentException.class, () -> {
      BitUtil.removeLongBit(ALL_BITS_ONE, 64);
    });
  }

  @Test
  void test_if_removeLongBitsByMask_remove_bits_correct() {
    long mask = 0b1000000000000000000000000000000000000000000000000000000000000001L;
    long expected = Long.MAX_VALUE - 1;
    long actual = BitUtil.removeLongBitsByMask(ALL_BITS_ONE, mask);
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  void test_if_setLongBit_set_lowest_bit_correct() {
    long expected = 1L;
    long actual = BitUtil.setLongBit(0);
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  void test_if_setLongBit_set_highest_bit_correct() {
    long expected = Long.MIN_VALUE;
    long actual = BitUtil.setLongBit(63);
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  void test_if_setLongBit_throws_IAE_if_position_is_below_range() {
    assertThrows(IllegalArgumentException.class, () -> BitUtil.setLongBit(-1));
  }

  @Test
  void test_if_setLongBit_throws_IAE_if_position_is_exceed_range() {
    assertThrows(IllegalArgumentException.class, () ->    BitUtil.setLongBit(64));
  }

  @Test
  void test_if_setAllBits_set_all_bits_to_one() {
    long expected = -1L;
    long actual = BitUtil.setAllBits();
    assertThat(actual, is(equalTo(expected)));
  }
}