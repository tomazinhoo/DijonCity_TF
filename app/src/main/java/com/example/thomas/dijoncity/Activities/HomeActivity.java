package com.example.thomas.dijoncity.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thomas.dijoncity.Adapters.PoiAdapter;
import com.example.thomas.dijoncity.Models.Location;
import com.example.thomas.dijoncity.Models.Poi;
import com.example.thomas.dijoncity.Models.Position;
import com.example.thomas.dijoncity.R;
import com.example.thomas.dijoncity.Helpers.HttpHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private final int MY_PERMISSIONS_REQUEST_READ_SMS = 0;

    private String TAG = HomeActivity.class.getSimpleName();
    private static String url = "https://my-json-server.typicode.com/lpotherat/pois/pois";

    private ListView listViewPois;
    private Button buttonMyTrips, buttonMap;
    private TextView textViewNbPois;

    private List<Poi> pois;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (ContextCompat.checkSelfPermission(HomeActivity.this,
                Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(HomeActivity.this,
                    Manifest.permission.READ_SMS)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(HomeActivity.this,
                        new String[]{Manifest.permission.READ_SMS},
                        MY_PERMISSIONS_REQUEST_READ_SMS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

        buttonMyTrips = (Button) findViewById(R.id.buttonMyTrips);
        buttonMyTrips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, TripsActivity.class);
                startActivity(intent);
            }
        });

        buttonMap = (Button) findViewById(R.id.buttonMap);
        buttonMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });

        listViewPois = (ListView) findViewById(R.id.listViewPois);
        listViewPois.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getBaseContext(), DetailsActivity.class);
                String id = ((Poi)adapterView.getAdapter().getItem(i)).getId();
                intent.putExtra("id", id);

                startActivity(intent);
            }
        });

        pois = new ArrayList<>();
        getPoisAsync();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_SMS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
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
                            String adress = jsonLocation.getString("adress");
                            int postalCode = jsonLocation.getInt("postalCode");
                            String city = jsonLocation.getString("city");
                            double lat = jsonPosition.getDouble("lat");
                            double lon = jsonPosition.getDouble("lon");

                            pois.add(new Poi(id, type, name, new Location(adress, postalCode, city, new Position(lat, lon))));
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

                PoiAdapter poiAdapter = new PoiAdapter(HomeActivity.this, pois);
                listViewPois.setAdapter(poiAdapter);

                textViewNbPois = (TextView) findViewById(R.id.textViewNbPois);
                textViewNbPois.setText(String.valueOf(pois.size()));
            }
        }.execute();
    }

    //endregion
}
