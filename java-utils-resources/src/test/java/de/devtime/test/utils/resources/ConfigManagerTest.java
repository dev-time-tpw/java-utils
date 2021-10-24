package de.devtime.test.utils.resources;

import static com.spotify.hamcrest.optional.OptionalMatchers.emptyOptional;
import static com.spotify.hamcrest.optional.OptionalMatchers.optionalWithValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThrows;

import java.io.IOException;
import java.util.MissingResourceException;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.devtime.utils.resources.ConfigManager;

public class ConfigManagerTest {

  private static final int ABSOLUTE_AMOUNT_OF_CONFIG_ITEMS = 2;

  private ConfigManager sut = ConfigManager.getInstance();

  @Before
  public void setup() {
    this.sut.clear();
  }

  @After
  public void tearDown() {
    this.sut.clear();
  }

  @Test
  public void testLoadConfigsWithFilenameEqualToNull() throws IOException {
    assertThrows(IllegalArgumentException.class, () -> this.sut.loadAllConfigsFromResources(null));
  }

  @Test
  public void testLoadConfigsWithEmptyFilename() throws IOException {
    assertThrows(IllegalArgumentException.class, () -> this.sut.loadAllConfigsFromResources(""));
  }

  @Test
  public void testLoadConfigsWithNullStream() throws IOException {
    assertThrows(IOException.class, () -> this.sut.loadAllConfigsFromResources("/null"));
  }

  @Test
  public void testLoadConfigsWithNullStream2() throws IOException {
    assertThrows(IOException.class, () -> this.sut.loadAllConfigsFromResources("null"));
  }

  @Test
  public void testLoadConfigsWithoutSlashPrefix() throws IOException {
    this.sut.loadAllConfigsFromResources("config.properties");
    assertThat(this.sut.getConfigKeys(), hasSize(ABSOLUTE_AMOUNT_OF_CONFIG_ITEMS));
  }

  @Test
  public void testLoadConfigsWithSlashPrefix() throws IOException {
    this.sut.loadAllConfigsFromResources("/config.properties");
    assertThat(this.sut.getConfigKeys(), hasSize(ABSOLUTE_AMOUNT_OF_CONFIG_ITEMS));
  }

  @Test
  public void testGetOptConfigWithNullKey() {
    Optional<String> config = this.sut.getOptConfig(null);
    assertThat(config, is(emptyOptional()));
  }

  @Test
  public void testGetOptConfigWithMissingKey() {
    Optional<String> config = this.sut.getOptConfig("missing");
    assertThat(config, is(emptyOptional()));
  }

  @Test
  public void testGetOptConfigWithExistingKey() throws IOException {
    this.sut.loadAllConfigsFromResources("config.properties");
    Optional<String> config = this.sut.getOptConfig("serverMode");
    assertThat(config, is(optionalWithValue()));
    assertThat(config.get(), is(equalTo("local")));
  }

  @Test
  public void testGetConfigWithNullKey() throws IOException {
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
  public void testGetConfigWithMissingKey() throws IOException {
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
  public void testGetConfigWithExistingKey() throws IOException {
    this.sut.loadAllConfigsFromResources("config.properties");
    String config = this.sut.getConfig("serverMode");
    assertThat(config, is(equalTo("local")));
  }

  @Test
  public void testGetConfigWithArguments() throws IOException {
    this.sut.loadAllConfigsFromResources("config.properties");
    String config = this.sut.getConfig("command", "/tmp");
    assertThat(config, is(equalTo("cd /tmp")));
  }

  @Test
  public void testGetConfigKeys() throws IOException {
    this.sut.loadAllConfigsFromResources("config.properties");
    assertThat(this.sut.getConfigKeys(), containsInAnyOrder("serverMode", "command"));
  }

  @Test
  public void testClear() throws IOException {
    this.sut.loadAllConfigsFromResources("config.properties");
    assertThat(this.sut.getConfigKeys(), hasSize(ABSOLUTE_AMOUNT_OF_CONFIG_ITEMS));
    this.sut.clear();
    assertThat(this.sut.getConfigKeys(), hasSize(0));
  }

}
