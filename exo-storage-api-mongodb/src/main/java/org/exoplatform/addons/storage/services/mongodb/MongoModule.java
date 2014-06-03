package org.exoplatform.addons.storage.services.mongodb;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import org.exoplatform.addons.storage.services.*;

public class MongoModule extends AbstractModule
{

  @Override
  protected void configure() {
      bind(ChatService.class).to(ChatServiceImpl.class);
      bind(NotificationService.class).to(NotificationServiceImpl.class);
      bind(TokenService.class).to(TokenServiceImpl.class);
      bind(UserService.class).to(UserServiceImpl.class);
      bind(StatisticsService.class).to(StatisticsServiceImpl.class).in(Scopes.SINGLETON);
  }
}
