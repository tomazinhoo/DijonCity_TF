package com.example.thomas.dijoncity.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.thomas.dijoncity.R;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        String id = getIntent().getStringExtra("id");
        Toast.makeText(getApplicationContext(), id, Toast.LENGTH_LONG).show();
    }
}
