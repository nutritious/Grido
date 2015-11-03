package com.codepath.grido.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.codepath.grido.R;
import com.codepath.grido.adapters.EndlessScrollListener;
import com.codepath.grido.adapters.ImageSearchGridAdapter;
import com.codepath.grido.models.ImageRecord;
import com.codepath.grido.models.ImageSearchParameters;
import com.codepath.grido.network.ImageSearchClient;
import com.codepath.grido.network.ImageSearchHandler;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 66;

    private EditText searchEditText;
    private GridView imageSearchGridView;

    private ImageSearchClient imageSearchClient;
    private ArrayList<ImageRecord> imageRecords;
    private ImageSearchGridAdapter imageSearchGridAdapter;

    private ImageSearchParameters filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        imageSearchClient = new ImageSearchClient(this);
        imageRecords = new ArrayList<ImageRecord>();

        filter = new ImageSearchParameters();

        setupViews();

        imageSearchGridAdapter = new ImageSearchGridAdapter(this, imageRecords);
        imageSearchGridView.setAdapter(imageSearchGridAdapter);

        imageSearchGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchActivity.this, ImageDetailActivity.class);
                ImageRecord imageRecord = imageRecords.get(position);
                intent.putExtra("imageUrl", imageRecord.url);
                startActivity(intent);
            }
        });

        imageSearchGridView.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                String searchQuery = searchEditText.getText().toString();
                boolean loadingMore = imageSearchClient.getMoreImageRecordsStartingWithIndex(totalItemsCount, searchQuery, filter, new ImageSearchHandler() {

                    public void onSuccess(ArrayList<ImageRecord> records) {
                        imageRecords.addAll(records);
                        imageSearchGridAdapter.notifyDataSetChanged();

                    }

                    public void onFailure(int statusCode, String errorMessage) {
                        Toast.makeText(SearchActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });

                return loadingMore;
            }
        });
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
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    public void onSettings(MenuItem item) {
        Intent intent = new Intent(this, SearchSettingsActivity.class);

        intent.putExtra("filter", filter);

        startActivityForResult(intent, REQUEST_CODE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            filter = (ImageSearchParameters) data.getSerializableExtra("filter");
        }
    }

    public void onImageSearchClick(View v) {
        String searchQuery = searchEditText.getText().toString();
        imageSearchClient.getImageRecords(searchQuery, filter, new ImageSearchHandler() {

            public void onSuccess(ArrayList<ImageRecord> records) {
                imageRecords.clear();
                imageRecords.addAll(records);
                imageSearchGridAdapter.notifyDataSetChanged();

            }

            public void onFailure(int statusCode, String errorMessage) {
                imageRecords.clear();
                imageSearchGridAdapter.notifyDataSetChanged();
                Toast.makeText(SearchActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean loadMoreDataIfNeeded() {
        return true;
    }
}
