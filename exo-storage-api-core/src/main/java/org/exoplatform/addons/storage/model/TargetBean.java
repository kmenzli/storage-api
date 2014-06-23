package org.exoplatform.addons.storage.model;

import javax.xml.bind.annotation.XmlType;

/**
 * Created by menzli on 23/06/14.
 */
@XmlType(propOrder = { "objectType", "displayName", "actorBean"})
public class TargetBean {

    private String objectType = "";

    private String displayName = "";

    private ActorBean actorBean;

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public ActorBean getActorBean() {
        return actorBean;
    }

    public void setActorBean(ActorBean actorBean) {
        this.actorBean = actorBean;
    }
}
