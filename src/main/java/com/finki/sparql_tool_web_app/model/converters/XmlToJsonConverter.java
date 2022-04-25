package com.finki.sparql_tool_web_app.model.converters;

import org.json.*;

public class XmlToJsonConverter {
    public static String convert(String xml) {
        try {
            JSONObject json = XML.toJSONObject(xml);
            String jsonString = json.toString(4);
            return jsonString;

        }catch (JSONException e) {
            System.out.println(e.toString());
            return null;
        }
    }
}
