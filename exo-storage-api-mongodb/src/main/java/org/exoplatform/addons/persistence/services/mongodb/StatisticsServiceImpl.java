package org.exoplatform.addons.persistence.services.mongodb;

import com.mongodb.*;
import org.exoplatform.addons.persistence.listener.ConnectionManager;
import org.exoplatform.addons.persistence.model.StatisticsBean;
import org.exoplatform.addons.persistence.services.StatisticsService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Created by menzli on 02/05/14.
 */
@Named("statisticsService")
@ApplicationScoped
public class StatisticsServiceImpl implements StatisticsService {

    private static Logger LOG = Logger.getLogger(StatisticsServiceImpl.class.getName());

    private DB db() {
        return ConnectionManager.getInstance().getDB();
    }

    @Override
    public void cleanupStatistics(long timestamp) throws Exception {

        DBCollection coll = db().getCollection(M_STATISTICS);

        DBCursor cursor = null;

        BasicDBObject query = new BasicDBObject();

        if (timestamp > 0) {

            query.put("timestamp", new BasicDBObject("$lt", timestamp));

        }

        try {

            cursor = coll.find(query);

            while (cursor.hasNext())
            {
                DBObject doc = cursor.next();

                coll.remove(doc);
            }
        } finally {

            cursor.close();

        }
    }

    /**
     *
     * @param criteria
     * @param scope : ALL, user, category, categoryId, type, content, link
     * @param offset
     * @param limit
     * @param sort
     * @param order
     * @return
     * @throws Exception
     */
    @Override
    public List<StatisticsBean> search(String criteria, String scope, int offset, int limit, int sort, int order, long timestamp) throws Exception {

        LinkedList<StatisticsBean> statistics = new LinkedList<StatisticsBean>();

        DBCollection coll = db().getCollection(M_STATISTICS);

        BasicDBObject sortQuery = new BasicDBObject("_id", sort);

        //BasicDBObject orderQuery = new BasicDBObject("_id", order);

        BasicDBObject query = new BasicDBObject();

        DBCursor cursor = null;

        //TODO when the search is for content,the like operator should be used
        if (!scope.equalsIgnoreCase("ALL")) {

            query.put(scope,criteria);

        } else {

            //--- search statistics by user
            statistics.addAll(search(criteria, "user", offset, limit, sort, order, timestamp));
            LOG.finest("##### [User Search Engine] load ["+statistics.size()+"] tuples ####### ");

            //--- search statistics by category
            statistics.addAll(search(criteria, "category", offset, limit, sort, order, timestamp));
            LOG.finest("##### [Category Search Engine] load ["+statistics.size()+"] tuples ####### ");

            //--- search statistics by categoryId
            statistics.addAll(search(criteria, "categoryId", offset, limit, sort, order, timestamp));
            LOG.finest("##### [CategoryId Search Engine] load ["+statistics.size()+"] tuples ####### ");

            //--- search statistics by type
            statistics.addAll(search(criteria, "type", offset, limit, sort, order, timestamp));
            LOG.finest("##### [Type Search Engine] load ["+statistics.size()+"] tuples ####### ");


            //--- search statistics by content
            statistics.addAll(search(criteria, "content", offset, limit, sort, order, timestamp));
            LOG.finest("##### [Content Search Engine] load ["+statistics.size()+"] tuples ####### ");

            //--- search statistics by link
            statistics.addAll(search(criteria, "link", offset, limit, sort, order, timestamp));
            LOG.finest("##### [Link Search Engine] load [" + statistics.size() + "] tuples ####### ");

            return statistics;



        }

        cursor = coll.find(query).limit(limit).sort(sortQuery);

        while (cursor.hasNext()) {

            DBObject doc = cursor.next();
            StatisticsBean statisticsBean = new StatisticsBean();
            statisticsBean.setTimestamp((Long)doc.get("timestamp"));
            statisticsBean.setUser(doc.get("user").toString());
            if (doc.containsField("from")) {
                statisticsBean.setFrom(doc.get("from").toString());
            }
            statisticsBean.setCategory(doc.get("category").toString());
            statisticsBean.setCategoryId(doc.get("categoryId").toString());
            statisticsBean.setType(doc.get("type").toString());
            statisticsBean.setContent(doc.get("content").toString());
            statisticsBean.setLink(doc.get("link").toString());
            statisticsBean.setSite(doc.get("site").toString());
            statisticsBean.setSiteType(doc.get("siteType").toString());

            statistics.add(statisticsBean);

        }

        LOG.finest("##### [" + statistics.size() + "] tuples fetched ####### ");

        return statistics;
    }

