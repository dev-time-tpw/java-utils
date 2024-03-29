package de.devtime.test.utils.resources;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.devtime.utils.resources.LanguageManager;

class LanguageManagerTest {

  private LanguageManager sut = LanguageManager.getInstance();

  @BeforeEach
  void setup() {
    ResourceBundle.clearCache();
    this.sut.clear();
    Locale.setDefault(Locale.GERMANY);
  }

  @Test
  void testLoadLabelsFromResource() {
    this.sut.loadLabelsFromResources("language/lang");
    assertThat(this.sut.getLabelKeys(), hasSize(3));
  }

  @Test
  void testLoadMessagesFromResource() {
    this.sut.loadMessagesFromResources("language/messages");
    assertThat(this.sut.getMessageKeys(), hasSize(2));
  }

  @Test
  void testLoadErrorsFromResource() {
    this.sut.loadErrorsFromResources("language/errors");
    assertThat(this.sut.getErrorKeys(), hasSize(2));
  }

  @Test
  void testLoadLabelsFromResourceWithBasenameNull() {
    assertThrows(NullPointerException.class, () ->  this.sut.loadLabelsFromResources(null));
  }

  @Test
  void testLoadLabelsFromResourceWithLocalGerman() {
    this.sut.loadLabelsFromResources("language/labels", Locale.GERMANY);
    assertThat(this.sut.getLabelKeys(Locale.GERMANY), hasSize(1));
  }

  @Test
  void testLoadMessagesFromResourceWithLocalFrance() {
    this.sut.loadMessagesFromResources("language/messages", Locale.FRANCE);
    assertThat(this.sut.getMessageKeys(Locale.FRANCE), hasSize(3));
  }

  @Test
  void testLoadErrorsFromResourceWithLocalUK() {
    this.sut.loadErrorsFromResources("language/errors", Locale.UK);
    assertThat(this.sut.getErrorKeys(Locale.UK), hasSize(3));
  }

  @Test
  void testLoadLabelsFromResourceWithLocalNull() {
    assertThrows(NullPointerException.class, () -> {
      this.sut.loadLabelsFromResources("language/labels", null);
    });
  }

  @Test
  void testLoadLabelsFromResourceWithMissingBaseName() {
    MissingResourceException exception = assertThrows(MissingResourceException.class, () -> {
      this.sut.loadLabelsFromResources("language/missing", Locale.GERMANY);
    });
    assertThat(exception.getMessage(), containsString("language/missing"));
  }

  @Test
  void testGetLabelWithExistingKey() {
    this.sut.loadLabelsFromResources("language/labels");
    assertThat(this.sut.getLabel("helloWorld"), is(equalTo("Hallo Welt!")));
  }

  @Test
  void testGetLabelWithColon() {
    this.sut.loadLabelsFromResources("language/labels");
    assertThat(this.sut.getLabelWithColon("helloWorld"), is(equalTo("Hallo Welt!:")));
  }

  @Test
  void testGetLabelWithColonButEmptyValue() {
    this.sut.loadLabelsFromResources("language/labels");
    assertThat(this.sut.getLabelWithColon("missing"), is(equalTo("")));
  }

  @Test
  void testGetLabelWithExistingKeyAndLocaleFR() {
    this.sut.loadLabelsFromResources("language/labels", Locale.FRANCE);
    assertThat(this.sut.getLabel("helloWorld", Locale.FRANCE), is(equalTo("Bonjour le monde!")));
  }

  @Test
  void testGetLabelWithColonAndKeyAndLocaleFR() {
    this.sut.loadLabelsFromResources("language/labels", Locale.FRANCE);
    assertThat(this.sut.getLabelWithColon("helloWorld", Locale.FRANCE), is(equalTo("Bonjour le monde!:")));
  }

  @Test
  void testGetLabelWithColonAndKeyAndLocaleFRButEmptyValue() {
    this.sut.loadLabelsFromResources("language/labels", Locale.FRANCE);
    assertThat(this.sut.getLabelWithColon("missing", Locale.FRANCE), is(equalTo("")));
  }

