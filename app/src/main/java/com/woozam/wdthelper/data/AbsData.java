package com.woozam.wdthelper.data;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.nodes.Element;

import java.io.Serializable;

public abstract class AbsData implements Serializable {

    private static final long serialVersionUID = -3457299138427233328L;

    protected transient JSONObject json;
    protected transient Element element;

    public AbsData() {
    }

    public AbsData(String jsonString) {
        this();
        try {
            json = new JSONObject(jsonString);
        } catch (JSONException e) {
            json = new JSONObject();
        }
    }

    public AbsData(Element element) {
        this();
        this.element = element;
    }
}