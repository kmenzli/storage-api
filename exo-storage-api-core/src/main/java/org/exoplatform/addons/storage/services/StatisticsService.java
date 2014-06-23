package org.exoplatform.addons.storage.services;

import org.exoplatform.addons.storage.model.*;

import java.util.List;

/**
 * Created by menzli on 21/04/14.
 */
public interface StatisticsService {

    public static final String M_STATISTICS = "statistics";

    public void cleanupStatistics(long timestamp) throws Exception;

    public int count (long timestamp) throws Exception;;


    public StatisticsBean addEntry(ActorBean actor, String verb, ObjectBean object, TargetBean target, ContextBean context) throws Exception;

    public StatisticsBean addEntry(StatisticsBean statisticsBean) throws Exception;
}
