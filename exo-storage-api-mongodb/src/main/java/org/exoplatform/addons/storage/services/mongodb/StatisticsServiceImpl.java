package org.exoplatform.addons.storage.services.mongodb;

import com.mongodb.*;
import org.exoplatform.addons.storage.listener.ConnectionManager;
import org.exoplatform.addons.storage.model.*;
import org.exoplatform.addons.storage.services.StatisticsService;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

    private String exportLocation = System.getProperty("java.io.tmpdir");

    private String importLocation;

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

    @Override
    public StatisticsBean addEntry(ActorBean actor, String verb, ObjectBean object, TargetBean target, ContextBean context) {

        DBCollection coll = db().getCollection(M_STATISTICS);

        BasicDBObject doc = new BasicDBObject();

        doc.put("timestamp", System.currentTimeMillis());

        doc.put("actor", actor);
        doc.put("verb", verb);
        doc.put("object", object);
        doc.put("target", target);
        doc.put("context", context);

        doc.put("isPrivate", false);

        //--- add new line to the dababase (what's the difference with save() method)
        coll.insert(doc);

        return null;
    }

    @Override
    public StatisticsBean addEntry(StatisticsBean statisticsBean) {

        DBCollection coll = db().getCollection(M_STATISTICS);

        BasicDBObject doc = new BasicDBObject();

        doc.put("timestamp", System.currentTimeMillis());

        //--- Persist an Actor Bean
        BasicDBObject actor = new BasicDBObject();

        actor.put("objectType",statisticsBean.getActor().getObjectType());
        actor.put("userName",statisticsBean.getActor().getUserName());
        doc.put("actor",actor);

        /** FIN */

        //--- persist the verb
        doc.put("verb", statisticsBean.getVerb());


        //--- Persist the Object Bean
        BasicDBObject object = new BasicDBObject();

        object.put("objectType",statisticsBean.getObject().getObjectType());
        object.put("displayName",statisticsBean.getObject().getDisplayName());
        object.put("content",statisticsBean.getObject().getContent());
        object.put("spentTime", statisticsBean.getObject().getSpentTime());
        object.put("url",statisticsBean.getObject().getUrl());
        object.put("link",statisticsBean.getObject().getLink());
        doc.put("object", actor);
        /** FIN */

        //--- Persist the the Target Bean
        if (statisticsBean.getTarget() != null ) {

            BasicDBObject target = new BasicDBObject();

            target.put("objectType",statisticsBean.getTarget().getObjectType());
            target.put("displayName",statisticsBean.getTarget().getDisplayName());
            //TODO : Bean Actors not serializable, add a method to do it
            //target.put("actorBean",statisticsBean.getActor());

            doc.put("target", target);
        }
        /** FIN */

        //--- Persist the the Context Bean

        if (statisticsBean.getContext() != null) {

            BasicDBObject context = new BasicDBObject();

            context.put("type",statisticsBean.getContext().getType());
            context.put("value",statisticsBean.getContext().getValue());

            doc.put("context", context);

        }



        doc.put("isPrivate", false);

        //--- add new line to the dababase (what's the difference with save() method)
        coll.insert(doc);

        return null;
    }

    @Override
    public int count (long timestamp) throws Exception {

        DBCollection coll = db().getCollection(M_STATISTICS);

        BasicDBObject query = new BasicDBObject();

        if (timestamp > 0 ) {

            //--- get only tuples which timestap >= {{timestamp}}
            query.put("timestamp", new BasicDBObject("$gte", timestamp));

        }

        return coll.find(query).count();

    }

    @Override
    public void export (List<StatisticsBean> statistics, String format) throws Exception {

        if (format.equalsIgnoreCase("json")) {

            json(statistics);

        } else if (format.equalsIgnoreCase("xml")) {

            xml(statistics);

        } else {

            throw new IllegalArgumentException();

        }
    }

    /**
     *
     * @param statistics
     */
    private void xml (List<StatisticsBean> statistics) {
        //--- build the xml structure
        Document doc = structure(statistics);

        //--- Generate the xml file
        XMLOutputter outputter = null;

        long timestamp = System.currentTimeMillis() / 1000;

        try {

            outputter = new XMLOutputter(Format.getPrettyFormat());

            outputter.output(doc, new FileWriter(exportLocation+"/statistics-"+timestamp+".xml"));

        } catch (IOException IOE) {

            LOG.severe(IOE.getMessage());

        }
    }

    /**
     *
     * @param statistics
     */
    private void json (List<StatisticsBean> statistics) {

        FileWriter file = null;

        long timestamp = System.currentTimeMillis() / 1000;

        try {

            file = new FileWriter(exportLocation+"/statistics-"+timestamp+".json");

            file.write(StatisticsBean.statisticstoJSON(statistics));

            file.flush();

            file.close();

        } catch (IOException IOE) {

            LOG.severe(IOE.getMessage());
        }
    }

    /**
     *
     * @param statistics
     * @return
     */
    private Document structure(List<StatisticsBean> statistics) {

        Document doc = new Document();

        Element statsListElement = new Element("statistics");

        Element statisticElement = null;

        for (StatisticsBean statisticsBean : statistics) {

            statisticElement = new Element("statistic");

            statisticElement.setAttribute("verb",statisticsBean.getVerb());

            Element actorElement = new Element("actor");

            actorElement.addContent(new Element("objectType").addContent(statisticsBean.getActor().getObjectType()));

            actorElement.addContent(new Element("userName").addContent(statisticsBean.getActor().getUserName()));

            statsListElement.addContent(statisticElement);

        }

        doc.addContent(statsListElement);

        return doc;

    }





}
