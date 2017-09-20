package com.example.thomas.dijoncity.Activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thomas.dijoncity.Models.Location;
import com.example.thomas.dijoncity.Models.Poi;
import com.example.thomas.dijoncity.Models.Position;
import com.example.thomas.dijoncity.R;
import com.example.thomas.dijoncity.Helpers.HttpHelper;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

public class DetailsActivity extends AppCompatActivity {

    private String TAG = DetailsActivity.class.getSimpleName();
    private static String url = "https://my-json-server.typicode.com/lpotherat/pois/pois/";

    private TextView textViewName, textViewAddress, textViewCity;
    private SupportMapFragment mapFragment;

    private Poi poi;
    private String idPoi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        idPoi = getIntent().getStringExtra("id");

        textViewName = (TextView) findViewById(R.id.textViewName);
        textViewAddress = (TextView) findViewById(R.id.textViewAddress);
        textViewCity = (TextView) findViewById(R.id.textViewCity);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        getPoiAsync();
    }

    //region GetPoiAsync

    private Bitmap resizeMapIcons(String iconName, int width, int height){
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(),getResources().getIdentifier(iconName, "drawable", getPackageName()));
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
        return resizedBitmap;
    }

    private void getPoiAsync() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                HttpHelper sh = new HttpHelper();
                String jsonString = sh.makeServiceCall(url + idPoi);

                if (jsonString != null) {
                    try {
                        JSONObject jsonPoi = new JSONObject(jsonString);
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

                        poi = new Poi(id, type, name, new Location(adress, postalCode, city, new Position(lat, lon)));
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

                textViewName.setText(poi.getName());
                textViewAddress.setText(poi.getLocation().getAdress());
                textViewCity.setText(poi.getLocation().getPostalCode() + " " + poi.getLocation().getCity());

                mapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {

                        LatLng latLng = new LatLng(poi.getLocation().getPosition().getLat(), poi.getLocation().getPosition().getLon());
                        MarkerOptions marker = new MarkerOptions().title(poi.getName()).position(latLng);

                        // Change l'icone du marker
                        if (poi.getType().equals("REST")) {
                            marker.icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("rest", 110, 110)));
                        } else {
                            marker.icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("cine", 120, 120)));
                        }

                        // Ajoute le marker sur la map
                        googleMap.addMarker(marker);

                        // Centre la map sur la ville (avec animation de zoom)
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
                    }
                });
            }
        }.execute();
    }

    //endregion
}
