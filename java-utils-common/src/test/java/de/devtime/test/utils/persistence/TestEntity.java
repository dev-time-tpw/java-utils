package de.devtime.test.utils.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import de.devtime.utils.persistence.AbstractPersistable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(setterPrefix = "with")

@Entity
@Table(name = "TestEntity")
public class TestEntity extends AbstractPersistable {

  @Column(name = "NAME", length = 256)
  private String name;

  @Override
  protected void linkReferencedObjects() {
    // nothing to do
  }
}
