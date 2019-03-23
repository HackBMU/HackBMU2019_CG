package com.example.sunejas.sihproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.sunejas.sihproject.Utilities.MovableFloatingActionButton;

public class PatientDashboardActivity extends AppCompatActivity {
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_dashboard);
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        phoneNumber = prefs.getString("phone", null);
        MovableFloatingActionButton addNewEventFab = findViewById(R.id.add_new_event_fab);
        addNewEventFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PatientDashboardActivity.this, AddEventActivity.class));

            }
        });
    }
}
