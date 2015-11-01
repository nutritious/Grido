package com.codepath.grido.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.grido.R;
import com.codepath.grido.models.ImageRecord;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ayegorov on 11/1/15.
 */
public class ImageSearchGridAdapter extends ArrayAdapter {

    private class ViewHolder {
        public ImageView imageView;
        public TextView textView;
    };

    public ImageSearchGridAdapter(Context context, ArrayList<ImageRecord> imageRecords) {
        super(context, 0, imageRecords);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageRecord imageRecord = (ImageRecord) getItem(position);
        ViewHolder viewHolder = null;

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.image_search_item, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.textView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.textView.setText(Html.fromHtml(imageRecord.title));
        Picasso.with(getContext()).load(imageRecord.thumbUrl).into(viewHolder.imageView);

        return convertView;
    }
}
