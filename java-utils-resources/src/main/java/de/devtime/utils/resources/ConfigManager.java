package de.devtime.utils.resources;

import java.io.IOException;
import java.util.MissingResourceException;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.helpers.MessageFormatter;

import com.google.common.base.Preconditions;

/**
 * All configurations that are required for an application are managed here.
 *
 * @author morrigan
 * @since 0.0.1
 */
public class ConfigManager {

  /**
   * @return only instance of this manager
   * @since 0.0.1
   */
  public static ConfigManager getInstance() {
    return INSTANCE;
  }

  private static final ConfigManager INSTANCE = new ConfigManager();

  private Properties configs;
  private String configFilename;

  /* Private singleton constructor */
  private ConfigManager() {
    super();

    this.configs = new Properties();
  }

  /**
   * Searches for a configuration based on a key that was previously loaded and returns that configuration value. If no
   * value exists for the key, an empty optional value is returned.
   *
   * @param configKey a configuration key
   * @return a configuration value wrapped in an optional or an empty optional
   * @since 0.0.1
   */
  public Optional<String> getOptConfig(String configKey) {
    return getOptConfigInternal(configKey);
  }

  /**
   * Searches for a configuration based on a key that was previously loaded and returns that configuration value. If no
   * value exists for the key, an {@link MissingResourceException} is thrown.
   *
   * @param configKey a configuration key
   * @return a configuration value
   * @throws MissingResourceException if no value is available for the key
   * @since 0.0.1
   */
  public String getConfig(String configKey) {
    return getConfigInternal(configKey);
  }

  /**
   * Searches for a configuration based on a key that was previously loaded and returns that configuration value. You
   * can use placeholders in a configuration as described in {@link MessageFormatter}. All placeholder are replaced with
   * the given parameter. If no value exists for the key, an {@link MissingResourceException} is thrown.
   *
   * @param configKey a configuration key
   * @param parameters a set of parameters
   * @return a configuration value
   * @throws MissingResourceException if no value is available for the key
   * @since 0.0.1
   */
  public String getConfig(String configKey, Object... parameters) {
    return replacePlaceholder(getConfigInternal(configKey), parameters);
  }

  /**
   * Loads all configurations from the specified properties file. The file must be located on the classpath and is
   * specified with the .properties file extension.
   * <p>
   * <b>Example</b><br>
   *
   * <pre>
   * ConfigManager configs = ConfigManager.getInstance();
   * configs.loadAllConfigsFromResources("config.properties");
   * </pre>
   *
   * @param filename a filename/path to the property file <i>(not blank)</i>
   * @throws IOException if an error occurred when reading from config file.
   * @since 0.0.1
   */
  public void loadAllConfigsFromResources(String filename) throws IOException {
    Preconditions.checkArgument(!StringUtils.isBlank(filename), "The filename must not be blank!");
    this.configFilename = filename;
    if (filename.startsWith("/")) {
      this.configs.load(getClass().getResourceAsStream(filename));
    } else {
      this.configs.load(getClass().getResourceAsStream(StringUtils.join("/", filename)));
    }
  }

  /**
   * @return all keys that was previously loaded from a property file
   * @since 0.0.1
   */
  public Set<String> getConfigKeys() {
    return this.configs.stringPropertyNames();
  }

  /**
   * Clears all configuration entries from this manager.
   *
   * @since 0.0.1
   */
  public void clear() {
    this.configs.clear();
  }

  private String replacePlaceholder(String value, Object... parameters) {
    return MessageFormatter.arrayFormat(value, parameters).getMessage();
  }

  private String getConfigInternal(String configKey) {
    Optional<String> optConfigValue = getOptConfigInternal(configKey);
    if (optConfigValue.isPresent()) {
      return optConfigValue.get();
    } else {
      throw new MissingResourceException(MessageFormatter
          .arrayFormat("Requested configuration with the key {} is not present in the configuration file {}",
              new Object[] {
                  configKey, this.configFilename
              }).getMessage(), this.configFilename, configKey);
    }
  }

  private Optional<String> getOptConfigInternal(String configKey) {
    String value;
    if (configKey == null || (value = this.configs.getProperty(configKey)) == null) {
      return Optional.empty();
    }
    return Optional.of(value);
  }
}
