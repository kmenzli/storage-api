package org.exoplatform.addons.persistence.services.jcr;

import com.google.inject.AbstractModule;
import org.exoplatform.addons.persistence.services.ChatService;
import org.exoplatform.addons.persistence.services.NotificationService;
import org.exoplatform.addons.persistence.services.TokenService;
import org.exoplatform.addons.persistence.services.UserService;

public class JCRModule extends AbstractModule
{

  @Override
  protected void configure() {
    bind(ChatService.class).to(ChatServiceImpl.class);
    bind(UserService.class).to(UserServiceImpl.class);
    bind(NotificationService.class).to(NotificationServiceImpl.class);
    bind(TokenService.class).to(TokenServiceImpl.class);
  }
}
