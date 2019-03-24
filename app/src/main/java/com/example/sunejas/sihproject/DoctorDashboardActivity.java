package com.example.sunejas.sihproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sunejas.sihproject.Adapter.PostAdapter;
import com.example.sunejas.sihproject.Models.EventDetails;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.util.ArrayList;

public class DoctorDashboardActivity extends AppCompatActivity {
    private ChildEventListener mChildEventListener;
    private DatabaseReference mDatabaseReference;
    private ArrayList<EventDetails> eventList, reviewedList;
    private PostAdapter adapter, reviewedAdapter;
    RecyclerView mRecyclerView, mReviewedRecyclerView;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    private long docPhone;
    SharedPreferences prefs;
    ImageView logoutButton;

    LinearLayout splReqLL, appointmentLL, tagsLL;
    Button viewCurrentCaseButton;
    TextView postsTextView, reviewedCaseTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        setContentView(R.layout.activity_doctor_dashboard);
        logoutButton = findViewById(R.id.logout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefs.edit().clear().apply();
                MDToast.makeText(DoctorDashboardActivity.this, "Logout Success", Toast.LENGTH_SHORT, MDToast.TYPE_SUCCESS).show();
                startActivity(new Intent(DoctorDashboardActivity.this, MainActivity.class));
                finish();
            }
        });

        splReqLL = findViewById(R.id.ll_spl_req);
        appointmentLL = findViewById(R.id.ll_appointment);
        tagsLL = findViewById(R.id.ll_tag);

        postsTextView = findViewById(R.id.tv_posts);
        postsTextView.setVisibility(View.GONE);

        reviewedCaseTextView = findViewById(R.id.tv_reviewed_cases);
        reviewedCaseTextView.setVisibility(View.GONE);

        splReqLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DoctorDashboardActivity.this, SpecialReqActivity.class));
            }
        });

        appointmentLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DoctorDashboardActivity.this, "Launch list of physical appointments", Toast.LENGTH_SHORT).show();
            }
        });

        tagsLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DoctorDashboardActivity.this, "Launch list of specialised tags", Toast.LENGTH_SHORT).show();
            }
        });


       // viewCurrentCaseButton = findViewById(R.id.btn_view_curr_case);



        eventList = new ArrayList<>();
        adapter = new PostAdapter(eventList, getApplicationContext());
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_admin_club_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView.setAdapter(adapter);

        String phone = prefs.getString("phone", null);
        docPhone = Long.parseLong(phone);

        reviewedList = new ArrayList<>();
        reviewedAdapter = new PostAdapter(reviewedList, getApplicationContext());
        mReviewedRecyclerView = (RecyclerView) findViewById(R.id.rv_reviewed_list);
        mReviewedRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mReviewedRecyclerView.setAdapter(reviewedAdapter);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("event");
    }

    @Override
    protected void onPause() {
        super.onPause();
        mChildEventListener = null;
        eventList.clear();
        reviewedList.clear();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mChildEventListener == null) {
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    try {
                        Log.d("database: ", dataSnapshot.toString());
                        EventDetails details = dataSnapshot.getValue(EventDetails.class);
                        details.setUserkey(dataSnapshot.getKey());
                        eventList.add(details);
                        postsTextView.setVisibility(View.VISIBLE);

                        if (details.getAssignedDoc() == docPhone) {
                            reviewedList.add(details);
                            reviewedCaseTextView.setVisibility(View.VISIBLE);
                            reviewedAdapter.notifyDataSetChanged();
                        }
                        Toast.makeText(DoctorDashboardActivity.this, String.valueOf(eventList.size()), Toast.LENGTH_SHORT).show();
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
        switch (item.getItemId()) {
            case R.id.menu_logout:
                prefs.edit().clear().apply();

                MDToast.makeText(DoctorDashboardActivity.this, "Logout Success", Toast.LENGTH_SHORT, MDToast.TYPE_SUCCESS).show();
                startActivity(new Intent(DoctorDashboardActivity.this, MainActivity.class));
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
