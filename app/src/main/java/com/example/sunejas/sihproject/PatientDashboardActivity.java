package com.example.sunejas.sihproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.sunejas.sihproject.Utilities.MovableFloatingActionButton;
import com.valdesekamdem.library.mdtoast.MDToast;

public class PatientDashboardActivity extends AppCompatActivity {
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    String phoneNumber;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_dashboard);
        prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        phoneNumber = prefs.getString("phone", null);
        MovableFloatingActionButton addNewEventFab = findViewById(R.id.add_new_event_fab);
        addNewEventFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PatientDashboardActivity.this, AddEventActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menu_logout:
                prefs.edit().clear().apply();


                MDToast.makeText(PatientDashboardActivity.this, "Logout Success", Toast.LENGTH_SHORT, MDToast.TYPE_SUCCESS).show();
                startActivity(new Intent(PatientDashboardActivity.this, LoginActivity.class));
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}


