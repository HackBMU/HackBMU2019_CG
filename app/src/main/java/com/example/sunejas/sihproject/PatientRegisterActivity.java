package com.example.sunejas.sihproject;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.sunejas.sihproject.Fragments.FragmentOtpChecker;
import com.example.sunejas.sihproject.Models.PatientDetails;
import com.example.sunejas.sihproject.Utilities.GetRoundedImage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.util.ArrayList;
import java.util.List;

import static com.example.sunejas.sihproject.Fragments.FragmentOtpChecker.REQUEST_ID_MULTIPLE_PERMISSIONS;

public class PatientRegisterActivity extends AppCompatActivity implements FragmentOtpChecker.otpCheckStatus {
    private EditText userName, userEmail, userPhone, userCity,userBloodGroup,userAge,userPassword;
    private CardView view;
    private ProgressDialog mProgress;
    private PatientDetails patientDetails;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_register);
        userName = (EditText) findViewById(R.id.et_reg_name);
        userEmail = (EditText) findViewById(R.id.et_reg_email);
        userCity = (EditText) findViewById(R.id.et_reg_city);
        userPhone = (EditText) findViewById(R.id.et_reg_mob);
        userPassword=(EditText)findViewById(R.id.et_reg_password);
        userBloodGroup=(EditText)findViewById(R.id.et_reg_blood_group);
        userAge=(EditText)findViewById(R.id.et_reg_patient_age);
        view = findViewById(R.id.rl_main_view);
        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),R.drawable.arrived_qr);
        GetRoundedImage getRoundedImage=new GetRoundedImage();
        Bitmap circularBitmap =getRoundedImage.getRoundedShape(bitmap);

        ImageView circularImageView = (ImageView)findViewById(R.id.doctor_image);
        circularImageView.setImageBitmap(circularBitmap);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        mProgress = new ProgressDialog(this);
        mProgress.setMessage("Registering You");
        mProgress.setTitle("Please Wait");
        mProgress.setCanceledOnTouchOutside(false);
        patientDetails = new PatientDetails();
        TextView submit = (TextView) findViewById(R.id.tv_reg_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean checker = validateCredentials();
                if (checker) {
                    mProgress.show();
                    FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( PatientRegisterActivity.this,  new OnSuccessListener<InstanceIdResult>() {
                        @Override
                        public void onSuccess(InstanceIdResult instanceIdResult) {
                            String newToken = instanceIdResult.getToken();
                            patientDetails.setmToken(newToken);
                            patientDetails.setEmail(userEmail.getText().toString());
                            patientDetails.setmName(userName.getText().toString());
                            patientDetails.setmCity(userCity.getText().toString());
                            patientDetails.setmPhone(userPhone.getText().toString());
                            patientDetails.setmBloodGroup(userBloodGroup.getText().toString());
                            patientDetails.setmAge(userAge.getText().toString());
                            patientDetails.setmPassword(userPassword.getText().toString());
                            //MDToast.makeText(PatientRegisterActivity.this, "Done!", MDToast.LENGTH_SHORT, MDToast.TYPE_SUCCESS).show();
                            checkOTP(patientDetails);

                        }
                    });
                }
            }
        });
    }
        private void checkOTP(PatientDetails patientDetails) {
            checkAndRequestPermissions();
            if(ContextCompat.checkSelfPermission(PatientRegisterActivity.this, android.Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED){
                FragmentManager fm = getFragmentManager();
                FragmentOtpChecker otpChecker = new FragmentOtpChecker();
                Bundle bundle = new Bundle();
                bundle.putString("phone", userPhone.getText().toString());
                otpChecker.setArguments(bundle);
                otpChecker.show(fm, "otpCheckerFragment");
            }
            mProgress.dismiss();
        }

        private Boolean validateCredentials() {
            if (!isNetworkAvailable()) {
                Snackbar.make(view, "Check your Internet Connection", Snackbar.LENGTH_SHORT).show();
                return false;
            }
            if (userName.getText().toString().equals("")) {
                userName.setError("Enter a User Name");
                return false;
            }
            if (userPassword.getText().toString().equals("")) {
                userName.setError("Enter a Password");
                return false;
            }
            if (userEmail.getText().toString().equals("")) {
                userEmail.setError("Enter an Email Address");
                return false;
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(userEmail.getText().toString()).matches()) {
                userEmail.setError("Enter a Valid Email Address");
                return false;
            }
            if (userPhone.getText().toString().equals("")) {
                userPhone.setError("Enter a Phone Number");
                return false;
            }
            if (!Patterns.PHONE.matcher(userPhone.getText().toString()).matches()) {
                userPhone.setError("Enter a valid Phone Number");
                return false;
            }
            if (userPhone.getText().toString().length() != 10) {
                userPhone.setError("Enter a valid Phone Number");
                return false;
            }
            if (userAge.getText().toString().equals("")) {
                userAge.setError("Enter a Age");
                return false;
            }
            if (userCity.getText().toString().equals("")) {
                userCity.setError("Enter a City");
                return false;
            }
            if (userBloodGroup.getText().toString().equals("")) {
                userBloodGroup.setError("Enter a Blood Group=");
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
        @Override
        public void updateResult(boolean status) {
        if (status) {
            registerUser();
        } else {
            mProgress.dismiss();
        }
        }

        public void registerUser(){
        databaseReference.child("patientDetails").child(patientDetails.getmPhone()).setValue(patientDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                databaseReference.child("users").child(patientDetails.getmPhone()).setValue("patient").addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        MDToast.makeText(PatientRegisterActivity.this, "Registered!", MDToast.LENGTH_SHORT, MDToast.TYPE_SUCCESS).show();
                        mProgress.dismiss();
                        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                        editor.putString("phone", patientDetails.getmPhone());
                        editor.putString("name",patientDetails.getmName());
                        editor.putString("role", "patient");
                        editor.apply();
                        startActivity(new Intent(PatientRegisterActivity.this, PatientDashboardActivity.class));
                        finishAffinity();
                    }
                });
            }
        });
        }
        private void checkAndRequestPermissions() {
            int receiveSMS = ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.RECEIVE_SMS);

            int readSMS = ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.READ_SMS);
            List<String> listPermissionsNeeded = new ArrayList<>();

            if (receiveSMS != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(android.Manifest.permission.RECEIVE_MMS);
            }
            if (readSMS != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(android.Manifest.permission.READ_SMS);
            }

            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(this,
                        listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                        REQUEST_ID_MULTIPLE_PERMISSIONS);
            }
        }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            if(requestCode == REQUEST_ID_MULTIPLE_PERMISSIONS){
                Bundle bundle = new Bundle();
                FragmentManager fm = getFragmentManager();
                FragmentOtpChecker otpChecker = new FragmentOtpChecker();
                bundle.putString("phone", patientDetails.getmPhone());
                otpChecker.setArguments(bundle);
                otpChecker.show(fm, "otpCheckerFragment");
            }
        }
}
