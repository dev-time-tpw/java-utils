package de.devtime.utils.resources;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.MessageFormatter;

/**
 * All labels and texts required for an application are managed here. Since applications are often offered in multiple
 * languages, this manager supports multilingualism using {@link ResourceBundle}.
 *
 * @author morrigan
 * @since 0.0.1
 */
public class LanguageManager {

  private static final Logger LOG = LoggerFactory.getLogger(LanguageManager.class);

  /**
   * @return only instance of this manager
   * @since 0.0.1
   */
  public static LanguageManager getInstance() {
    return INSTANCE;
  }

  private static final LanguageManager INSTANCE = new LanguageManager();
  private static final String COLON = ":";

  private enum Bundle {
    LABELS, MESSAGES, ERRORS
  }

  private Map<Locale, ResourceBundle> labels;
  private Map<Locale, ResourceBundle> messages;
  private Map<Locale, ResourceBundle> errors;

  /* Private singleton constructor */
  private LanguageManager() {
    super();
    this.labels = new HashMap<>();
    this.messages = new HashMap<>();
    this.errors = new HashMap<>();
  }

  /**
   * Determines a corresponding label for a label key and returns it. The label is returned in the language that is
   * stored as default ({@link Locale#getDefault()}) in the JVM.
   *
   * @param key a label key
   * @return a label or an empty string if there is no label for the key
   * @since 0.0.1
   */
  public String getLabel(String key) {
    return getValue(Bundle.LABELS, key, Locale.getDefault());
  }

  /**
   * Determines a corresponding label for a label key and returns it with a colon at the end. The label is returned in
   * the language that is stored as default ({@link Locale#getDefault()}) in the JVM.
   *
   * @param key a label key
   * @return a label or an empty string if there is no label for the key
   * @since 0.0.1
   */
  public String getLabelWithColon(String key) {
    String value = getValue(Bundle.LABELS, key, Locale.getDefault());
    return StringUtils.isBlank(value) ? value : StringUtils.join(value, COLON);
  }

  /**
   * Determines a corresponding label for a label key and returns it in the given language.
   *
   * @param key a label key
   * @param locale a language
   * @return a label or an empty string if there is no label for the key
   * @since 0.0.1
   */
  public String getLabel(String key, Locale locale) {
    return getValue(Bundle.LABELS, key, locale);
  }

  /**
   * Determines a corresponding label for a label key and returns it with a colon at the end and in the given language.
   *
   * @param key a label key
   * @param locale a language
   * @return a label or an empty string if there is no label for the key
   * @since 0.0.1
   */
  public String getLabelWithColon(String key, Locale locale) {
    String value = getValue(Bundle.LABELS, key, locale);
    return StringUtils.isBlank(value) ? value : StringUtils.join(value, COLON);
  }

  /**
   * Determines a corresponding message for a message key, replaces existing placeholder with the given parameter and
   * returns it in the default language ({@link Locale#getDefault()}).<br>
   * For more information about placeholder and how to use them see {@link MessageFormatter}
   *
   * @param key a message key
   * @param parameters a set of parameters
   * @return a message or an empty string if there is no message for the key
   * @since 0.0.1
   */
  public String getMessage(String key, Object... parameters) {
    return replacePlaceholder(getValue(Bundle.MESSAGES, key, Locale.getDefault()), parameters);
  }

  /**
   * Determines a corresponding message for a message key, replaces existing placeholder with the given parameter and
   * returns it in the given language.<br>
   * For more information about placeholder and how to use them see {@link MessageFormatter}
   *
   * @param key a message key
   * @param locale a language
   * @param parameters a set of parameters
   * @return a message or an empty string if there is no message for the key
   * @since 0.0.1
   */
  public String getMessage(String key, Locale locale, Object... parameters) {
    return replacePlaceholder(getValue(Bundle.MESSAGES, key, locale), parameters);
  }

  /**
   * Determines a corresponding error message for a error key, replaces existing placeholder with the given parameter
   * and returns it in the default language ({@link Locale#getDefault()}).<br>
   * For more information about placeholder and how to use them see {@link MessageFormatter}
   *
   * @param key a error key
   * @param parameters a set of parameters
   * @return an error message or an empty string if there is no error message for the key
   * @since 0.0.1
   */
  public String getError(String key, Object... parameters) {
    return replacePlaceholder(getValue(Bundle.ERRORS, key, Locale.getDefault()), parameters);
  }

