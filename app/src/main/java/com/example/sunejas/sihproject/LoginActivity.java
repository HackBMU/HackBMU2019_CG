package com.example.sunejas.sihproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
    EditText phoneNumber, password;
    TextView submit,signUp;
    ProgressDialog pd;
    String pass;
    Integer checker = 0;

    private DatabaseReference databaseReference;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    private CardView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        phoneNumber = (EditText) findViewById(R.id.et_reg_mob);
        pd = new ProgressDialog(this);
        password = (EditText) findViewById(R.id.et_reg_password);
        submit = (TextView) findViewById(R.id.tv_reg_submit);
        view = findViewById(R.id.rl_main_view);
        ImageView imageView = (ImageView) findViewById(R.id.doctor_image);
        signUp=findViewById(R.id.sign_up);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
            }
        });
        databaseReference = FirebaseDatabase.getInstance().getReference();
        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Boolean check = validateCredentials();
                if (check) {
                    pass = password.getText().toString();
                    pd.setMessage("wait please");
                    pd.show();
                    final String phone = phoneNumber.getText().toString();
                    databaseReference.child("users")
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (final DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                        String p = snapshot.getKey();
                                        String role = snapshot.getValue(String.class);
                                        if (p.equals(phone)) {
                                            if (role.equals("patient")) {
                                                databaseReference.child("patientDetails").child(p).addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                        PatientDetails patientDetails = dataSnapshot.getValue(PatientDetails.class);
                                                        Log.d("prerna", patientDetails.getmPassword());
                                                        if (patientDetails.getmPassword().equals(pass)) {
                                                            SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                                                            editor.putString("phone", phone);
                                                            editor.putString("role", "patient");
                                                            editor.putString("name",patientDetails.getmName());
                                                            //Log.d("prerna",patientDetails.getmName());
                                                            editor.apply();

                                                            checker = 1;

                                                            pd.hide();

                                                            startActivity(new Intent(LoginActivity.this, PatientDashboardActivity.class));
                                                            finishAffinity();

                                                        } else {
                                                            pd.hide();
                                                            MDToast.makeText(LoginActivity.this, "Incorrect password", Toast.LENGTH_SHORT, MDToast.TYPE_ERROR).show();
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(DatabaseError databaseError) {

                                                    }
                                                });
                                            } else if (role.equals("doctor")) {
                                                databaseReference.child("doctorDetails").child(p).addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                        DoctorDetails doctorDetails = dataSnapshot.getValue(DoctorDetails.class);
                                                        if (doctorDetails.getmPassword().equals(pass)) {
                                                            SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                                                            editor.putString("phone", phone);
                                                            editor.putString("role", "doctor");
                                                            editor.putString("name",doctorDetails.getmName());
                                                            //Log.d("prerna", doctorDetails.getmName());
                                                            editor.apply();

                                                            checker = 1;

                                                            pd.hide();
                                                            startActivity(new Intent(LoginActivity.this, DoctorDashboardActivity.class));
                                                            finishAffinity();
                                                        } else {
                                                            pd.hide();
                                                            MDToast.makeText(LoginActivity.this, "Incorrect password", Toast.LENGTH_SHORT, MDToast.TYPE_ERROR).show();
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(DatabaseError databaseError) {

                                                    }
                                                });
                                            }
                                        } else {
                                            Log.e("TAG", "Phone does not exist");
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                }
                            });
                }
            }
        });


    }

    private Boolean validateCredentials() {
        if (!isNetworkAvailable()) {
            Snackbar.make(view, "Check your Internet Connection", Snackbar.LENGTH_SHORT).show();
            return false;
        }
        if (phoneNumber.getText().toString().equals("")) {
            phoneNumber.setError("Enter a Phone Number");
            return false;
        }
        if (password.getText().toString().equals("")) {
            password.setError("Enter a Password");
            return false;
        }
        return true;
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}