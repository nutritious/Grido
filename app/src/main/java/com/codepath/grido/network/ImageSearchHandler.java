package com.codepath.grido.network;

import android.util.Log;

import com.codepath.grido.models.ImageRecord;

import java.util.ArrayList;

/**
 * Created by ayegorov on 10/31/15.
 */
public class ImageSearchHandler {

    public void onSuccess(ArrayList<ImageRecord> images) {
        Log.w("DEBUG", "got the images " + images.toString());
    }

    public void onFailure(int statusCode, String errorMessage) {
        Log.w("DEBUG", "request failed with code '" + statusCode + "' message '" + errorMessage + "'" );
    }
}
