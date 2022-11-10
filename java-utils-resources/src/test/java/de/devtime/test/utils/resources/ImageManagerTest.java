package de.devtime.test.utils.resources;

import static com.spotify.hamcrest.optional.OptionalMatchers.emptyOptional;
import static com.spotify.hamcrest.optional.OptionalMatchers.optionalWithValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.romankh3.image.comparison.ImageComparison;
import com.github.romankh3.image.comparison.model.ImageComparisonResult;
import com.github.romankh3.image.comparison.model.ImageComparisonState;

import de.devtime.utils.resources.ImageManager;

class ImageManagerTest {

  private ImageManager sut = ImageManager.getInstance();

  @BeforeEach
  void setup() {
    this.sut.clear();
  }

  @AfterEach
  void tearDown() {
    this.sut.clear();
  }

  @Test
  void testLoadAllImagesFromResources() {
    this.sut.loadAllImagesFromResources();

    Optional<Image> greenImage = this.sut.getImage("20x20_green-bmp");
    assertThat(greenImage, is(optionalWithValue()));
    Color colorGreen = new Color(((BufferedImage) greenImage.get()).getRGB(10, 10));
    assertThat(colorGreen.getGreen(), is(equalTo(255)));

    Optional<Image> redImage = this.sut.getImage("20x20_red-bmp");
    assertThat(redImage, is(optionalWithValue()));
    Color colorRed = new Color(((BufferedImage) redImage.get()).getRGB(10, 10));
    assertThat(colorRed.getRed(), is(equalTo(255)));

    assertThat(this.sut.getImageNames(), hasSize(15));
    assertThat(this.sut.getImageNames(),
        containsInAnyOrder("20x20_green-bmp", "20x20_green-gif", "20x20_green-ico", "20x20_green-jpeg",
            "20x20_green-jpg", "20x20_green-png", "20x20_green-tif", "20x20_green-tiff", "20x20_red-bmp",
            "20x20_red-gif", "20x20_red-ico", "20x20_red-jpg", "20x20_red-png", "20x20_red-tif", "97461-png"));
  }

  @Test
  void testLoadAllImagesFromResourcesInImagesDir() {
    this.sut.loadAllImagesFromResources("images");

    Optional<Image> greenImage = this.sut.getImage("20x20_green-bmp");
    assertThat(greenImage, is(optionalWithValue()));
    Color colorGreen = new Color(((BufferedImage) greenImage.get()).getRGB(10, 10));
    assertThat(colorGreen.getGreen(), is(equalTo(255)));

    Optional<Image> redImage = this.sut.getImage("20x20_red-bmp");
    assertThat(redImage, is(optionalWithValue()));
    Color colorRed = new Color(((BufferedImage) redImage.get()).getRGB(10, 10));
    assertThat(colorRed.getRed(), is(equalTo(255)));

    assertThat(this.sut.getImageNames(), hasSize(15));
    assertThat(this.sut.getImageNames(),
        containsInAnyOrder("20x20_green-bmp", "20x20_green-gif", "20x20_green-ico", "20x20_green-jpeg",
            "20x20_green-jpg", "20x20_green-png", "20x20_green-tif", "20x20_green-tiff", "20x20_red-bmp",
            "20x20_red-gif", "20x20_red-ico", "20x20_red-jpg", "20x20_red-png", "20x20_red-tif", "97461-png"));
  }

  @Test
  void testLoadAllImagesFromResourcesInSpecialDir() {
    this.sut.loadAllImagesFromResources("images/red");

    Optional<Image> redImage = this.sut.getImage("20x20_red-bmp");
    assertThat(redImage, is(optionalWithValue()));
    Color colorRed = new Color(((BufferedImage) redImage.get()).getRGB(10, 10));
    assertThat(colorRed.getRed(), is(equalTo(255)));

    assertThat(this.sut.getImageNames(), hasSize(6));
    assertThat(this.sut.getImageNames(),
        containsInAnyOrder("20x20_red-bmp", "20x20_red-gif", "20x20_red-ico", "20x20_red-jpg", "20x20_red-png",
            "20x20_red-tif"));
  }

  @Test
  void testLoadAllImagesFromResourceInUpperDir() {
    this.sut.loadAllImagesFromResources("images/upper");

    assertThat(this.sut.getImageNames(), hasSize(6));
    assertThat(this.sut.getImageNames(),
        containsInAnyOrder("20x20_green-bmp", "20x20_green-gif", "20x20_green-ico", "20x20_green-jpg",
            "20x20_green-png", "20x20_green-tif"));
  }

