package com.example.thomas.dijoncity.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thomas.dijoncity.Models.Location;
import com.example.thomas.dijoncity.Models.Trip;
import com.example.thomas.dijoncity.R;

import java.util.List;

/**
 * Created by Thomas on 18/12/2017.
 */

public class TripAdapter extends ArrayAdapter<Trip> {
    public TripAdapter(Context context, List<Trip> trips) {
        super(context, 0, trips);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_trip, parent, false);
        }

        TripViewHolder viewHolder = (TripViewHolder) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new TripViewHolder();
            viewHolder.imageCinema = (ImageView)convertView.findViewById(R.id.imageCinema);
            viewHolder.textCinema = (TextView)convertView.findViewById(R.id.textCinema);
            viewHolder.imageRestaurant = (ImageView)convertView.findViewById(R.id.imageRestaurant);
            viewHolder.textRestaurant = (TextView)convertView.findViewById(R.id.textRestaurant);
            viewHolder.textStatus = (TextView)convertView.findViewById(R.id.textStatus);
            convertView.setTag(viewHolder);
        }

        Trip trip = getItem(position);

        viewHolder.imageCinema.setImageResource(R.drawable.cine);
        viewHolder.textCinema.setText(trip.getCinemaId());
        viewHolder.imageRestaurant.setImageResource(R.drawable.rest);
        viewHolder.textRestaurant.setText(trip.getRestaurantId());
        viewHolder.textStatus.setText(trip.getStatus());

        return convertView;
    }

    private class TripViewHolder {
        public ImageView imageCinema;
        public TextView textCinema;
        public ImageView imageRestaurant;
        public TextView textRestaurant;
        public TextView textStatus;
    }
}
