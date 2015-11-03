package com.codepath.grido.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.codepath.grido.R;
import com.codepath.grido.models.ImageColorParameter;
import com.codepath.grido.models.ImageRecord;
import com.codepath.grido.models.ImageSearchParameters;
import com.codepath.grido.models.ImageSizeParameter;
import com.codepath.grido.models.ImageTypeParameter;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by ayegorov on 10/31/15.
 */
public class ImageSearchClient {

    private static final String BASE_URL = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0";
    private static final int MAX_IMAGE_COUNT = 64;
    private static final int MAX_PAGE_SIZE = 8;

    private AsyncHttpClient httpClient;
    private Context context;

    private String[] imageSizes;
    private String[] imageColors;
    private String[] imageTypes;

    private int totalImageCount;

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

        imageSizes = context.getResources().getStringArray(R.array.image_search_parameters_sizes_array);
        imageColors = context.getResources().getStringArray(R.array.image_search_parameters_colors_array);
        imageTypes = context.getResources().getStringArray(R.array.image_search_parameters_types_array);
    }

    public void getImageRecords(String query, ImageSearchParameters parameters, final ImageSearchHandler handler) {
        totalImageCount = -1;
        getMoreImageRecordsStartingWithIndex(0, query, parameters, handler);
    }

    public boolean getMoreImageRecordsStartingWithIndex(final int startIndex, String query, ImageSearchParameters parameters, final ImageSearchHandler handler) {

        boolean result = false;

        assert (query.length() > 0);
        String url = BASE_URL + "&safe=active&start=" + startIndex + "&q=" + query + "&rsz=" + MAX_PAGE_SIZE + getImageFilterQuery(parameters);

        if (startIndex < totalImageCount || startIndex == 0) {
            result = true;

            Log.d("DEBUG IMAGE CLIENT:", "url - (" + url + ")");

            httpClient.get(url, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                    try {
                        JSONObject responseData = response.getJSONObject("responseData");
                        ArrayList<ImageRecord> imageRecords = ImageRecord.arrayFromJSON(responseData.getJSONArray("results"));

                        String resultCountString = responseData.getJSONObject("cursor").getString("resultCount");
                        int resultCount = 0;
                        try {
                            resultCount = NumberFormat.getNumberInstance(java.util.Locale.US).parse(resultCountString).intValue();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        ImageSearchClient.this.totalImageCount = Math.min(MAX_IMAGE_COUNT, Math.max(resultCount, 0));

                        Log.d("DEBUG IMAGE CLIENT:", "loaded startIndex - (" + startIndex + ") - totalImageCount - (" + ImageSearchClient.this.totalImageCount + ")");
                        handler.onSuccess(imageRecords);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        handler.onFailure(statusCode, "No Images");
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

        return result;
    }

    private String getImageFilterQuery(ImageSearchParameters parameters) {

        String filterQuery = "";

        if (parameters.imageSize != ImageSizeParameter.ImageSizeAny) {
            filterQuery += "&imgsz=" + imageSizes[parameters.imageSize.ordinal() - 1];
        }

        if (parameters.imageColor != ImageColorParameter.ImageColorAny) {
            filterQuery += "&imgcolor=" + imageColors[parameters.imageColor.ordinal() - 1];
        }

        if (parameters.imageType != ImageTypeParameter.ImageTypeAny) {
            filterQuery += "&imgtype=" + imageTypes[parameters.imageType.ordinal() - 1];
        }

        if (parameters.domain != null && parameters.domain.length() > 0) {
            filterQuery += "&as_sitesearch=" + parameters.domain;
        }

        return filterQuery;
    }
}