  @Test
  void testLoadAllImagesFromResourcesWithBmpExtension() {
    this.sut.loadAllImagesFromResources("images", ".bmp");
    assertThat(this.sut.getImageNames(), hasSize(2));
    assertThat(this.sut.getImageNames(), containsInAnyOrder("20x20_red-bmp", "20x20_green-bmp"));
  }

  @Test
  void testLoadAllImagesFromResourcesWithGifExtension() {
    this.sut.loadAllImagesFromResources("images", ".gif");
    assertThat(this.sut.getImageNames(), hasSize(2));
    assertThat(this.sut.getImageNames(), containsInAnyOrder("20x20_red-gif", "20x20_green-gif"));
  }

  @Test
  void testLoadAllImagesFromResourcesWithIcoExtension() {
    this.sut.loadAllImagesFromResources("images", ".ico");
    assertThat(this.sut.getImageNames(), hasSize(2));
    assertThat(this.sut.getImageNames(), containsInAnyOrder("20x20_red-ico", "20x20_green-ico"));
  }

  @Test
  void testLoadAllImagesFromResourcesWithJpgExtension() {
    this.sut.loadAllImagesFromResources("images", ".jpg");
    assertThat(this.sut.getImageNames(), hasSize(2));
    assertThat(this.sut.getImageNames(), containsInAnyOrder("20x20_red-jpg", "20x20_green-jpg"));
  }

  @Test
  void testLoadAllImagesFromResourcesWithPngExtension() {
    this.sut.loadAllImagesFromResources("images", ".png");
    assertThat(this.sut.getImageNames(), hasSize(3));
    assertThat(this.sut.getImageNames(), containsInAnyOrder("20x20_red-png", "20x20_green-png", "97461-png"));
  }

  @Test
  void testLoadAllImagesFromResourcesWithTifExtension() {
    this.sut.loadAllImagesFromResources("images", ".tif");
    assertThat(this.sut.getImageNames(), hasSize(2));
    assertThat(this.sut.getImageNames(), containsInAnyOrder("20x20_red-tif", "20x20_green-tif"));
  }

  //  @Test(timeout = 500)
  // FIXME Use mocked webserver for URL test
  // by morrigan on 03.11.2021
  void testLoadImageFromUrl() throws IOException {
    URL url = new URL("https://render.guildwars2.com/file/25B230711176AB5728E86F5FC5F0BFAE48B32F6E/97461.png");
    this.sut.loadImageFromUrl("97461-png", url);
    Optional<Image> image = this.sut.getImage("97461-png");
    assertThat(image, is(not(emptyOptional())));
    BufferedImage actualImage = (BufferedImage) image.get();
    BufferedImage expectedImage = ImageIO.read(getClass().getResourceAsStream("/images/97461.png"));
    ImageComparisonResult result = new ImageComparison(expectedImage, actualImage).compareImages();
    assertThat(result.getImageComparisonState(), is(equalTo(ImageComparisonState.MATCH)));
  }

  //  @Test(timeout = 500)
  // FIXME Use mocked webserver for URL test
  // by morrigan on 03.11.2021
  void testLoadImageFromMissingUrl() throws IOException {
    URL url = new URL("https://render.guildwars2.com/file/25B230711176AB5728E86F5FC5F0BFAE48B32F6E/9746.png");
    assertThrows(IOException.class, () -> this.sut.loadImageFromUrl("9746-png", url));
  }

  @Test
  void testGetImageWithNameEqualToNull() {
    Optional<Image> image = this.sut.getImage(null);
    assertThat(image, is(emptyOptional()));
  }

  @Test
  void testGetImageWithMissingImage() {
    Optional<Image> image = this.sut.getImage("missingimage");
    assertThat(image, is(emptyOptional()));
  }

  @Test
  void testGetImageWithDimensionEqualToNull() {
    this.sut.loadAllImagesFromResources("images/red");
    NullPointerException exception = assertThrows(NullPointerException.class,
        () -> this.sut.getImage("20x20_red-png", null));
    assertThat(exception.getMessage(), containsString("The scale dimension factor must not be null"));
  }

  @Test
  void testGetImageWithDimension() {
    this.sut.loadAllImagesFromResources("images/red");
    Optional<Image> image = this.sut.getImage("20x20_red-png", new Dimension(10, 10));
    assertThat(image, is(optionalWithValue()));
    assertThat((image.get()).getWidth(null), is(equalTo(10)));
    assertThat((image.get()).getHeight(null), is(equalTo(10)));
  }

