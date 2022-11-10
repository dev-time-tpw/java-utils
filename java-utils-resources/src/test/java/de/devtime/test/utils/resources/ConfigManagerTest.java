package de.devtime.test.utils.resources;

import static com.spotify.hamcrest.optional.OptionalMatchers.emptyOptional;
import static com.spotify.hamcrest.optional.OptionalMatchers.optionalWithValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.MissingResourceException;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.devtime.utils.resources.ConfigManager;

class ConfigManagerTest {

  private static final int ABSOLUTE_AMOUNT_OF_CONFIG_ITEMS = 2;

  private ConfigManager sut = ConfigManager.getInstance();

  @BeforeEach
  public void setup() {
    this.sut.clear();
  }

  @AfterEach
  public void tearDown() {
    this.sut.clear();
  }

  @Test
  void testLoadConfigsWithFilenameEqualToNull() throws IOException {
    assertThrows(IllegalArgumentException.class, () -> this.sut.loadAllConfigsFromResources(null));
  }

  @Test
  void testLoadConfigsWithEmptyFilename() throws IOException {
    assertThrows(IllegalArgumentException.class, () -> this.sut.loadAllConfigsFromResources(""));
  }

  @Test
  void testLoadConfigsWithNullStream() throws IOException {
    assertThrows(IOException.class, () -> this.sut.loadAllConfigsFromResources("/null"));
  }

  @Test
  void testLoadConfigsWithNullStream2() throws IOException {
    assertThrows(IOException.class, () -> this.sut.loadAllConfigsFromResources("null"));
  }

  @Test
  void testLoadConfigsWithoutSlashPrefix() throws IOException {
    this.sut.loadAllConfigsFromResources("config.properties");
    assertThat(this.sut.getConfigKeys(), hasSize(ABSOLUTE_AMOUNT_OF_CONFIG_ITEMS));
  }

  @Test
  void testLoadConfigsWithSlashPrefix() throws IOException {
    this.sut.loadAllConfigsFromResources("/config.properties");
    assertThat(this.sut.getConfigKeys(), hasSize(ABSOLUTE_AMOUNT_OF_CONFIG_ITEMS));
  }

  @Test
  void testGetOptConfigWithNullKey() {
    Optional<String> config = this.sut.getOptConfig(null);
    assertThat(config, is(emptyOptional()));
  }

  @Test
  void testGetOptConfigWithMissingKey() {
    Optional<String> config = this.sut.getOptConfig("missing");
    assertThat(config, is(emptyOptional()));
  }

  @Test
  void testGetOptConfigWithExistingKey() throws IOException {
    this.sut.loadAllConfigsFromResources("config.properties");
    Optional<String> config = this.sut.getOptConfig("serverMode");
    assertThat(config, is(optionalWithValue()));
    assertThat(config.get(), is(equalTo("local")));
  }

  @Test
  void testGetConfigWithNullKey() throws IOException {
    this.sut.loadAllConfigsFromResources("config.properties");
    MissingResourceException exception = assertThrows(MissingResourceException.class,
        () -> this.sut.getConfig(null));
    assertThat(exception.getMessage(), containsString("null"));
    assertThat(exception.getMessage(), containsString("is not present"));
    assertThat(exception.getMessage(), containsString("config.properties"));
    assertThat(exception.getClassName(), containsString("config.properties"));
    assertThat(exception.getKey(), is(equalTo(null)));
  }

  @Test
  void testGetConfigWithMissingKey() throws IOException {
    this.sut.loadAllConfigsFromResources("config.properties");
    MissingResourceException exception = assertThrows(MissingResourceException.class,
        () -> this.sut.getConfig("missing"));
    assertThat(exception.getMessage(), containsString("missing"));
    assertThat(exception.getMessage(), containsString("is not present"));
    assertThat(exception.getMessage(), containsString("config.properties"));
    assertThat(exception.getClassName(), containsString("config.properties"));
    assertThat(exception.getKey(), containsString("missing"));
  }

  @Test
  void testGetConfigWithExistingKey() throws IOException {
    this.sut.loadAllConfigsFromResources("config.properties");
    String config = this.sut.getConfig("serverMode");
    assertThat(config, is(equalTo("local")));
  }

  @Test
  void testGetConfigWithArguments() throws IOException {
    this.sut.loadAllConfigsFromResources("config.properties");
    String config = this.sut.getConfig("command", "/tmp");
    assertThat(config, is(equalTo("cd /tmp")));
  }

  @Test
  void testGetConfigKeys() throws IOException {
    this.sut.loadAllConfigsFromResources("config.properties");
    assertThat(this.sut.getConfigKeys(), containsInAnyOrder("serverMode", "command"));
  }

  @Test
  void testClear() throws IOException {
    this.sut.loadAllConfigsFromResources("config.properties");
    assertThat(this.sut.getConfigKeys(), hasSize(ABSOLUTE_AMOUNT_OF_CONFIG_ITEMS));
    this.sut.clear();
    assertThat(this.sut.getConfigKeys(), hasSize(0));
  }
}
