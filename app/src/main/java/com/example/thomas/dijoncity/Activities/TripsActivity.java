package com.example.thomas.dijoncity.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thomas.dijoncity.Adapters.PoiAdapter;
import com.example.thomas.dijoncity.Adapters.PoiSpinAdapter;
import com.example.thomas.dijoncity.Adapters.TripAdapter;
import com.example.thomas.dijoncity.Helpers.DbTripHelper;
import com.example.thomas.dijoncity.Helpers.HttpHelper;
import com.example.thomas.dijoncity.Models.Location;
import com.example.thomas.dijoncity.Models.Poi;
import com.example.thomas.dijoncity.Models.Position;
import com.example.thomas.dijoncity.Models.Trip;
import com.example.thomas.dijoncity.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TripsActivity extends AppCompatActivity {

    private Button buttonAddTrip;
    private ListView listViewTrips;

    List<Trip> trips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trips);

        buttonAddTrip = (Button)findViewById(R.id.buttonAddTrip);
        listViewTrips = (ListView)findViewById(R.id.listViewTrips);

        buttonAddTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TripsActivity.this, AddTripActivity.class);
                startActivity(intent);
            }
        });

        trips = new ArrayList<>();

        DbTripHelper dbTrip = new DbTripHelper(TripsActivity.this);
        dbTrip.open();
        trips = dbTrip.getAllTrips();
        dbTrip.close();

        TripAdapter tripAdapter = new TripAdapter(TripsActivity.this, trips);
        listViewTrips.setAdapter(tripAdapter);
    }
}
