package org.exoplatform.addons.storage.model;

import javax.xml.bind.annotation.XmlType;

/**
 * Created by menzli on 23/06/14.
 */
@XmlType(propOrder = { "type", "value"})
public class ContextBean {

    private String type = "";
    private String value = "";

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
