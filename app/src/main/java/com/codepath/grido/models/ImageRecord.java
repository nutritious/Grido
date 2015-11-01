package com.codepath.grido.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ayegorov on 10/31/15.
 */
public class ImageRecord {
    public String url;
    public String thumbUrl;
    public String title;

    public ImageRecord(JSONObject jsonObject) {
        try {
            this.url = jsonObject.getString("url");
            this.thumbUrl = jsonObject.getString("tbUrl");
            this.title = jsonObject.getString("title");
        } catch(JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<ImageRecord> arrayFromJSON(JSONArray jsonArray) {

        ArrayList<ImageRecord> result = new ArrayList<ImageRecord>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                result.add(new ImageRecord(jsonObject));

            } catch (JSONException e) {
                result = null;
                e.printStackTrace();
            }

        }

        return result;
    }
}
