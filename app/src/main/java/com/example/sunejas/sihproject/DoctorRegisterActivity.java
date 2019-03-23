package com.example.sunejas.sihproject;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sunejas.sihproject.Fragments.FragmentOtpChecker;
import com.example.sunejas.sihproject.Models.DoctorDetails;
import com.example.sunejas.sihproject.Utilities.GetRoundedImage;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.util.ArrayList;
import java.util.List;

import static com.example.sunejas.sihproject.Fragments.FragmentOtpChecker.REQUEST_ID_MULTIPLE_PERMISSIONS;

public class DoctorRegisterActivity extends AppCompatActivity implements FragmentOtpChecker.otpCheckStatus{
    private EditText name, email, phone, city,field,specialization,licenseNumber,password;
    private CardView view;
    private ProgressDialog mProgress;
    private DoctorDetails doctorDetails;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_register);
        name = (EditText) findViewById(R.id.et_reg_name);
        email = (EditText) findViewById(R.id.et_reg_email);
        city = (EditText) findViewById(R.id.et_reg_city);
        phone = (EditText) findViewById(R.id.et_reg_mob);
        password = (EditText)findViewById(R.id.et_reg_password);
        field =(EditText)findViewById(R.id.et_reg_field);
        specialization =(EditText)findViewById(R.id.et_reg_specialisation);
        licenseNumber=(EditText)findViewById(R.id.et_reg_license);
        view =  findViewById(R.id.rl_main_view);
        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),R.drawable.ic_add_white);
        GetRoundedImage getRoundedImage=new GetRoundedImage();
        Bitmap circularBitmap =getRoundedImage.getRoundedShape(bitmap);

        ImageView circularImageView = (ImageView)findViewById(R.id.doctor_image);
        circularImageView.setImageBitmap(circularBitmap);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        mProgress = new ProgressDialog(this);
        mProgress.setMessage("Registering You");
        mProgress.setTitle("Please Wait");
        mProgress.setCanceledOnTouchOutside(false);
        doctorDetails = new DoctorDetails();
        TextView submit = (TextView) findViewById(R.id.tv_reg_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean checker = validateCredentials();
                if (checker) {
                    mProgress.show();
                    doctorDetails.setEmail(email.getText().toString());
                    doctorDetails.setmName(name.getText().toString());
                    doctorDetails.setmCity(city.getText().toString());
                    doctorDetails.setmPhone(phone.getText().toString());
                    doctorDetails.setmField(field.getText().toString());
                    doctorDetails.setmPassword(password.getText().toString());
                    doctorDetails.setmSpecialization(specialization.getText().toString());
                    doctorDetails.setmLicenseNumber(licenseNumber.getText().toString());
                    //MDToast.makeText(PatientRegisterActivity.this, "Done!", MDToast.LENGTH_SHORT, MDToast.TYPE_SUCCESS).show();
                    checkOTP(doctorDetails);
                }
            }
        });
    }
    private void checkOTP(DoctorDetails doctorDetails) {
        checkAndRequestPermissions();
        if(ContextCompat.checkSelfPermission(DoctorRegisterActivity.this, android.Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED){
            FragmentManager fm = getFragmentManager();
            FragmentOtpChecker otpChecker = new FragmentOtpChecker();
            Bundle bundle = new Bundle();
            bundle.putString("phone", phone.getText().toString());
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
        if (name.getText().toString().equals("")) {
            name.setError("Enter a User Name");
            return false;
        }
        if (password.getText().toString().equals("")) {
            name.setError("Enter a Password");
            return false;
        }
        if (email.getText().toString().equals("")) {
            email.setError("Enter an Email Address");
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
            email.setError("Enter a Valid Email Address");
            return false;
        }
        if (phone.getText().toString().equals("")) {
            phone.setError("Enter a Phone Number");
            return false;
        }
        if (!Patterns.PHONE.matcher(phone.getText().toString()).matches()) {
            phone.setError("Enter a valid Phone Number");
            return false;
        }
        if (phone.getText().toString().length() != 10) {
            phone.setError("Enter a valid Phone Number");
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
            register();
            MDToast.makeText(DoctorRegisterActivity.this, "Registered!", MDToast.LENGTH_SHORT, MDToast.TYPE_SUCCESS).show();
            mProgress.dismiss();
            startActivity(new Intent(DoctorRegisterActivity.this, DoctorDashboardActivity.class));
            finish();
        } else {
            mProgress.dismiss();
        }
    }

    public void register(){
        databaseReference.child("doctorDetails").child(doctorDetails.getmPhone()).setValue(doctorDetails);
        databaseReference.child("users").child(doctorDetails.getmPhone()).setValue("doctor");
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
            bundle.putString("phone", doctorDetails.getmPhone());
            otpChecker.setArguments(bundle);
            otpChecker.show(fm, "otpCheckerFragment");
        }
    }
}
