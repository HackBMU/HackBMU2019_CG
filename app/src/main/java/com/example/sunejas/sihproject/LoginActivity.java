package com.example.sunejas.sihproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sunejas.sihproject.Models.DoctorDetails;
import com.example.sunejas.sihproject.Models.PatientDetails;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.valdesekamdem.library.mdtoast.MDToast;

public class LoginActivity extends AppCompatActivity {
    EditText phoneNumber,password;
    TextView submit;
    String pass;
    private DatabaseReference databaseReference;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        phoneNumber = (EditText) findViewById(R.id.et_reg_mob);
        password =(EditText)findViewById(R.id.et_reg_password);
        submit = (TextView) findViewById(R.id.tv_reg_submit);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                pass = password.getText().toString();
                final int[] flag = {0};
                final String phone = phoneNumber.getText().toString();
                databaseReference.child("users")
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (final DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    String p=snapshot.getKey();
                                    String role = snapshot.getValue(String.class);
                                    if(p.equals(phone)){
                                        if(role.equals("patient")) {
                                            databaseReference.child("patientDetails").child(p).addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    PatientDetails patientDetails = dataSnapshot.getValue(PatientDetails.class);
                                                    Log.d("prerna",patientDetails.getmPassword());
                                                    if(patientDetails.getmPassword().equals(pass)){
                                                        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                                                        editor.putString("phone",phone);
                                                        editor.putString("role","patient");
                                                        editor.apply();
                                                        startActivity(new Intent(LoginActivity.this, PatientDashboardActivity.class));
                                                        finish();
                                                    }else{
                                                        MDToast.makeText(LoginActivity.this, "Incorrect password", Toast.LENGTH_SHORT, MDToast.TYPE_ERROR).show();

                                                    }
                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            });
                                        }
                                        if(role.equals("doctor")){
                                            databaseReference.child("doctorDetails").child(p).addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    DoctorDetails doctorDetails = dataSnapshot.getValue(DoctorDetails.class);
                                                    if(doctorDetails.getmPassword().equals(pass)){
                                                        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                                                        editor.putString("phone",phone);
                                                        editor.putString("role","doctor");
                                                        editor.apply();
                                                        startActivity(new Intent(LoginActivity.this,DoctorDashboardActivity.class));
                                                        finish();
                                                    }else{
                                                        MDToast.makeText(LoginActivity.this, "Incorrect password", Toast.LENGTH_SHORT, MDToast.TYPE_ERROR).show();
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            });
                                        }
                                    }
                                }
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });
            }
        });
    }
}
