package de.devtime.utils.persistence;

import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EntityEventLogger {

  @PostPersist
  private void created(AbstractPersistable entity) {
    LOG.info("Entity was created: {}", entity);
  }

  @PostUpdate
  private void updated(AbstractPersistable entity) {
    LOG.info("Entity was updated: {}", entity);
  }

  @PostRemove
  private void removed(AbstractPersistable entity) {
    LOG.info("Entity was removed: {}", entity);
  }

  @PostLoad
  private void loaded(AbstractPersistable entity) {
    LOG.info("Entity was loaded: {}", entity);
  }
}
