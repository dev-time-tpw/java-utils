package de.devtime.utils.swing;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Objects;

import com.google.common.base.Preconditions;

/**
 * Provides constants and methods for configuring a {@link GridBagLayout} to make the GUI configuration clearer.
 *
 * @author morrigan
 * @since 0.0.1
 */
public class GCUtil {

  public static final int WEST = GridBagConstraints.WEST;
  public static final int SOUTH = GridBagConstraints.SOUTH;
  public static final int EAST = GridBagConstraints.EAST;
  public static final int NORTH = GridBagConstraints.NORTH;
  public static final int SOUTHWEST = GridBagConstraints.SOUTHWEST;
  public static final int SOUTHEAST = GridBagConstraints.SOUTHEAST;
  public static final int NORTHWEST = GridBagConstraints.NORTHWEST;
  public static final int NORTHEAST = GridBagConstraints.NORTHEAST;
  public static final int CENTER = GridBagConstraints.CENTER;
  public static final int NONE = GridBagConstraints.NONE;
  public static final int HORI = GridBagConstraints.HORIZONTAL;
  public static final int VERT = GridBagConstraints.VERTICAL;
  public static final int BOTH = GridBagConstraints.BOTH;

  /**
   * Configures a {@link GridBagConstraints} object.
   *
   * @param gc a object to configure <i>(not null)</i>.
   * @param gridx {@link GridBagConstraints#gridx}
   * @param gridy {@link GridBagConstraints#gridy}
   * @param anchor {@link GridBagConstraints#anchor}
   * @param fill {@link GridBagConstraints#fill}
   * @param weightx {@link GridBagConstraints#weightx}
   * @param weighty {@link GridBagConstraints#weighty}
   * @param gridwidth {@link GridBagConstraints#gridwidth}
   * @param gridheight {@link GridBagConstraints#gridheight}
   * @param insets {@link GridBagConstraints#insets}
   * @since 0.0.1
   */
  public static void configGC(final GridBagConstraints gc, final int gridx, final int gridy, final int anchor,
      final int fill, final double weightx, final double weighty, final int gridwidth, final int gridheight,
      final Insets insets) {
    Preconditions.checkArgument(Objects.nonNull(gc), "The GridBagConstrains object must not be null!");

    gc.gridx = gridx;
    gc.gridy = gridy;
    gc.anchor = anchor;
    gc.fill = fill;
    gc.weightx = weightx;
    gc.weighty = weighty;
    gc.gridwidth = gridwidth;
    gc.gridheight = gridheight;
    gc.insets = insets;
  }

  private GCUtil() {
    super();
  }
}