  /**
   * Determines a corresponding error message for a error key, replaces existing placeholder with the given parameter
   * and returns it in the given language.<br>
   * For more information about placeholder and how to use them see {@link MessageFormatter}
   *
   * @param key a message key
   * @param locale a language
   * @param parameters a set of parameters
   * @return an error message or an empty string if there is no error message for the key
   * @since 0.0.1
   */
  public String getError(String key, Locale locale, Object... parameters) {
    return replacePlaceholder(getValue(Bundle.ERRORS, key, locale), parameters);
  }

  /**
   * Loads all labels from the specified resource and makes them available in this manager. A resource for the current
   * default language ({@link Locale#getDefault()}) is searched for. If it is not found, it tries to load a default
   * resource.<br>
   * Only resources on the classpath are searched.
   *
   * @param baseName name of a resource with labels
   * @throws MissingResourceException if the resource cannot be found
   * @see ResourceBundle#getBundle(String, Locale)
   * @since 0.0.1
   */
  public void loadLabelsFromResources(String baseName) {
    loadLabelsFromResources(Bundle.LABELS, baseName, Optional.empty());
  }

  /**
   * Loads all labels from the specified resource and makes them available in this manager. A resource for the given
   * language is searched for. If it is not found, it tries to load a default resource.<br>
   * Only resource on the classpath are searched.
   *
   * @param baseName name of a resource with labels
   * @param locale a language
   * @throws MissingResourceException if the resource cannot be found
   * @see ResourceBundle#getBundle(String, Locale)
   * @since 0.0.1
   */
  public void loadLabelsFromResources(String baseName, Locale locale) {
    loadLabelsFromResources(Bundle.LABELS, baseName, Optional.of(locale));
  }

  /**
   * Loads all messages from the specified resource and makes them available in this manager. A resource for the current
   * default language ({@link Locale#getDefault()}) is searched for. If it is not found, it tries to load a default
   * resource.<br>
   * Only resources on the classpath are searched.
   *
   * @param baseName name of a resource with messages
   * @throws MissingResourceException if the resource cannot be found
   * @see ResourceBundle#getBundle(String, Locale)
   * @since 0.0.1
   */
  public void loadMessagesFromResources(String baseName) {
    loadLabelsFromResources(Bundle.MESSAGES, baseName, Optional.empty());
  }

  /**
   * Loads all messages from the specified resource and makes them available in this manager. A resource for the given
   * language is searched for. If it is not found, it tries to load a default resource.<br>
   * Only resource on the classpath are searched.
   *
   * @param baseName name of a resource with messages
   * @param locale a language
   * @throws MissingResourceException if the resource cannot be found
   * @see ResourceBundle#getBundle(String, Locale)
   * @since 0.0.1
   */
  public void loadMessagesFromResources(String baseName, Locale locale) {
    loadLabelsFromResources(Bundle.MESSAGES, baseName, Optional.of(locale));
  }

  /**
   * Loads all error messages from the specified resource and makes them available in this manager. A resource for the
   * current default language ({@link Locale#getDefault()}) is searched for. If it is not found, it tries to load a
   * default resource.<br>
   * Only resources on the classpath are searched.
   *
   * @param baseName name of a resource with error messages
   * @throws MissingResourceException if the resource cannot be found
   * @see ResourceBundle#getBundle(String, Locale)
   * @since 0.0.1
   */
  public void loadErrorsFromResources(String baseName) {
    loadLabelsFromResources(Bundle.ERRORS, baseName, Optional.empty());
  }

  /**
   * Loads all error messages from the specified resource and makes them available in this manager. A resource for the
   * given language is searched for. If it is not found, it tries to load a default resource.<br>
   * Only resource on the classpath are searched.
   *
   * @param baseName name of a resource with error messages
   * @param locale a language
   * @throws MissingResourceException if the resource cannot be found
   * @see ResourceBundle#getBundle(String, Locale)
   * @since 0.0.1
   */
  public void loadErrorsFromResources(String baseName, Locale locale) {
    loadLabelsFromResources(Bundle.ERRORS, baseName, Optional.of(locale));
  }

