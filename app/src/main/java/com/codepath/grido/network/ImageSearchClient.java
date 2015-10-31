package com.codepath.grido.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.codepath.grido.models.ImageRecord;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by ayegorov on 10/31/15.
 */
public class ImageSearchClient {

    private static final String BASE_URL = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0";
    private AsyncHttpClient httpClient;
    private Context context;

    //
    // Private Helpers
    //
    private Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    //
    // -- Interface --
    //

    public ImageSearchClient(Context context) {
        this.context = context;
        this.httpClient = new AsyncHttpClient();
    }

    public void getImageRecords(String query, final ImageSearchHandler handler) {

        assert (query.length() > 0);
        String url = BASE_URL + "&q=" + query;
        httpClient.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    JSONObject responseData = response.getJSONObject("responseData");
                    ArrayList<ImageRecord> imageRecords = ImageRecord.arrayFromJSON(responseData.getJSONArray("results"));

                    handler.onSuccess(imageRecords);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

                String errorMessage;

                if (!isNetworkAvailable()) {
                    errorMessage = "Network is NOT available. Try again later.";
                } else {
                    errorMessage = errorResponse.toString();
                }

                handler.onFailure(statusCode, errorMessage);
            }
        });
    }
}
