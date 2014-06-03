package org.exoplatform.addons.storage.model;

import javax.xml.bind.annotation.XmlType;
import java.util.List;

/**
 * Created by menzli on 02/05/14.
 */

@XmlType(propOrder = { "user", "from", "type", "category", "categoryId", "content", "link", "site", "siteType", "timestamp" })
public class StatisticsBean {

    //@XmlElement(name = "user")
    private String user;

    private String from;

    private String type;

    private String content;

    private String link;

    private String category;

    private String site;

    private String siteType;

    private String categoryId;

    private Long timestamp;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getSiteType() {
        return siteType;
    }

    public void setSiteType(String siteType) {
        this.siteType = siteType;
    }

    public String toJSON()
    {
        StringBuffer sb = new StringBuffer();

        sb.append("{");

        sb.append("\"user\": \""+this.getUser()+"\",");
        sb.append("\"type\": \""+this.getType()+"\",");
        sb.append("\"from\": \""+this.getFrom()+"\",");
        sb.append("\"site\": \""+this.getSite()+"\",");
        sb.append("\"siteType\": \""+this.getSiteType()+"\",");
        sb.append("\"category\": \""+this.getCategory()+"\",");
        sb.append("\"categoryId\": \""+this.getCategoryId()+"\",");
        sb.append("\"content\": \""+this.getContent().replaceAll("\n", "<br/>")+"\",");
        sb.append("\"link\": \""+this.getLink()+"\",");
        sb.append("\"timestamp\": "+this.getTimestamp());

        sb.append("}");

        return sb.toString();
    }

    public static String statisticstoJSON(List<StatisticsBean> statisticsBeans)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("\"statistics\": [");
        boolean first=true;
        for (StatisticsBean statisticsBean:statisticsBeans) {
            if (!first) {
                sb.append(",");
            } else {
                first=false;
            }

            sb.append(statisticsBean.toJSON());

        }
        sb.append("]");

        return sb.toString();
    }

}
