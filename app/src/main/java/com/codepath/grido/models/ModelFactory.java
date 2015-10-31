package com.codepath.grido.models;

/**
 * Created by ayegorov on 10/31/15.
 */
public class ModelFactory<T> {
    private static ModelFactory ourInstance = new ModelFactory();

    public static ModelFactory getInstance() {
        return ourInstance;
    }

    // Q: Can I still do it in Java?
//    public ArrayList<T> arrayFromJSON(JSONArray jsonArray) {
//
//        ArrayList<T> result = new ArrayList<T>();
//        for (int i = 0; i < jsonArray.length(); i++) {
//            try {
//                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                result.add(new T(jsonObject));
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//        }
//
//        return result;
//    }

    private ModelFactory() {
    }
}
