package com.codepath.grido.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;

import com.codepath.grido.R;
import com.codepath.grido.network.ImageSearchClient;
import com.codepath.grido.network.ImageSearchHandler;

public class SearchActivity extends AppCompatActivity {

    private EditText searchEditText;
    private GridView imageSearchGridView;

    private ImageSearchClient imageSearchClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        imageSearchClient = new ImageSearchClient(this);
        
        setupViews();
    }

    private void setupViews() {
        searchEditText = (EditText)findViewById(R.id.searchEditText);
        imageSearchGridView = (GridView)findViewById(R.id.imageSearchGridView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onImageSearchClick(View v) {
        String searchQuery = searchEditText.getText().toString();
        imageSearchClient.getImageRecords(searchQuery, new ImageSearchHandler() {

//            public void onSuccess(ArrayList<ImageRecord> images) {
//                Log.d("Result", images.toString());
//            }
//
//            public void onFailure(int statusCode, String errorMessage) {
//            }
        });
//        final Toast toast = Toast.makeText(this, searchQuery, Toast.LENGTH_SHORT);
//        toast.show();
    }
}
