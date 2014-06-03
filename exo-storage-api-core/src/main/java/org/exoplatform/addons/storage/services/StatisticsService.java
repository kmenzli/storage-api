package org.exoplatform.addons.storage.services;

import org.exoplatform.addons.storage.model.StatisticsBean;

import java.util.List;

/**
 * Created by menzli on 21/04/14.
 */
public interface StatisticsService {

    //TODO : add methods to calculate count of returned results according to a given criteria

    public static final String M_STATISTICS = "statistics";

    public void cleanupStatistics(long timestamp) throws Exception;

    public List<StatisticsBean> search(String word, String type, int offset, int limit, int sort, int order, long timestamp) throws Exception;

    public List<StatisticsBean> filter(String user, String category, String categoryId, String type, String site, String siteType, String content, boolean isPrivate, long timestamp) throws Exception;

    public StatisticsBean addEntry(String user, String from, String type, String category, String categoryId, String content, String link, String site, String siteType) throws Exception;

    public List<StatisticsBean> getStatistics(long timestamp) throws Exception;

    public void exportStatistics() throws Exception;

    public boolean importStatistics() throws Exception;
}
