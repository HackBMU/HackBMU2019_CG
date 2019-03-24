package com.example.sunejas.sihproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

import com.example.sunejas.sihproject.Adapter.DoctorAdapter;
import com.example.sunejas.sihproject.Models.DoctorDetails;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class FeaturedDoctorActivity extends AppCompatActivity {
    private ChildEventListener mChildEventListener;
    private DatabaseReference mDatabaseReference;
    private ArrayList<DoctorDetails> doctorList;
    private DoctorAdapter adapter;
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_featured_doctor);
        ImageView imageView=findViewById(R.id.back_btn);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        doctorList= new ArrayList<>();
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_featured_doctor_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("doctorDetails");
    }
    @Override
    protected void onPause() {
        super.onPause();
        mChildEventListener = null;
        doctorList.clear();
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
                        DoctorDetails details = dataSnapshot.getValue(DoctorDetails.class);
                        doctorList.add(details);
                        Log.d("prerna",String.valueOf(doctorList.size()));
                        adapter = new DoctorAdapter(doctorList, getApplicationContext());
                        mRecyclerView.setAdapter(adapter);

//                        Toast.makeText(FeaturedDoctorActivity.this, String.valueOf(doctorList.size()), Toast.LENGTH_SHORT).show();
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
}
