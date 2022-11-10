package de.devtime.test.utils.persistence;

import java.util.HashSet;

import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;

public class HibernateProxyOfHashSet extends HashSet<Void> implements HibernateProxy {

  private static final long serialVersionUID = 1L;

  @Override
  public Object writeReplace() {
    return null;
  }

  @Override
  public LazyInitializer getHibernateLazyInitializer() {
    return null;
  }
}
