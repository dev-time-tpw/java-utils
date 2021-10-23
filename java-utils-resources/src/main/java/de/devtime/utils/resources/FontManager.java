package de.devtime.utils.resources;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.io.FilenameUtils;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * All fonts that are required for an application are managed here. Since applications can run on different operating
 * systems, all fonts must be supplied, loaded and made available by this manager.
 *
 * @author morrigan
 * @since 0.0.1
 */
public class FontManager {

  private static final Logger LOG = LoggerFactory.getLogger(FontManager.class);

  /**
   * Returns the only instance of this manager.
   *
   * @return this manager
   * @since 0.0.1
   */
  public static FontManager getInstance() {
    return INSTANCE;
  }

  private static final FontManager INSTANCE = new FontManager();

  private Map<String, Font> fontCache;

  /* Private singleton constructor */
  private FontManager() {
    super();

    this.fontCache = new HashMap<>();
  }

  /**
   * Searches for a font based on the name that was previously loaded and returns that font. If no font exists for the
   * name, an empty optional value is returned.
   *
   * @param fontName name of a font
   * @return a font wrapped in an optional or an empty optional
   * @since 0.0.1
   */
  public Optional<Font> getFont(String fontName) {
    return getFont(fontName, Optional.empty(), Optional.empty(), Optional.empty());
  }

  /**
   * Searches for a font based on the name that was previously loaded and returns that font in the given size. If no
   * font exists for the name, an empty optional value is returned.
   *
   * @param fontName name of a font
   * @param size a font size ({@link Font#deriveFont(float)} is used)
   * @return a font wrapped in an optional or an empty optional
   * @since 0.0.1
   */
  public Optional<Font> getFont(String fontName, float size) {
    return getFont(fontName, Optional.of(size), Optional.empty(), Optional.empty());
  }

  /**
   * Searches for a font based on the name that was previously loaded and returns that font in the given style. If no
   * font exists for the name, an empty optional value is returned.
   *
   * @param fontName name of a font
   * @param style a font style ({@link Font#deriveFont(int)} is used)
   * @return a font wrapped in an optional or an empty optional
   * @since 0.0.1
   */
  public Optional<Font> getFont(String fontName, int style) {
    return getFont(fontName, Optional.empty(), Optional.of(style), Optional.empty());
  }

  /**
   * Searches for a font based on the name that was previously loaded and returns that font with the given
   * transformation. If no font exists for the name, an empty optional value is returned.
   *
   * @param fontName name of a font
   * @param trans a transformation ({@link Font#deriveFont(AffineTransform)} is used)
   * @return a font wrapped in an optional or an empty optional
   * @since 0.0.1
   */
  public Optional<Font> getFont(String fontName, AffineTransform trans) {
    return getFont(fontName, Optional.empty(), Optional.empty(), Optional.of(trans));
  }

  /**
   * Searches for a font based on the name that was previously loaded and returns that font in the given size and style.
   * If no font exists for the name, an empty optional value is returned.
   *
   * @param fontName name of a font
   * @param size a font size ({@link Font#deriveFont(float)} is used)
   * @param style a font style ({@link Font#deriveFont(int)} is used)
   * @return a font wrapped in an optional or an empty optional
   * @since 0.0.1
   */
  public Optional<Font> getFont(String fontName, float size, int style) {
    return getFont(fontName, Optional.of(size), Optional.of(style), Optional.empty());
  }

  /**
   * Searches for a font based on the name that was previously loaded and returns that font in the given size and
   * transformation. If no font exists for the name, an empty optional value is returned.
   *
   * @param fontName name of a font
   * @param size a font size ({@link Font#deriveFont(float)} is used)
   * @param trans a transformation ({@link Font#deriveFont(AffineTransform)} is used)
   * @return a font wrapped in an optional or an empty optional
   * @since 0.0.1
   */
  public Optional<Font> getFont(String fontName, float size, AffineTransform trans) {
    return getFont(fontName, Optional.of(size), Optional.empty(), Optional.of(trans));
  }

