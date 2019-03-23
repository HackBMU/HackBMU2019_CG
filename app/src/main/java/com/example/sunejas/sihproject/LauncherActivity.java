package com.example.sunejas.sihproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LauncherActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String restoredRole = prefs.getString("role", null);
        if (restoredRole != null) {
            if(restoredRole.equals("patient")){
                startActivity(new Intent(LauncherActivity.this,PatientDashboardActivity.class));
                finish();
            }else if(restoredRole.equals("doctor")){
                startActivity(new Intent(LauncherActivity.this,DoctorDashboardActivity.class));
                finish();
            }
        }else {
            startActivity(new Intent(LauncherActivity.this,MainActivity.class));
            finish();
        }
    }
}
