package de.devtime.test.utils.swing;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import org.junit.jupiter.api.Test;

import de.devtime.utils.swing.GcUtil;

class GCUtilTest {

  @Test
  void testConfigGCWithNull() {
    assertThrows(IllegalArgumentException.class, () -> GcUtil.configGc(null, 1, 2, 3, 4, 5.0, 6.0, 7, 8, null));
  }

  @Test
  void testConfigGC() {
    GridBagConstraints gc = new GridBagConstraints();
    GcUtil.configGc(gc, 1, 2, 3, 4, 5.0, 6.0, 7, 8, new Insets(11, 22, 33, 44));
    assertThat(gc.gridx, is(equalTo(1)));
    assertThat(gc.gridy, is(equalTo(2)));
    assertThat(gc.anchor, is(equalTo(3)));
    assertThat(gc.fill, is(equalTo(4)));
    assertThat(gc.weightx, is(equalTo(5.0)));
    assertThat(gc.weighty, is(equalTo(6.0)));
    assertThat(gc.gridwidth, is(equalTo(7)));
    assertThat(gc.gridheight, is(equalTo(8)));
    assertThat(gc.insets, is(notNullValue()));
    assertThat(gc.insets.top, is(equalTo(11)));
    assertThat(gc.insets.left, is(equalTo(22)));
    assertThat(gc.insets.bottom, is(equalTo(33)));
    assertThat(gc.insets.right, is(equalTo(44)));
  }

}
