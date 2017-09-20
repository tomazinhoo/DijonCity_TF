package com.example.thomas.dijoncity.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thomas.dijoncity.Adapters.PoiAdapter;
import com.example.thomas.dijoncity.Models.Location;
import com.example.thomas.dijoncity.Models.Poi;
import com.example.thomas.dijoncity.Models.Position;
import com.example.thomas.dijoncity.R;
import com.example.thomas.dijoncity.Services.HttpHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private String TAG = HomeActivity.class.getSimpleName();
    private static String url = "https://my-json-server.typicode.com/lpotherat/pois/pois";

    private TextView textViewTitle;
    private ListView listViewPois;

    private List<Poi> pois;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        textViewTitle.setText("Liste des points d'intérêts de Dijon");

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

    //region GetPoisAsync

    private void getPoisAsync() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                HttpHandler sh = new HttpHandler();
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
            }
        }.execute();
    }

    //endregion
}
