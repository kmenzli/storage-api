package org.exoplatform.addons.storage.mongdb;

import org.exoplatform.addons.storage.bootstrap.ServiceBootstrap;
import org.exoplatform.addons.storage.listener.ConnectionManager;
import org.exoplatform.addons.storage.model.*;
import org.exoplatform.addons.storage.services.StatisticsService;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by menzli on 29/04/14.
 */
public class StatisticsTestCase extends AbstractTestCase {

    ActorBean actor1,actor2;

    ContextBean context;

    ObjectBean object;

    TargetBean target;

    StatisticsBean stats;

    StatisticsService statisticsService;

    List<StatisticsBean> statisticBOs = null;

    @Before
    public void setUp() {

        /** Mock Objects*/
        actor1 = new ActorBean();
        actor1.setObjectType("person");
        actor1.setUserName("kmenzli");

        actor2 = new ActorBean();
        actor2.setObjectType("person");
        actor2.setUserName("bpaillereau");

        context = new ContextBean();
        context.setType("site");
        context.setValue("intranet");

        object = new ObjectBean();
        object.setObjectType("page");
        object.setDisplayName("");
        object.setSpentTime(0.12f);
        object.setContent("Validating the stat module");
        object.setLink("http://exoplatform.org");
        object.setUrl("platform/acme");

        target = new TargetBean();
        target.setObjectType("post");
        target.setDisplayName("145efqdrezrf12qsdf");
        target.setActorBean(actor2);

        //--- Statistics Bean init
        stats = new StatisticsBean();
        stats.setActor(actor1);
        stats.setContext(context);
        stats.setObject(object);
        stats.setTarget(target);
        stats.setVerb("like");

        ConnectionManager.getInstance().getDB().getCollection(StatisticsService.M_STATISTICS).drop();

        statisticsService = ServiceBootstrap.getStatisticsService();
    }

    @Test
    //@PerfTest(invocations = 10, threads = 1)
    public void testAddEntry() throws Exception {


        statisticsService.addEntry(stats);

        assertEquals(statisticsService.count(0),1);

        statisticsService.cleanupStatistics(0);

    }
}
