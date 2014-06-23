package org.exoplatform.addons.storage.model;

import javax.xml.bind.annotation.XmlType;

/**
 * Created by menzli on 23/06/14.
 */
@XmlType(propOrder = { "objectType", "userName"})
public class ActorBean {

    private String objectType = "";

    private String userName = "";

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
