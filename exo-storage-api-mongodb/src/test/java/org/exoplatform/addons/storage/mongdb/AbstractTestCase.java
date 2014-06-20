package org.exoplatform.addons.storage.mongdb;

import org.exoplatform.addons.storage.bootstrap.ServiceBootstrap;
import org.exoplatform.addons.storage.listener.ConnectionManager;
import org.exoplatform.addons.storage.listener.GuiceManager;
import org.exoplatform.addons.storage.utils.PropertyManager;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.io.IOException;
import java.util.logging.Logger;

public class AbstractTestCase
{


  static Logger log = Logger.getLogger("StatisticsTestCase");

  @BeforeClass
  public static void before() throws IOException
  {
    PropertyManager.overrideProperty(PropertyManager.PROPERTY_SERVER_TYPE, "embed");
    PropertyManager.overrideProperty(PropertyManager.PROPERTY_SERVER_PORT, "27777");

    ConnectionManager.forceNew();
    ConnectionManager.getInstance().getDB("unittest");

    GuiceManager.forceNew();
    ServiceBootstrap.forceNew();
  }

  @AfterClass
  public static void teardown() throws Exception {
    ConnectionManager.getInstance().close();
  }




}
