package de.devtime.utils.swing;

import java.awt.Insets;

/**
 * Constants for several insets to layout UI components.
 *
 * @author morrigan
 * @since 0.0.1
 */
public class InsetConstants {

  /** Default spacing in pixel between UI components. */
  public static final int DEFAULT_DISTANCE = 5;

  /** Default small spacing in pixel for UI components (e.g. buttons in a button bar). */
  public static final int DEFAULT_SMALL_DISTANCE = 2;

  /** No insets. */
  public static final Insets NO_INSETS = new Insets(0, 0, 0, 0);

  /** Insets with default spacing on the top side. */
  public static final Insets TOP_INSETS = new Insets(DEFAULT_DISTANCE, 0, 0, 0);

  /** Insets with default spacing on the left side. */
  public static final Insets LEFT_INSETS = new Insets(0, DEFAULT_DISTANCE, 0, 0);

  /** Insets with default spacing on the bottom side. */
  public static final Insets BOTTOM_INSETS = new Insets(0, 0, DEFAULT_DISTANCE, 0);

  /** Insets with default spacing on the right side. */
  public static final Insets RIGHT_INSETS = new Insets(0, 0, 0, DEFAULT_DISTANCE);

  /** Insets with default spacing on the left and top side. */
  public static final Insets LT_INSETS = new Insets(DEFAULT_DISTANCE, DEFAULT_DISTANCE, 0, 0);

  /** Insets with default spacing on the left, top and right side. */
  public static final Insets LTR_INSETS = new Insets(DEFAULT_DISTANCE, DEFAULT_DISTANCE, 0, DEFAULT_DISTANCE);

  /** Insets with default spacing on the left, top and bottom side. */
  public static final Insets LTB_INSETS = new Insets(DEFAULT_DISTANCE, DEFAULT_DISTANCE, DEFAULT_DISTANCE, 0);

  /** Insets with default spacing in all directions. */
  public static final Insets ALL_INSETS = new Insets(DEFAULT_DISTANCE, DEFAULT_DISTANCE, DEFAULT_DISTANCE,
      DEFAULT_DISTANCE);

  /** Insets with small spacing on the top side. */
  public static final Insets SMALL_TOP_INSETS = new Insets(DEFAULT_SMALL_DISTANCE, 0, 0, 0);

  /** Insets with small spacing on the left side. */
  public static final Insets SMALL_LEFT_INSETS = new Insets(0, DEFAULT_SMALL_DISTANCE, 0, 0);

  /** Insets with small spacing on the bottom side. */
  public static final Insets SMALL_BOTTOM_INSETS = new Insets(0, 0, DEFAULT_SMALL_DISTANCE, 0);

  /** Insets with small spacing on the right side. */
  public static final Insets SMALL_RIGHT_INSETS = new Insets(0, 0, 0, DEFAULT_SMALL_DISTANCE);

  /** Insets with small spacing on the left and top side. */
  public static final Insets SMALL_LT_INSETS = new Insets(DEFAULT_SMALL_DISTANCE, DEFAULT_SMALL_DISTANCE, 0, 0);

  /** Insets with small spacing on the left, top and right side. */
  public static final Insets SMALL_LTR_INSETS = new Insets(DEFAULT_SMALL_DISTANCE, DEFAULT_SMALL_DISTANCE, 0,
      DEFAULT_SMALL_DISTANCE);

  /** Insets with small spacing on the left, top and bottom side. */
  public static final Insets SMALL_LTB_INSETS = new Insets(DEFAULT_SMALL_DISTANCE, DEFAULT_SMALL_DISTANCE,
      DEFAULT_SMALL_DISTANCE, 0);

  /** Insets with small spacing in all directions. */
  public static final Insets SMALL_ALL_INSETS = new Insets(DEFAULT_SMALL_DISTANCE, DEFAULT_SMALL_DISTANCE,
      DEFAULT_SMALL_DISTANCE, DEFAULT_SMALL_DISTANCE);

  private InsetConstants() {
    super();
  }
}