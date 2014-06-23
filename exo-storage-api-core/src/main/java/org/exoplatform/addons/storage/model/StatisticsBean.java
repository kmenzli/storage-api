package org.exoplatform.addons.storage.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.xml.bind.annotation.XmlType;
import java.util.List;

/**
 * Created by menzli on 02/05/14.
 */

@XmlType(propOrder = { "actor", "verb", "object", "target", "context"})
public class StatisticsBean {

    private ActorBean actor;

    private String verb;

    private ObjectBean object;

    private TargetBean target;

    private ContextBean context;

    public ActorBean getActor() {
        return actor;
    }

    public void setActor(ActorBean actor) {
        this.actor = actor;
    }

    public String getVerb() {
        return verb;
    }

    public void setVerb(String verb) {
        this.verb = verb;
    }

    public ObjectBean getObject() {
        return object;
    }

    public void setObject(ObjectBean object) {
        this.object = object;
    }

    public TargetBean getTarget() {
        return target;
    }

    public void setTarget(TargetBean target) {
        this.target = target;
    }

    public ContextBean getContext() {
        return context;
    }

    public void setContext(ContextBean context) {
        this.context = context;
    }

    public String toJSON() {

        throw new UnsupportedOperationException( "Method not yet implemented" );

    }

    public static String statisticstoJSON(List<StatisticsBean> statisticsBeans) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        return gson.toJson(statisticsBeans);

    }
}
