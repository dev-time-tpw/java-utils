package de.devtime.test.utils.resources;

import static com.spotify.hamcrest.optional.OptionalMatchers.emptyOptional;
import static com.spotify.hamcrest.optional.OptionalMatchers.optionalWithValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import java.awt.Font;
import java.awt.geom.AffineTransform;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.devtime.utils.resources.FontManager;

class FontManagerTest {

  private FontManager sut = FontManager.getInstance();

  @BeforeEach
  public void setup() {
    this.sut.clear();
  }

  @AfterEach
  public void tearDown() {
    this.sut.clear();
  }

  @Test
  void testLoadAllFontsFromResources() {
    this.sut.loadAllFontsFromResources();

    Optional<Font> cronosRegular = this.sut.getFont("cronos-pro-regular");
    assertThat(cronosRegular, is(optionalWithValue()));
    assertThat(cronosRegular.get().getFontName(), is(equalTo("CronosPro-Regular")));

    Optional<Font> cronosItalic = this.sut.getFont("cronos-pro-italic");
    assertThat(cronosItalic, is(optionalWithValue()));
    assertThat(cronosItalic.get().getFontName(), is(equalTo("CronosPro-Italic")));

    assertThat(this.sut.getFontNames(), hasSize(4));
    assertThat(this.sut.getFontNames(),
        containsInAnyOrder("cronos-pro-regular", "cronos-pro-italic", "menomonia", "menomonia-italic"));
  }

  @Test
  void testLoadAllFontsFromResourcesWithDirEqualsToNull() {
    this.sut.loadAllFontsFromResources(null);
    assertThat(this.sut.getFontNames(), hasSize(4));
  }

  @Test
  void testLoadAllFontsFromResourcesInFontDir() {
    this.sut.loadAllFontsFromResources("font");

    Optional<Font> cronosRegular = this.sut.getFont("cronos-pro-regular");
    assertThat(cronosRegular, is(optionalWithValue()));
    assertThat(cronosRegular.get().getFontName(), is(equalTo("CronosPro-Regular")));

    Optional<Font> cronosItalic = this.sut.getFont("cronos-pro-italic");
    assertThat(cronosItalic, is(optionalWithValue()));
    assertThat(cronosItalic.get().getFontName(), is(equalTo("CronosPro-Italic")));

    assertThat(this.sut.getFontNames(), hasSize(4));
    assertThat(this.sut.getFontNames(),
        containsInAnyOrder("cronos-pro-regular", "cronos-pro-italic", "menomonia", "menomonia-italic"));
  }

  @Test
  void testLoadAllFontsFromResourcesInSpecialDir() {
    this.sut.loadAllFontsFromResources("font/special");

    Optional<Font> menomonia = this.sut.getFont("menomonia");
    assertThat(menomonia, is(optionalWithValue()));

    Optional<Font> menomoniaItalic = this.sut.getFont("menomonia-italic");
    assertThat(menomoniaItalic, is(optionalWithValue()));

    assertThat(this.sut.getFontNames(), hasSize(2));
    assertThat(this.sut.getFontNames(), containsInAnyOrder("menomonia", "menomonia-italic"));
  }

  @Test
  void testLoadAllFontsFromResourceInUpperDir() {
    this.sut.loadAllFontsFromResources("font/upper");

    assertThat(this.sut.getFontNames(), hasSize(2));
    assertThat(this.sut.getFontNames(), containsInAnyOrder("menomonia", "menomonia-italic"));
  }

  @Test
  void testLoadAllFontsFromResourcesWithDirectoryTypeAndFileEstension() {
    this.sut.loadAllFontsFromResources("font/special", Font.TRUETYPE_FONT, ".ttf");

    Optional<Font> menomonia = this.sut.getFont("menomonia");
    assertThat(menomonia, is(optionalWithValue()));

    Optional<Font> menomoniaItalic = this.sut.getFont("menomonia-italic");
    assertThat(menomoniaItalic, is(optionalWithValue()));

    assertThat(this.sut.getFontNames(), hasSize(2));
    assertThat(this.sut.getFontNames(), containsInAnyOrder("menomonia", "menomonia-italic"));
  }

