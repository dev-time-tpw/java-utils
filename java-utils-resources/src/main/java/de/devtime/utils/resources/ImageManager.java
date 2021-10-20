package de.devtime.utils.resources;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

/**
 * All images that are required for an application are managed here.
 * <p>
 * Currently the following image formats are supported.
 * <ul>
 * <li>Joint Photographic Experts Group (JPG/JEPG)</li>
 * <li>Graphics Interchange Format (GIF)</li>
 * <li>Portable Network Graphics (PNG)</li>
 * <li>Tagged Image File Format (TIF/TIFF)</li>
 * <li>Windows Bitmap (BMP)</li>
 * <li>Microsoft Icon (ICO)</li>
 * </ul>
 *
 * @author morrigan
 * @since 0.0.1
 */
public class ImageManager {

  private static final Logger LOG = LoggerFactory.getLogger(ImageManager.class);

  /**
   * @return only instance of this manager
   * @since 0.0.1
   */
  public static ImageManager getInstance() {
    return INSTANCE;
  }

  private static final ImageManager INSTANCE = new ImageManager();

  /** Supported file extensions corresponding to the image format */
  private static final String[] SUPPORTED_FILE_EXTENSIONS = new String[] {
      ".bmp", ".gif", ".ico", ".jpg", ".jpeg", ".png", ".tif", ".tiff",
      ".BMP", ".GIF", ".ICO", ".JPG", ".JPEG", ".PNG", ".TIF", ".TIFF"
  };

  private final Map<String, Image> imageCache;

  /* Private singleton constructor */
  private ImageManager() {
    super();

    this.imageCache = new HashMap<>();
  }

  /**
   * Searches for an image based on a image name that was previously loaded by the filename of an image and returns the
   * image. If no image exists for the image name, an empty optional is returned.
   *
   * @param imageName a name of an image (corresponding to the filename)
   * @return an image wrapped in an optional or an empty optional
   * @since 0.0.1
   */
  public Optional<Image> getImage(String imageName) {
    return getImage(imageName, Optional.empty(), Optional.empty());
  }

  /**
   * Searches for an image based on a image name that was previously loaded by the filename of an image and returns the
   * image in the given size. If no image exists for the image name, an empty optional is returned.
   *
   * @param imageName a name of an image (corresponding to the filename)
   * @param scaleToWidth scale factor for with <i>(greater 0)</i>
   * @param scaleToHeight scale factor for height <i>(greater 0)</i>
   * @return an image wrapped in an optional or an empty optional
   * @since 0.0.1
   */
  public Optional<Image> getImage(String imageName, int scaleToWidth, int scaleToHeight) {
    Preconditions.checkArgument(scaleToWidth > 0, "The scale factor for width must be greater than 0.");
    Preconditions.checkArgument(scaleToHeight > 0, "The scale factor for height must be greater than 0.");
    return getImage(imageName, Optional.of(scaleToWidth), Optional.of(scaleToHeight));
  }

  /**
   * Searches for an image based on a image name that was previously loaded by the filename of an image and returns the
   * image in the given size. If no image exists for the image name, an empty optional is returned.
   *
   * @param imageName a name of an image (corresponding to the filename)
   * @param scaleToDimension scale factor <i>(not null)</i>
   * @return an image wrapped in an optional or an empty optional
   * @since 0.0.1
   */
  public Optional<Image> getImage(String imageName, Dimension scaleToDimension) {
    Preconditions.checkNotNull(scaleToDimension, "The scale dimension factor must not be null!");
    return getImage(imageName, Optional.of(scaleToDimension.width), Optional.of(scaleToDimension.height));
  }

  /**
   * Searches for an icon image based on a image name that was previously loaded by the filename of an image and returns
   * the image as an {@code ImageIcon}. If no image exists for the image name, an empty optional is returned.
   *
   * @param imageName a name of an image (corresponding to the filename)
   * @return an icon image wrapped in an optional or an empty optional
   * @since 0.0.1
   */
  public Optional<ImageIcon> getImageIcon(String imageName) {
    return getImageIcon(imageName, Optional.empty(), Optional.empty());
  }

  /**
   * Searches for an icon image based on a image name that was previously loaded by the filename of an image and returns
   * the image as an {@code ImageIcon} in the given size. If no image exists for the image name, an empty optional is
   * returned.
   *
   * @param imageName a name of an image (corresponding to the filename)
   * @param scaleToWidth scale factor for with <i>(greater 0)</i>
   * @param scaleToHeight scale factor for height <i>(greater 0)</i>
   * @return an icon image wrapped in an optional or an empty optional
   * @since 0.0.1
   */
  public Optional<ImageIcon> getImageIcon(String imageName, int scaleToWidth, int scaleToHeight) {
    Preconditions.checkArgument(scaleToWidth > 0, "The scale factor for width must be greater than 0.");
    Preconditions.checkArgument(scaleToHeight > 0, "The scale factor for height must be greater than 0.");
    return getImageIcon(imageName, Optional.of(scaleToWidth), Optional.of(scaleToHeight));
  }

