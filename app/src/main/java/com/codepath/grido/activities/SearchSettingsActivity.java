package com.codepath.grido.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.ArrayRes;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.codepath.grido.R;
import com.codepath.grido.models.ImageColorParameter;
import com.codepath.grido.models.ImageSearchParameters;
import com.codepath.grido.models.ImageSizeParameter;
import com.codepath.grido.models.ImageTypeParameter;

public class SearchSettingsActivity extends AppCompatActivity {

    private Spinner imageSizeSpinner;
    private Spinner colorFilterSpinner;
    private Spinner imageTypeSpinner;
    private EditText siteFilterEditText;
    private Button saveButton;

    private ImageSearchParameters imageSearchParameters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_settings);

        Intent intent = getIntent();

        imageSearchParameters = (ImageSearchParameters) intent.getSerializableExtra("filter");

        createImageSizeSpinner();
        createColorFilterSpinner();
        createImageTypeSpinner();

        siteFilterEditText = (EditText) findViewById(R.id.siteFilterEditText);
        siteFilterEditText.setText(imageSearchParameters.domain);

        saveButton = (Button)findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int imageSizePosition = imageSizeSpinner.getSelectedItemPosition();
                int colorFilterPosition = colorFilterSpinner.getSelectedItemPosition();
                int imageTypePosition = imageTypeSpinner.getSelectedItemPosition();

                imageSearchParameters.imageSize = ImageSizeParameter.values()[imageSizePosition];
                imageSearchParameters.imageColor = ImageColorParameter.values()[colorFilterPosition];
                imageSearchParameters.imageType = ImageTypeParameter.values()[imageTypePosition];
                imageSearchParameters.domain = siteFilterEditText.getText().toString();

                Intent data = new Intent();
                data.putExtra("filter", imageSearchParameters);

                SearchSettingsActivity.this.setResult(RESULT_OK, data);
                SearchSettingsActivity.this.finish();
            }
        });
    }

    private Spinner createSpinner(@IdRes int resourceId, @ArrayRes int arrayResourceId) {
        Spinner spinner = (Spinner) findViewById(resourceId);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, arrayResourceId, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_view);
        spinner.setAdapter(adapter);
        return spinner;
    }

    private void createImageSizeSpinner() {
        imageSizeSpinner = createSpinner(R.id.imageSizeSpinner, R.array.image_search_sizes_array);
        imageSizeSpinner.setSelection(imageSearchParameters.imageSize.ordinal());
//        imageSizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                imageSearchParameters.imageSize = ImageSizeParameter.values()[position];
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });
    }

    private void createColorFilterSpinner() {
        colorFilterSpinner = createSpinner(R.id.colorFilterSpinner, R.array.image_search_colors_array);
        colorFilterSpinner.setSelection(imageSearchParameters.imageColor.ordinal());
//        colorFilterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                imageSearchParameters.imageColor = ImageColorParameter.values()[position];
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
    }

    private void createImageTypeSpinner() {
        imageTypeSpinner = createSpinner(R.id.imageTypeSpinner, R.array.image_search_types_array);
        imageTypeSpinner.setSelection(imageSearchParameters.imageType.ordinal());
//        imageTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                imageSearchParameters.imageType = ImageTypeParameter.values()[position];
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_search_settings, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
