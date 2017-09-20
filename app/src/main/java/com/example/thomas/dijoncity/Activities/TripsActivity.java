package com.example.thomas.dijoncity.Activities;

import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.thomas.dijoncity.Adapters.PoiAdapter;
import com.example.thomas.dijoncity.Models.Poi;
import com.example.thomas.dijoncity.R;

import java.util.ArrayList;
import java.util.List;

public class TripsActivity extends AppCompatActivity {

    private Spinner spinnerCinema, spinnerStatus;
    private EditText editTextPredictedDate;

    List<Poi> cinemas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trips);

        /*cinemas = new ArrayList<>();
        spinnerCinema = (Spinner) findViewById(R.id.spinnerCinema);
        ArrayAdapter<Poi> poiAdapter = new PoiAdapter(TripsActivity.this, cinemas);
        poiAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerCinema.setAdapter(poiAdapter);*/

        editTextPredictedDate = (EditText) findViewById(R.id.editTextPredictedDate);

        spinnerStatus = (Spinner) findViewById(R.id.spinnerStatus);
        ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource(this, R.array.trip_status_array, R.layout.support_simple_spinner_dropdown_item);
        statusAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(statusAdapter);
    }
}
