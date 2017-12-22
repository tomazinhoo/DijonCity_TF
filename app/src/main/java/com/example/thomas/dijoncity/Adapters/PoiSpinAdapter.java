package com.example.thomas.dijoncity.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.thomas.dijoncity.Models.Poi;
import com.example.thomas.dijoncity.R;

import java.util.List;

/**
 * Created by Thomas on 18/12/2017.
 */

public class PoiSpinAdapter extends ArrayAdapter<Poi> {
    private Context context;
    private List<Poi> pois;

    public PoiSpinAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Poi> objects) {
        super(context, resource, objects);
        this.context = context;
        this.pois = objects;
    }

    @Override
    public int getCount() {
        return pois.size();
    }

    @Override
    public Poi getItem(int position) {
        return pois.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_poi_row, parent, false);
        }

        TextView text = (TextView)convertView.findViewById(R.id.name);
        text.setText(pois.get(position).getName());
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_poi_row, parent, false);
        }

        TextView text = (TextView)convertView.findViewById(R.id.name);
        text.setText(pois.get(position).getName());
        return convertView;
    }
}
