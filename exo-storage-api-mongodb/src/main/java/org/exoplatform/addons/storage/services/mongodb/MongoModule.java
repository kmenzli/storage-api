package org.exoplatform.addons.storage.services.mongodb;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import org.exoplatform.addons.storage.services.*;

public class MongoModule extends AbstractModule
{

  @Override
  protected void configure() {
      bind(StatisticsService.class).to(StatisticsServiceImpl.class).in(Scopes.SINGLETON);
  }
}
