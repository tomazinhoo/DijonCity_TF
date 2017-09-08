package com.example.thomas.dijoncity.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thomas.dijoncity.Models.Location;
import com.example.thomas.dijoncity.Models.Poi;
import com.example.thomas.dijoncity.R;

import java.util.List;

/**
 * Created by Thomas on 08/09/2017.
 */

public class PoiAdapter extends ArrayAdapter<Poi> {

    public PoiAdapter(Context context, List<Poi> poises) {
        super(context, 0, poises);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_poi, parent, false);
        }

        PoiViewHolder viewHolder = (PoiViewHolder) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new PoiViewHolder();
            viewHolder.image = (ImageView) convertView.findViewById(R.id.image);
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.address = (TextView) convertView.findViewById(R.id.address);
            convertView.setTag(viewHolder);
        }

        Poi poi = getItem(position);
        Location location = poi.getLocation();

        if (poi.getType().equals("REST")) {
            viewHolder.image.setImageResource(R.drawable.rest);
        } else {
            viewHolder.image.setImageResource(R.drawable.cine);
        }
        viewHolder.name.setText(poi.getName());
        viewHolder.address.setText(location.getAdress() + " " + location.getPostalCode() + " " + location.getCity());

        return convertView;
    }

    private class PoiViewHolder {
        public ImageView image;
        public TextView name;
        public TextView address;
    }
}
