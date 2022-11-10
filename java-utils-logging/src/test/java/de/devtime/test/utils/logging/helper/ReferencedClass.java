package de.devtime.test.utils.logging.helper;

import java.util.UUID;

import de.devtime.utils.logging.annotations.LoggingId;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReferencedClass {

  @LoggingId
  private String id;

  @LoggingId
  private String identityId;

  private String name;

  public ReferencedClass(String name) {
    this.id = UUID.randomUUID().toString();
    this.identityId = UUID.randomUUID().toString();
    this.name = name;
  }
}
