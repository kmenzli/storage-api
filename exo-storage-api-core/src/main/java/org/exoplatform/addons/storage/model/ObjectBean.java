package org.exoplatform.addons.storage.model;

import javax.xml.bind.annotation.XmlType;

/**
 * Created by menzli on 23/06/14.
 */
@XmlType(propOrder = { "objectType", "displayName", "content", "spentTime", "url", "link"})
public class ObjectBean {

    private String objectType = "";
    private String displayName = "";
    private String content = "";
    private float spentTime ;
    private String url = "";
    private String link = "";

    public void setSpentTime(float spentTime) {
        this.spentTime = spentTime;
    }

    public float getSpentTime() {
        return spentTime;
    }

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }



    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
