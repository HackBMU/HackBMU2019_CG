package com.example.sunejas.sihproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.sunejas.sihproject.Adapter.PostAdapter;
import com.example.sunejas.sihproject.Models.EventDetails;
import com.example.sunejas.sihproject.Utilities.MovableFloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.util.ArrayList;

public class PatientDashboardActivity extends AppCompatActivity {
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    String phoneNumber;
    SharedPreferences prefs;
    private ChildEventListener mChildEventListener;
    private DatabaseReference mDatabaseReference;
    private ArrayList<EventDetails> eventList;
    private PostAdapter adapter;
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_dashboard);
        prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        phoneNumber = prefs.getString("phone", null);
        eventList = new ArrayList<>();
        MovableFloatingActionButton addNewEventFab = findViewById(R.id.add_new_event_fab);
        addNewEventFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PatientDashboardActivity.this, AddEventActivity.class));
            }
        });
        adapter = new PostAdapter(eventList, getApplicationContext());
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_admin_club_list);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView.setAdapter(adapter);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("event");
    }

    @Override
    protected void onPause() {
        super.onPause();
        mChildEventListener = null;
        eventList.clear();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(mChildEventListener == null) {
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    try {
                        Log.d("database: ", dataSnapshot.toString());
                        EventDetails details = dataSnapshot.getValue(EventDetails.class);
                        eventList.add(details);
                        Toast.makeText(PatientDashboardActivity.this, String.valueOf(eventList.size()), Toast.LENGTH_SHORT).show();
                        adapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        Log.e("error", e.getMessage());
                    }
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };
            mDatabaseReference.addChildEventListener(mChildEventListener);
        }
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
                startActivity(new Intent(PatientDashboardActivity.this, MainActivity.class));
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}


