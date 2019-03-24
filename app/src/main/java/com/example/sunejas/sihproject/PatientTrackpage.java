package com.example.sunejas.sihproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.sunejas.sihproject.ChatPage.ChatActivity;

import static com.example.sunejas.sihproject.DoctorDashboardActivity.MY_PREFS_NAME;

public class PatientTrackpage extends AppCompatActivity {
FloatingActionButton fb;
 SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_trackpage);
        Button button=findViewById(R.id.bt_close);
        fb=findViewById(R.id.fab_chat);
        Button assign=findViewById(R.id.bt_appointment);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Case is closed",Toast.LENGTH_LONG).show();
            }
        });
        pref = this.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        final String name = pref.getString("name", null);
        final String phone = pref.getString("phone", null);

        assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Doctor has been notified.Chat with the doctor or call doctor at 9467893333",Toast.LENGTH_LONG).show();
            }
        });
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(PatientTrackpage.this, ChatActivity.class);
                intent.putExtra("name",name);
                intent.putExtra("phone",phone);
                intent.putExtra("tag","p");
                startActivity(intent);
            }
        });
    }

}