  /**
   * Searches for an icon image based on a image name that was previously loaded by the filename of an image and returns
   * the image as an {@code ImageIcon} in the given size. If no image exists for the image name, an empty optional is
   * returned.
   *
   * @param imageName a name of an image (corresponding to the filename)
   * @param scaleToDimension scale factor <i>(not null)</i>
   * @return an icon image wrapped in an optional or an empty optional
   * @since 0.0.1
   */
  public Optional<ImageIcon> getImageIcon(String imageName, Dimension scaleToDimension) {
    Preconditions.checkNotNull(scaleToDimension, "The scale dimension factor must not be null!");
    return getImageIcon(imageName, Optional.of(scaleToDimension.width), Optional.of(scaleToDimension.height));
  }

  /**
   * Loads all images from all resource directories that are on the classpath. The images must have one of the supported
   * extensions.
   *
   * @since 0.0.1
   */
  public void loadAllImagesFromResources() {
    loadAllImagesFromResources("", SUPPORTED_FILE_EXTENSIONS);
  }

  /**
   * Loads all images from the given resource directory that is on the classpath. The images must have obe of the
   * supported extensions.
   *
   * @param directory a directory which contains the images
   * @since 0.0.1
   */
  public void loadAllImagesFromResources(String directory) {
    loadAllImagesFromResources(directory, SUPPORTED_FILE_EXTENSIONS);
  }

  /**
   * Loads all images filtered by file extensions from the given resource directory that is on the classpath.
   *
   * @param directory a directory which contains the images
   * @param fileExtensions a set of file extensions
   * @since 0.0.1
   */
  public void loadAllImagesFromResources(String directory, String... fileExtensions) {
    Reflections reflections = new Reflections(directory, new ResourcesScanner());
    Set<String> availableImages = new HashSet<>();
    for (String fileExtension : fileExtensions) {
      availableImages.addAll(reflections.getResources(Pattern.compile(".*\\" + fileExtension)));
    }

    for (String imagePath : availableImages) {
      String baseName = FilenameUtils.getBaseName(imagePath);
      String extension = FilenameUtils.getExtension(imagePath);
      String key = StringUtils.join(baseName, "-", extension);
      try {
        addImage(key, imagePath);
      } catch (IOException e) {
        LOG.error(e.getMessage(), e);
      }
    }

    int amount = getImageNames().size();
    LOG.info("{} image{} loaded successfully.", amount, amount > 1 ? "s" : "");
  }

  /**
   * Loads a image based on its URL and adds it with the given image name to this manager.
   *
   * @param imageName Name an image name
   * @param url an URL to the image
   * @throws IOException if an error occurs during reading the image from URL
   * @since 0.0.1
   */
  public void loadImageFromUrl(String imageName, URL url) throws IOException {
    this.imageCache.put(imageName, ImageIO.read(url));
  }

  /**
   * Returns all image names based on there filenames which currently managed in by this manager.
   *
   * @return a set of image names
   * @since 0.0.1
   */
  public Set<String> getImageNames() {
    return this.imageCache.keySet();
  }

  /**
   * Clears all image entries from this manager.
   *
   * @since 0.0.1
   */
  public void clear() {
    this.imageCache.clear();
  }

  private void addImage(String imageName, String filePath) throws IOException {
    this.imageCache.put(imageName, ImageIO.read(getClass().getResourceAsStream("/" + filePath)));
  }

  private Optional<ImageIcon> getImageIcon(String imageName, Optional<Integer> scaleToWidth,
      Optional<Integer> scaleToHeight) {
    Optional<Image> image = getImage(imageName, scaleToWidth, scaleToHeight);
    Optional<ImageIcon> result = Optional.empty();
    if (image.isPresent()) {
      result = Optional.of(new ImageIcon(image.get()));
    }
    return result;
  }

  private Optional<Image> getImage(String imageName, Optional<Integer> scaleToWidth, Optional<Integer> scaleToHeight) {
    Optional<Image> result = Optional.ofNullable(this.imageCache.get(imageName));
    int width = 0;
    int height = 0;
    int newWidth = 0;
    int newHeight = 0;
    if (result.isPresent()) {
      BufferedImage img = (BufferedImage) result.get();
      width = img.getWidth();
      height = img.getHeight();
      if (scaleToWidth.isPresent()) {
        newWidth = scaleToWidth.get();
      }
      if (scaleToHeight.isPresent()) {
        newHeight = scaleToHeight.get();
      }
      if ((newWidth > 0 && newHeight > 0) && (width != newWidth || height != newHeight)) {
        result = Optional.of(result.get().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH));
      }
    } else {
      LOG.warn("Image with name {} is not available!", imageName);
    }
    return result;
  }
}