  @Test
  void testGetImageWithInvalidWidthScale() {
    this.sut.loadAllImagesFromResources("images/red");
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> this.sut.getImage("20x20_red-png", 0, 20));
    assertThat(exception.getMessage(), containsString("width must be greater than 0"));
  }

  @Test
  void testGetImageWithInvalidHeightScale() {
    this.sut.loadAllImagesFromResources("images/red");
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> this.sut.getImage("20x20_red-png", 20, 0));
    assertThat(exception.getMessage(), containsString("height must be greater than 0"));
  }

  @Test
  void testGetImageWithScaleWidth() {
    this.sut.loadAllImagesFromResources("images/red");
    Optional<Image> image = this.sut.getImage("20x20_red-png", 10, 20);
    assertThat(image, is(optionalWithValue()));
    assertThat((image.get()).getWidth(null), is(equalTo(10)));
    assertThat((image.get()).getHeight(null), is(equalTo(20)));
  }

  @Test
  void testGetImageWithScaleHeight() {
    this.sut.loadAllImagesFromResources("images/red");
    Optional<Image> image = this.sut.getImage("20x20_red-png", 20, 10);
    assertThat(image, is(optionalWithValue()));
    assertThat((image.get()).getWidth(null), is(equalTo(20)));
    assertThat((image.get()).getHeight(null), is(equalTo(10)));
  }

  @Test
  void testGetImageWithScaleWidthAndHeight() {
    this.sut.loadAllImagesFromResources("images/red");
    Optional<Image> image = this.sut.getImage("20x20_red-png", 10, 10);
    assertThat(image, is(optionalWithValue()));
    assertThat((image.get()).getWidth(null), is(equalTo(10)));
    assertThat((image.get()).getHeight(null), is(equalTo(10)));
  }

  @Test
  void testGetImageIconWithNameEqualToNull() {
    Optional<ImageIcon> image = this.sut.getImageIcon(null);
    assertThat(image, is(emptyOptional()));
  }

  @Test
  void testGetImageIconWithMissingImage() {
    Optional<ImageIcon> image = this.sut.getImageIcon("missingimage");
    assertThat(image, is(emptyOptional()));
  }

  @Test
  void testGetImageIconWithDimension() {
    this.sut.loadAllImagesFromResources("images/red");
    Optional<ImageIcon> image = this.sut.getImageIcon("20x20_red-png", new Dimension(10, 10));
    assertThat(image, is(optionalWithValue()));
    assertThat((image.get()).getIconWidth(), is(equalTo(10)));
    assertThat((image.get()).getIconHeight(), is(equalTo(10)));
  }

  @Test
  void testGetImageIconWithInvalidWidthScale() {
    this.sut.loadAllImagesFromResources("images/red");
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> this.sut.getImageIcon("20x20_red-png", 0, 20));
    assertThat(exception.getMessage(), containsString("width must be greater than 0"));
  }

  @Test
  void testGetImageIconWithInvalidHeightScale() {
    this.sut.loadAllImagesFromResources("images/red");
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> this.sut.getImageIcon("20x20_red-png", 20, 0));
    assertThat(exception.getMessage(), containsString("height must be greater than 0"));
  }

  @Test
  void testGetImageIconWithInvalidDimension() {
    this.sut.loadAllImagesFromResources("images/red");
    NullPointerException exception = assertThrows(NullPointerException.class,
        () -> this.sut.getImageIcon("20x20_red-png", null));
    assertThat(exception.getMessage(), containsString("The scale dimension factor must not be null"));
  }

  @Test
  void testGetImageIconWithScaleWidth() {
    this.sut.loadAllImagesFromResources("images/red");
    Optional<ImageIcon> image = this.sut.getImageIcon("20x20_red-png", 10, 20);
    assertThat(image, is(optionalWithValue()));
    assertThat((image.get()).getIconWidth(), is(equalTo(10)));
    assertThat((image.get()).getIconHeight(), is(equalTo(20)));
  }

  @Test
  void testGetImageIconWithScaleHeight() {
    this.sut.loadAllImagesFromResources("images/red");
    Optional<ImageIcon> image = this.sut.getImageIcon("20x20_red-png", 20, 10);
    assertThat(image, is(optionalWithValue()));
    assertThat((image.get()).getIconWidth(), is(equalTo(20)));
    assertThat((image.get()).getIconHeight(), is(equalTo(10)));
  }

  @Test
  void testGetImageIconWithScaleWidthAndHeight() {
    this.sut.loadAllImagesFromResources("images/red");
    Optional<ImageIcon> image = this.sut.getImageIcon("20x20_red-png", 10, 10);
    assertThat(image, is(optionalWithValue()));
    assertThat((image.get()).getIconWidth(), is(equalTo(10)));
    assertThat((image.get()).getIconHeight(), is(equalTo(10)));
  }

  @Test
  void testClear() {
    this.sut.loadAllImagesFromResources("images");
    assertThat(this.sut.getImageNames(), is(not(empty())));
    this.sut.clear();
    assertThat(this.sut.getImageNames(), is(empty()));
  }
}