  @Test
  void testGetMessageWithExistingKey() {
    this.sut.loadMessagesFromResources("language/messages");
    assertThat(this.sut.getMessage("helloMsg"), is(equalTo("Hallo und herzlich Willkommen!")));
  }

  @Test
  void testGetMessageWithKeyAndParameters() {
    this.sut.loadMessagesFromResources("language/messages");
    assertThat(this.sut.getMessage("helloMsgWithParam", "Tom"), is(equalTo("Hallo und herzlich Willkommen Tom!")));
  }

  @Test
  void testGetMessageWithExistingKeyAndLocaleFR() {
    this.sut.loadMessagesFromResources("language/messages", Locale.FRANCE);
    assertThat(this.sut.getMessage("helloMsg", Locale.FRANCE), is(equalTo("Bonjour et bienvenue!")));
  }

  @Test
  void testGetMessageWithKeyAndLocaleFRAndParameters() {
    this.sut.loadMessagesFromResources("language/messages", Locale.FRANCE);
    assertThat(this.sut.getMessage("helloMsgWithParams", Locale.FRANCE, "Tom", "Jerry"),
        is(equalTo("Bonjour et bienvenue Tom and Jerry!")));
  }

  @Test
  void testGetErrorWithExistingKey() {
    this.sut.loadErrorsFromResources("language/errors");
    assertThat(this.sut.getError("E0001"), is(equalTo("Unbekannter Fehler")));
  }

  @Test
  void testGetErrorWithKeyAndParameters() {
    this.sut.loadErrorsFromResources("language/errors");
    assertThat(this.sut.getError("E0002", NullPointerException.class),
        is(equalTo("Es ist ein interner Fehler aufgetreten. Details: class java.lang.NullPointerException")));
  }

  @Test
  void testGetErrorWithExistingKeyAndLocaleUK() {
    this.sut.loadErrorsFromResources("language/errors", Locale.UK);
    assertThat(this.sut.getError("E0001", Locale.UK), is(equalTo("Unknown error")));
  }

  @Test
  void testGetErrorWithKeyAndLocaleUKAndParameters() {
    this.sut.loadErrorsFromResources("language/errors", Locale.UK);
    assertThat(this.sut.getError("E0003", Locale.UK, "test.txt", 7),
        is(equalTo("File comparison failed for file 'test.txt' in line '7'.")));
  }

  @Test
  void testGetLabelWithNullKey() {
    this.sut.loadLabelsFromResources("language/labels");
    assertThat(this.sut.getLabel(null), is(equalTo("")));
  }

  @Test
  void testGetLabelWithNullLocale() {
    this.sut.loadLabelsFromResources("language/labels");
    assertThat(this.sut.getLabel("helloWorld", null), is(equalTo("Hallo Welt!")));
  }

  @Test
  void testGetLabelWithMissedLoadingLocaleFR() {
    this.sut.loadLabelsFromResources("language/labels");
    assertThat(this.sut.getLabel("helloWorld", Locale.FRANCE), is(equalTo("")));
    // TODO Logger warning prüfen.
    // by morrigan on 14.09.2021
  }

  @Test
  void testGetLabelWithNotExistingKey() {
    this.sut.loadLabelsFromResources("language/labels");
    assertThat(this.sut.getLabel("hello"), is(equalTo("")));
    // TODO Logger warning prüfen.
    // by morrigan on 14.09.2021
  }

  @Test
  void testClear() {
    this.sut.loadLabelsFromResources("language/labels", Locale.GERMANY);
    assertThat(this.sut.getLabelKeys(Locale.GERMANY), hasSize(1));
    this.sut.loadMessagesFromResources("language/messages", Locale.FRANCE);
    assertThat(this.sut.getMessageKeys(Locale.FRANCE), hasSize(3));
    this.sut.loadErrorsFromResources("language/errors", Locale.UK);
    assertThat(this.sut.getErrorKeys(Locale.UK), hasSize(3));

    this.sut.clear();

    assertThrows(MissingResourceException.class, () -> this.sut.getLabelKeys(Locale.GERMANY));
    assertThrows(MissingResourceException.class, () -> this.sut.getLabelKeys(Locale.FRANCE));
    assertThrows(MissingResourceException.class, () -> this.sut.getLabelKeys(Locale.UK));
  }

}
