package org.exoplatform.addons.storage.listener;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.exoplatform.addons.storage.utils.PropertyManager;

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

      //TODO : Provide a pattern to simplify the intoduction of new implementation such as couchbase, elastic-search, etc..
      try {
          if (injector_==null) {

              //TODO : The binding is done during the compilation time itself, IMO it should be done during the runtime !!!

              //TODO : need to implement a service to retreive which Module should be used. 1- Use a dynamic provider or a properties file, etc ...

              // Use the activated implementation
              Class<?> guiceModule = Class.forName(PropertyManager.getProperty(PropertyManager.PROPERTY_SERVICES_IMPLEMENTATION));

              // Create Guice Injector
              injector_ = Guice.createInjector((AbstractModule)guiceModule.newInstance());
          }
      } catch (Exception E) {

          log.severe("Storage Engine faced problems during Guice Modules initialization");

      }
  }
}
