package de.devtime.utils.persistence;

import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PostPersist;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.domain.Persistable;

import de.devtime.utils.logging.CustomToStringStyle;
import lombok.Getter;

@Getter

@MappedSuperclass
public abstract class AbstractPersistable implements Persistable<String> {

  @Id
  @Column(name = "ID")
  private String id;

  @Transient
  private boolean isNew;

  protected AbstractPersistable() {
    this.isNew = true;
  }

  public void init() {
    this.id = UUID.randomUUID().toString();

    linkReferencedObjects();
  }

  protected abstract void linkReferencedObjects();

  @PostPersist
  private void postPersist() {
    this.isNew = false;
  }

  @Override
  public final int hashCode() {
    return Objects.hash(getId());
  }

  @Override
  public final boolean equals(Object obj) {
    if ((obj == null) || !EntityHelper.isClassEquals(this, obj)) {
      return false;
    }
    AbstractPersistable other = (AbstractPersistable) obj;
    return Objects.equals(getId(), other.getId());
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, CustomToStringStyle.PARAMETRIZED_STYLE);
  }
}