  /**
   * @return all keys from all labels that was previously loaded.
   * @since 0.0.1
   */
  public Set<String> getLabelKeys() {
    return getLabelKeys(Locale.getDefault());
  }

  /**
   * @param locale a language
   * @return all keys to all labels of the given language
   * @since 0.0.1
   */
  public Set<String> getLabelKeys(Locale locale) {
    return toSet(getResourceBundle(Bundle.LABELS, locale));
  }

  /**
   * @return all keys from all messages that was previously loaded.
   * @since 0.0.1
   */
  public Set<String> getMessageKeys() {
    return getMessageKeys(Locale.getDefault());
  }

  /**
   * @param locale a language
   * @return all keys to all messages of the given language
   * @since 0.0.1
   */
  public Set<String> getMessageKeys(Locale locale) {
    return toSet(getResourceBundle(Bundle.MESSAGES, locale));
  }

  /**
   * @return all keys from all error messages that was previously loaded.
   * @since 0.0.1
   */
  public Set<String> getErrorKeys() {
    return getErrorKeys(Locale.getDefault());
  }

  /**
   * @param locale a language
   * @return all keys to all error messages of the given language
   * @since 0.0.1
   */
  public Set<String> getErrorKeys(Locale locale) {
    return toSet(getResourceBundle(Bundle.ERRORS, locale));
  }

  /**
   * Clears all labels, messages and error messages from this manager.
   *
   * @since 0.0.1
   */
  public void clear() {
    this.labels = new HashMap<>();
    this.messages = new HashMap<>();
    this.errors = new HashMap<>();
  }

  private Set<String> toSet(ResourceBundle bundle) {
    Set<String> result = new HashSet<>();
    Enumeration<String> keys = bundle.getKeys();
    while (keys.hasMoreElements()) {
      result.add(keys.nextElement());
    }
    return result;
  }

  private String replacePlaceholder(String value, Object... parameters) {
    return MessageFormatter.arrayFormat(value, parameters).getMessage();
  }

  private String getValue(Bundle bundleName, String key, Locale locale) {
    String result = "";
    try {
      if (!StringUtils.isBlank(key)) {
        ResourceBundle resourceBundle = getResourceBundle(bundleName, locale);
        result = resourceBundle.getString(key);
      }
    } catch (MissingResourceException e) {
      LOG.warn("No value found for the key '{}' in the resource bundle '{}'. Details: {}", key, bundleName.name(),
          e.getMessage());
    }
    return result;
  }

  private ResourceBundle getResourceBundle(Bundle bundle, Locale locale) {
    ResourceBundle resourceBundle;
    if (locale == null) {
      locale = Locale.getDefault();
    }
    switch (bundle) {
      case LABELS:
        resourceBundle = this.labels.get(locale);
      break;
      case MESSAGES:
        resourceBundle = this.messages.get(locale);
      break;
      case ERRORS:
        resourceBundle = this.errors.get(locale);
      break;

      default:
        throw new IllegalStateException("Missing mapping for bundle: " + bundle);
    }
    if (resourceBundle == null) {
      throw new MissingResourceException("No resource bundle available for the language " + locale.getDisplayLanguage()
          + ". Please use a load method for your language before receiving a value.", bundle.name(), "");
    }
    return resourceBundle;
  }

  private void loadLabelsFromResources(Bundle bundle, String baseName, Optional<Locale> optLocale) {
    Locale locale;
    if (optLocale.isPresent()) {
      locale = optLocale.get();
    } else {
      locale = Locale.getDefault();
    }
    switch (bundle) {
      case LABELS:
        this.labels.put(locale, ResourceBundle.getBundle(baseName, locale));
      break;
      case MESSAGES:
        this.messages.put(locale, ResourceBundle.getBundle(baseName, locale));
      break;
      case ERRORS:
        this.errors.put(locale, ResourceBundle.getBundle(baseName, locale));
      break;

      default:
        throw new IllegalStateException("Missing mapping for bundle: " + bundle);
    }
  }
}
