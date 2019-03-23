package com.example.sunejas.sihproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;

public class DoctorDashboardActivity extends AppCompatActivity {
    private RecyclerView postsRecyclerView;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_dashboard);
        postsRecyclerView = (RecyclerView) findViewById(R.id.posts_recycler_view);

        postsRecyclerView.setHasFixedSize(true);

        postsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));

    }
}