  /**
   * Searches for a font based on the name that was previously loaded and returns that font in the given style and
   * transformation. If no font exists for the name, an empty optional value is returned.
   *
   * @param fontName name of a font
   * @param style a font style ({@link Font#deriveFont(int)} is used)
   * @param trans a transformation ({@link Font#deriveFont(AffineTransform)} is used)
   * @return a font wrapped in an optional or an empty optional
   * @since 0.0.1
   */
  public Optional<Font> getFont(String fontName, int style, AffineTransform trans) {
    return getFont(fontName, Optional.empty(), Optional.of(style), Optional.of(trans));
  }

  /**
   * Searches for a font based on the name that was previously loaded and returns that font in the given size, style and
   * transformation. If no font exists for the name, an empty optional value is returned.
   *
   * @param fontName name of a font
   * @param size a font size ({@link Font#deriveFont(float)} is used)
   * @param style a font style ({@link Font#deriveFont(int)} is used)
   * @param trans a transformation ({@link Font#deriveFont(AffineTransform)} is used)
   * @return a font wrapped in an optional or an empty optional
   * @since 0.0.1
   */
  public Optional<Font> getFont(String fontName, float size, int style, AffineTransform trans) {
    return getFont(fontName, Optional.of(size), Optional.of(style), Optional.of(trans));
  }

  /**
   * Loads all fonts from all resource directories that are on the classpath. The fonts must have the extension .ttf and
   * must be {@link Font#TRUETYPE_FONT} fonts.
   *
   * @since 0.0.1
   */
  public void loadAllFontsFromResources() {
    loadAllFontsFromResources("");
  }

  /**
   * Loads all fonts from the given resource directory that is on the classpath. The fonts must have the extension .ttf
   * and must be {@link Font#TRUETYPE_FONT} fonts.
   *
   * @param directory a directory which contains the fonts
   * @since 0.0.1
   */
  public void loadAllFontsFromResources(String directory) {
    loadAllFontsFromResources(directory, Font.TRUETYPE_FONT, ".ttf", ".TTF");
  }

  /**
   * Loads all fonts with the given file extension from the given resource directory that is on the classpath.
   *
   * @param directory a directory which contains the fonts
   * @param fontType a font type (e.g. {@link Font#TRUETYPE_FONT})
   * @param fileExtensions file extension of the fonts (e.g. .ttf)
   * @since 0.0.1
   */
  public void loadAllFontsFromResources(String directory, int fontType, String... fileExtensions) {
    Reflections reflections;
    if (directory == null) {
      reflections = new Reflections("", new ResourcesScanner());
    } else {
      reflections = new Reflections(directory, new ResourcesScanner());
    }
    Set<String> availableFonts = new HashSet<>();
    for (String fileExtension : fileExtensions) {
      availableFonts.addAll(reflections.getResources(Pattern.compile(".*\\" + fileExtension)));
    }

    for (String fontPath : availableFonts) {
      String baseName = FilenameUtils.getBaseName(fontPath);
      try {
        addFont(baseName, fontType, fontPath);
      } catch (FontFormatException | IOException e) {
        LOG.error(e.getMessage(), e);
      }
    }

    int amount = getFontNames().size();
    LOG.info("{} font{} loaded successfully.", amount, amount > 1 ? "s" : "");
  }

  /**
   * Returns all font names to the fonts currently managed in by this manager.
   *
   * @return a set of font names
   * @since 0.0.1
   */
  public Set<String> getFontNames() {
    return new HashSet<>(this.fontCache.keySet());
  }

  /**
   * Clears all font entries from this manager.
   *
   * @since 0.0.1
   */
  public void clear() {
    this.fontCache.clear();
  }

  private void addFont(String fontName, int fontType, String filePath) throws FontFormatException, IOException {
    this.fontCache.put(fontName, Font.createFont(fontType, getClass().getResourceAsStream("/" + filePath)));
  }

  private Optional<Font> getFont(String fontName, Optional<Float> size, Optional<Integer> style,
      Optional<AffineTransform> trans) {
    Optional<Font> result = Optional.ofNullable(this.fontCache.get(fontName));
    if (result.isPresent()) {
      Font font = result.get();
      if (size.isPresent()) {
        font = font.deriveFont(size.get());
      }
      if (style.isPresent()) {
        font = font.deriveFont(style.get());
      }
      if (trans.isPresent()) {
        font = font.deriveFont(trans.get());
      }
      result = Optional.of(font);
    } else {
      LOG.warn("Font with name {} is not available!", fontName);
    }
    return result;
  }
}
