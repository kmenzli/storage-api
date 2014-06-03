package org.exoplatform.addons.persistence.listener;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.exoplatform.addons.persistence.utils.PropertyManager;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.logging.Logger;

@WebListener
public class GuiceManager implements ServletContextListener
{

  private static Logger log = Logger.getLogger("GuiceManager");

  private static Injector injector_;

  @Override
  public void contextInitialized(ServletContextEvent servletContextEvent)
  {
    log.info("INITIALIZING GUICE");
    GuiceManager.forceNew();

  }

  @Override
  public void contextDestroyed(ServletContextEvent servletContextEvent)
  {
    log.info("CLOSING GUICE");
  }

  public static Injector getInstance()
  {
    return injector_;
  }

  public static void forceNew() {
      try {
            if (injector_==null)
            {
              if (PropertyManager.PROPERTY_SERVICE_IMPL_MONGO.equals(PropertyManager.getProperty(PropertyManager.PROPERTY_SERVICES_IMPLEMENTATION)))
              {
                injector_ = Guice.createInjector((AbstractModule)Class.forName("org.exoplatform.addons.persistence.services.mongodb.MongoModule").newInstance());
              }
              else
              {
                injector_ = Guice.createInjector((AbstractModule)Class.forName("org.exoplatform.addons.persistence.services.mongodb.JCRModule").newInstance());
              }

            }
      } catch (Exception E) {

      }
  }
}
