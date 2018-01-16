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

    private String TAG = TripsActivity.class.getSimpleName();
    private static String url = "https://my-json-server.typicode.com/lpotherat/pois/pois";

    private Spinner spinnerCinemas, spinnerRestaurants, spinnerStatus;
    private DatePicker datePickerPredictedDate;
    private EditText editTextComment;
    private Button buttonSave;

    List<Poi> cinemas, restaurants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trips);

        //buttonAddTrip = (Button)findViewById(R.id.buttonAddTrip);
        listViewTrips = (ListView)findViewById(R.id.listViewTrips);

        /*buttonAddTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TripsActivity.this, AddTripActivity.class);
                startActivity(intent);
            }
        });*/

        trips = new ArrayList<>();

        DbTripHelper dbTrip = new DbTripHelper(TripsActivity.this);
        dbTrip.open();
        trips = dbTrip.getAllTrips();
        dbTrip.close();

        TripAdapter tripAdapter = new TripAdapter(TripsActivity.this, trips);
        listViewTrips.setAdapter(tripAdapter);

        //region Ajout d'un parcours

        spinnerCinemas = (Spinner)findViewById(R.id.spinnerCinemas);
        spinnerRestaurants = (Spinner)findViewById(R.id.spinnerRestaurants);
        datePickerPredictedDate = (DatePicker)findViewById(R.id.datePickerPredictedDate);
        spinnerStatus = (Spinner)findViewById(R.id.spinnerStatus);
        editTextComment = (EditText)findViewById(R.id.editTextComment);
        buttonSave = (Button)findViewById(R.id.buttonSave);

        cinemas = new ArrayList<>();
        restaurants = new ArrayList<>();

        getPoisAsync();

        ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource(this, R.array.trip_status_array, R.layout.support_simple_spinner_dropdown_item);
        statusAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(statusAdapter);

        // Evenement lorsqu'on clique sur le bouton enregistrer
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Poi cinema = (Poi)spinnerCinemas.getSelectedItem();
                Poi restaurant = (Poi)spinnerRestaurants.getSelectedItem();
                String status = (String)spinnerStatus.getSelectedItem();

                int day = datePickerPredictedDate.getDayOfMonth();
                int month = datePickerPredictedDate.getMonth();
                int year = datePickerPredictedDate.getYear();
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, day);

                Trip trip = new Trip();
                trip.setCinemaId(cinema.getId());
                trip.setRestaurantId(restaurant.getId());
                trip.setCreationDate(Calendar.getInstance().getTime());
                trip.setPredictedDate(calendar.getTime());
                trip.setComment(editTextComment.getText().toString());
                trip.setStatus(status);

                DbTripHelper dbTrip = new DbTripHelper(TripsActivity.this);
                dbTrip.open();
                dbTrip.insertTrip(trip);
                dbTrip.close();
                finish();
                startActivity(getIntent());
            }
        });

        //endregion
    }

    //region GetPoisAsync

    private void getPoisAsync() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                HttpHelper sh = new HttpHelper();
                String jsonString = sh.makeServiceCall(url);

                if (jsonString != null) {
                    try {
                        JSONArray jsonPois = new JSONArray(jsonString);

                        for (int i = 0; i < jsonPois.length(); i++) {
                            JSONObject jsonPoi = jsonPois.getJSONObject(i);
                            JSONObject jsonLocation = jsonPoi.getJSONObject("location");
                            JSONObject jsonPosition = jsonLocation.getJSONObject("position");

                            String id = jsonPoi.getString("id");
                            String type = jsonPoi.getString("type");
                            String name = jsonPoi.getString("name");
                            String address = jsonLocation.getString("adress");
                            int postalCode = jsonLocation.getInt("postalCode");
                            String city = jsonLocation.getString("city");
                            double lat = jsonPosition.getDouble("lat");
                            double lon = jsonPosition.getDouble("lon");

                            if (type.equals("REST")){
                                restaurants.add(new Poi(id, type, name, new Location(address, postalCode, city, new Position(lat, lon))));
                            }
                            else {
                                cinemas.add(new Poi(id, type, name, new Location(address, postalCode, city, new Position(lat, lon))));
                            }
                        }
                    } catch (final JSONException e) {
                        Log.e(TAG, "Erreur de parsing Json: " + e.getMessage());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Erreur de parsing Json: " + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                final PoiSpinAdapter cinemasAdapter = new PoiSpinAdapter(TripsActivity.this, android.R.layout.simple_spinner_item, cinemas);
                spinnerCinemas.setAdapter(cinemasAdapter);

                final PoiSpinAdapter restaurantsAdapter = new PoiSpinAdapter(TripsActivity.this, android.R.layout.simple_spinner_item, restaurants);
                spinnerRestaurants.setAdapter(restaurantsAdapter);
            }
        }.execute();
    }

    //endregion
}
