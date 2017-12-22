package com.example.thomas.dijoncity.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.TextView;

import com.example.thomas.dijoncity.Models.Trip;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Thomas on 20/09/2017.
 */

public class DbTripHelper {
    private static final String TABLE_TRIP = "Trip";
    private static final String COL_ID = "id";
    private static final int NUM_COL_ID = 0;
    private static final String COL_CINEMA_ID = "cinemaId";
    private static final int NUM_COL_CINEMA_ID = 1;
    private static final String COL_RESTAURANT_ID = "restaurantId";
    private static final int NUM_COL_RESTAURANT_ID = 2;
    private static final String COL_CREATION_DATE = "creationDate";
    private static final int NUM_COL_CREATION_DATE = 3;
    private static final String COL_PREDICTED_DATE = "predictedDate";
    private static final int NUM_COL_PREDICTED_DATE = 4;
    private static final String COL_COMMENT = "comment";
    private static final int NUM_COL_COMMENT = 5;
    private static final String COL_STATUS = "status";
    private static final int NUM_COL_STATUS = 6;

    private SQLiteDatabase db;
    private  DataBaseHelper dbHelper;

    public DbTripHelper(Context context) {
        dbHelper = new DataBaseHelper(context);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    public long insertTrip(Trip trip) {
        ContentValues values = new ContentValues();

        values.put(COL_CINEMA_ID, trip.getCinemaId());
        values.put(COL_RESTAURANT_ID, trip.getRestaurantId());
        values.put(COL_CREATION_DATE, String.valueOf(trip.getCreationDate()));
        values.put(COL_PREDICTED_DATE, String.valueOf(trip.getPredictedDate()));
        values.put(COL_COMMENT, trip.getComment());
        values.put(COL_STATUS, trip.getStatus());

        return db.insert(TABLE_TRIP, null, values);
    }

    public List<Trip> getAllTrips() {
        List<Trip> trips = new ArrayList<>();

        Cursor cursor = db.rawQuery("select * from " + TABLE_TRIP, null);

        while (cursor.moveToNext()) {
            Trip trip = cursorToTrip(cursor);
            trips.add(trip);
        }

        cursor.close();

        return trips;
    }

    public Trip cursorToTrip(Cursor c) {
        if (c.getCount() == 0)
            return null;

        Date creationDate = new Date(c.getLong(NUM_COL_CREATION_DATE));
        Date predictedDate = new Date(c.getLong(NUM_COL_PREDICTED_DATE));

        Trip trip = new Trip();
        trip.setId(c.getInt(NUM_COL_ID));
        trip.setCinemaId(c.getString(NUM_COL_CINEMA_ID));
        trip.setRestaurantId(c.getString(NUM_COL_RESTAURANT_ID));
        trip.setCreationDate(creationDate);
        trip.setPredictedDate(predictedDate);
        trip.setComment(c.getString(NUM_COL_COMMENT));
        trip.setStatus(c.getString(NUM_COL_STATUS));

        return trip;
    }
}