    @Override
    public List<StatisticsBean> filter(String user, String category, String categoryId, String type, String site, String siteType, String content, boolean isPrivate, long timestamp) throws Exception {

        LinkedList<StatisticsBean> statistics = new LinkedList<StatisticsBean>();

        DBCollection collection = db().getCollection(M_STATISTICS);

        BasicDBObject query = new BasicDBObject("isPrivate", isPrivate);

        //TODO : replace if blocs by a design pattern
        if (content != null) {

            query.put("content", new BasicDBObject("$regex", content));

        }
        if (user != null) {

            query.put("user",user);

        }

        if (category != null) {

            query.put("category",category);

        }

        if (categoryId != null) {

            query.put("categoryId",categoryId);

        }

        if (type != null) {

            query.put("type",type);

        }

        if (timestamp > 0) {

            query.put("timestamp",new BasicDBObject("$gt", timestamp));

        }

        if ( site!= null ) {

            query.put("site",site);

        }

        if (siteType != null) {

            query.put("siteType",siteType);

        }

        //--- invoke the find method
        DBCursor cursor = collection.find(query);

        try {
            while (cursor.hasNext()) {
                DBObject doc = cursor.next();
                StatisticsBean statisticsBean = new StatisticsBean();
                statisticsBean.setTimestamp((Long)doc.get("timestamp"));
                statisticsBean.setUser(doc.get("user").toString());
                if (doc.containsField("from")) {
                    statisticsBean.setFrom(doc.get("from").toString());
                }
                statisticsBean.setCategory(doc.get("category").toString());
                statisticsBean.setCategoryId(doc.get("categoryId").toString());
                statisticsBean.setType(doc.get("type").toString());
                statisticsBean.setContent(doc.get("content").toString());
                statisticsBean.setLink(doc.get("link").toString());
                statisticsBean.setSite(doc.get("site").toString());
                statisticsBean.setSiteType(doc.get("siteType").toString());

                //--- Add the statistic tuple to the collection
                statistics.add(statisticsBean);
            }

        } catch (Exception E) {

            LOG.log (Level.SEVERE,"Connexion impossible :"+ E.getMessage(),E);

        } finally {

            //--- close the cursor after each invocation
            cursor.close();
        }

        return statistics;

    }

    @Override
    public StatisticsBean addEntry(String user, String from, String type, String category, String categoryId, String content, String link, String site, String siteType) {
        DBCollection coll = db().getCollection(M_STATISTICS);
        BasicDBObject doc = new BasicDBObject();
        doc.put("timestamp", System.currentTimeMillis());
        doc.put("user", user);
        doc.put("from", from);
        doc.put("type", type);
        doc.put("category", category);
        doc.put("categoryId", categoryId);
        doc.put("site", site);
        doc.put("siteType", siteType);
        doc.put("content", content);
        doc.put("link", link);
        doc.put("isPrivate", false);

        //--- add new line to the dababase (what's the difference with save() method)
        coll.insert(doc);

        return null;
    }

    @Override
    public List<StatisticsBean> getStatistics(long timestamp) throws Exception {

        LinkedList<StatisticsBean> statistics = new LinkedList<StatisticsBean>();

        DBCollection coll = db().getCollection(M_STATISTICS);

        DBCursor cursor = null;

        BasicDBObject query = new BasicDBObject();

        if (timestamp > 0 ) {

            //--- get only tuples which timestap >= {{timestamp}}
            query.put("timestamp", new BasicDBObject("$gte", timestamp));

        }
        try {

            cursor = coll.find(query);

            while (cursor.hasNext()) {

                DBObject doc = cursor.next();
                StatisticsBean statisticsBean = new StatisticsBean();
                statisticsBean.setTimestamp((Long)doc.get("timestamp"));
                if (doc.get("user") != null) {
                    statisticsBean.setUser(doc.get("user").toString());
                }
                if (doc.get("from") != null) {
                    statisticsBean.setFrom(doc.get("from").toString());
                }
                if (doc.get("category") != null) {
                    statisticsBean.setCategory(doc.get("category").toString());
                }
                statisticsBean.setCategoryId(doc.get("categoryId").toString());
                if (doc.get("type") != null) {
                    statisticsBean.setType(doc.get("type").toString());
                }
                if (doc.get("content") != null) {
                    statisticsBean.setContent(doc.get("content").toString());
                }
                if (doc.get("link") != null) {
                    statisticsBean.setLink(doc.get("link").toString());
                }

                statisticsBean.setSite(doc.get("site").toString());
                statisticsBean.setSiteType(doc.get("siteType").toString());

                //--- Add the statistic tupe to the collection
                statistics.add(statisticsBean);

            }
            return statistics;
        } finally {

            cursor.close();

        }

    }

    @Override
    public void exportStatistics() throws Exception {
        throw new UnsupportedOperationException( "Method not yet implemented" );
    }

    @Override
    public boolean importStatistics() throws Exception {
        throw new UnsupportedOperationException( "Method not yet implemented" );
    }
}