  @Test
  void testGetFontWithFontnameEqualToNull() {
    Optional<Font> font = this.sut.getFont(null);
    assertThat(font, is(emptyOptional()));
  }

  @Test
  void testGetFontWithMissingFont() {
    Optional<Font> font = this.sut.getFont("missingfont");
    assertThat(font, is(emptyOptional()));
  }

  @Test
  void testGetFontWithGivenSize() {
    this.sut.loadAllFontsFromResources("font/special");
    Optional<Font> font = this.sut.getFont("menomonia", 26f);
    assertThat(font, is(optionalWithValue()));
    assertThat(font.get().getSize(), is(equalTo(26)));
  }

  @Test
  void testGetFontWithGivenStyle() {
    this.sut.loadAllFontsFromResources("font/special");
    Optional<Font> font = this.sut.getFont("menomonia", Font.BOLD);
    assertThat(font, is(optionalWithValue()));
    assertThat(font.get().isBold(), is(equalTo(true)));
  }

  @Test
  void testGetFontWithGivenTransformation() {
    this.sut.loadAllFontsFromResources("font/special");
    AffineTransform affineTransform = new AffineTransform();
    affineTransform.rotate(Math.PI / 2);
    Optional<Font> font = this.sut.getFont("menomonia", affineTransform);
    assertThat(font, is(optionalWithValue()));
    assertThat(font.get().getTransform(), is(equalTo(affineTransform)));
  }

  @Test
  void testGetFontWithGivenSizeAndStyle() {
    this.sut.loadAllFontsFromResources("font/special");
    Optional<Font> font = this.sut.getFont("menomonia", 26f, Font.BOLD);
    assertThat(font, is(optionalWithValue()));
    assertThat(font.get().getSize(), is(equalTo(26)));
    assertThat(font.get().isBold(), is(equalTo(true)));
  }

  @Test
  void testGetFontWithGivenStyleAndTransformation() {
    this.sut.loadAllFontsFromResources("font/special");
    AffineTransform affineTransform = new AffineTransform();
    affineTransform.rotate(Math.PI / 2);
    Optional<Font> font = this.sut.getFont("menomonia", Font.BOLD, affineTransform);
    assertThat(font, is(optionalWithValue()));
    assertThat(font.get().isBold(), is(equalTo(true)));
    assertThat(font.get().getTransform(), is(equalTo(affineTransform)));
  }

  @Test
  void testGetFontWithGivenSizeAndTransformation() {
    this.sut.loadAllFontsFromResources("font/special");
    AffineTransform affineTransform = new AffineTransform();
    affineTransform.rotate(Math.PI / 2);
    Optional<Font> font = this.sut.getFont("menomonia", 26f, affineTransform);
    assertThat(font, is(optionalWithValue()));
    assertThat(font.get().getSize(), is(equalTo(26)));
    assertThat(font.get().getTransform(), is(equalTo(affineTransform)));
  }

  @Test
  void testGetFontWithGivenSizeAndStyleAndTransformation() {
    this.sut.loadAllFontsFromResources("font/special");
    AffineTransform affineTransform = new AffineTransform();
    affineTransform.rotate(Math.PI / 2);
    Optional<Font> font = this.sut.getFont("menomonia", 26f, Font.BOLD, affineTransform);
    assertThat(font, is(optionalWithValue()));
    assertThat(font.get().getSize(), is(equalTo(26)));
    assertThat(font.get().isBold(), is(equalTo(true)));
    assertThat(font.get().getTransform(), is(equalTo(affineTransform)));
  }

  @Test
  void testClear() {
    this.sut.loadAllFontsFromResources("font");
    assertThat(this.sut.getFontNames(), is(not(empty())));
    this.sut.clear();
    assertThat(this.sut.getFontNames(), is(empty()));
  }
}
