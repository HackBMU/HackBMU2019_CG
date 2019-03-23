package com.example.sunejas.sihproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sunejas.sihproject.Models.EventDetails;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddEventActivity extends AppCompatActivity {
    TextView currentDate, days, weeks, months, years, male, female;
    EditText title, aboutProblem, allergies;
    private ProgressDialog progress;
    ImageView problemImage;
    final int REQUEST_CODE_GALLERY = 999;
    int n = 0, x = 0;
    Button postButton;
    Uri overviewUri, closeupUri;

    StorageReference filepathOverview, filepathCloseup;
    EventDetails eventDetails;
    private DatabaseReference databaseReference;
    private StorageReference mStorage;
    RelativeLayout closeup, overview;
    String phoneNumber;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    int RESULT_GALLERY;
    private ImageView ivCloseupBack, ivOverviewBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        title = findViewById(R.id.et_title);
        progress = new ProgressDialog(this);
        mStorage = FirebaseStorage.getInstance().getReference();
        currentDate = findViewById(R.id.today_date);
        aboutProblem = findViewById(R.id.et_about_problem);
        allergies = findViewById(R.id.et_allergies);
        days = findViewById(R.id.tv_days);
        weeks = findViewById(R.id.tv_weeks);
        months = findViewById(R.id.tv_months);
        years = findViewById(R.id.tv_years);
        postButton = findViewById(R.id.button_post);
        closeup = findViewById(R.id.rl_closeup);
        overview = findViewById(R.id.rl_overview);

        ivCloseupBack = findViewById(R.id.iv_closeup_back);
        ivOverviewBack = findViewById(R.id.iv_overview_back);

        eventDetails = new EventDetails();
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        phoneNumber = prefs.getString("phone", null);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        final Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        final String formattedDate = df.format(c);
        currentDate.setText(formattedDate);
        days.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                days.setBackgroundColor(Color.CYAN);
                weeks.setBackgroundColor(Color.WHITE);
                months.setBackgroundColor(Color.WHITE);
                years.setBackgroundColor(Color.WHITE);
                n = 1;
            }
        });
        weeks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weeks.setBackgroundColor(Color.CYAN);
                days.setBackgroundColor(Color.WHITE);
                months.setBackgroundColor(Color.WHITE);
                years.setBackgroundColor(Color.WHITE);
                n = 2;
            }
        });

        months.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                months.setBackgroundColor(Color.CYAN);
                weeks.setBackgroundColor(Color.WHITE);
                days.setBackgroundColor(Color.WHITE);
                years.setBackgroundColor(Color.WHITE);
                n = 3;
            }
        });

        years.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                years.setBackgroundColor(Color.CYAN);
                weeks.setBackgroundColor(Color.WHITE);
                months.setBackgroundColor(Color.WHITE);
                days.setBackgroundColor(Color.WHITE);
                n = 4;
            }
        });

//        male.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                male.setBackgroundColor(Color.CYAN);
//                female.setBackgroundColor(Color.WHITE);
//                x = 1;
//            }
//        });
//        female.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                male.setBackgroundColor(Color.WHITE);
//                female.setBackgroundColor(Color.CYAN);
//                x = 2;
//            }
//        });
        closeup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RESULT_GALLERY = 0;
                Intent galleryIntent = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_GALLERY);
            }
        });
        overview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RESULT_GALLERY = 1;
                Intent galleryIntent = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_GALLERY);
            }
        });
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!checkCredentials()) {
                    return;
                }

                progress.setMessage("Uploading");
                progress.show();

//Overview Image

                filepathOverview = mStorage.child(phoneNumber).child(overviewUri.getLastPathSegment());

                UploadTask uploadTask1 = filepathOverview.putFile(overviewUri);

                uploadTask1.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Log.e("Overview ", exception+"");
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(AddEventActivity.this, "Image Upload Successful", Toast.LENGTH_LONG).show();
                    }
                });

                Task<Uri> urlTask1= uploadTask1.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        return filepathOverview.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUri = task.getResult();

                            if (downloadUri != null) {
                                eventDetails.setOverviewImage(downloadUri.toString());
                            }
                            Log.e("uriOverview",eventDetails.getOverviewImage());
                            filepathCloseup = mStorage.child(phoneNumber).child(closeupUri.getLastPathSegment());

                            UploadTask uploadTask2 = filepathCloseup.putFile(closeupUri);

                            uploadTask2.addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    // Handle unsuccessful uploads
                                    Log.e("Closeup ", exception+"");
                                }
                            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Toast.makeText(AddEventActivity.this, "Image Upload Successful", Toast.LENGTH_LONG).show();

                                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                                    // ...
                                }
                            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                }
                            });

                            Task<Uri> urlTask2 = uploadTask2.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                @Override
                                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task2) throws Exception {
                                    if (!task2.isSuccessful()) {
                                        throw task2.getException();
                                    }
                                    return filepathCloseup.getDownloadUrl();
                                }
                            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task2) {
                                    if (task2.isSuccessful()) {
                                        Uri downloadUri = task2.getResult();
                                        Log.e("uriCloseup",downloadUri.toString());
                                        eventDetails.setCloseupImage(downloadUri.toString());
                                        eventDetails.setDate(c.getTime());
                                        eventDetails.setComment(aboutProblem.getText().toString());
                                        eventDetails.setAllergies(allergies.getText().toString());

                                        eventDetails.setUserId(Long.parseLong(phoneNumber));
                                        if (n == 1) {
                                            eventDetails.setDuration("Days");
                                        } else if (n == 2) {
                                            eventDetails.setDuration("Weeks");
                                        } else if (n == 3) {
                                            eventDetails.setDuration("Months");
                                        } else {
                                            eventDetails.setDuration("Years");
                                        }
                                        String eventId = databaseReference.child("event").push().getKey();
                                        databaseReference.child("event").child(eventId).setValue(eventDetails);
                                        databaseReference.child("patientDetails").child(phoneNumber).child("posts").child(eventId).setValue(eventId);
                                        progress.hide();
                                        startActivity(new Intent(AddEventActivity.this, PatientDashboardActivity.class));
                                        finish();
                                        MDToast.makeText(AddEventActivity.this, "Posted Successfully", MDToast.LENGTH_SHORT, MDToast.TYPE_SUCCESS).show();
                                    } else {
                                        // Handle failures
                                        // ...
                                    }
                                }
                            });

                        } else {
                        }
                    }
                });


//Closeup Image





            }
        });

    }

    boolean checkCredentials() {

        if (title.getText().toString().trim().equals("")) {
            title.setError("enter title of problems");
            return false;
        }

        if (aboutProblem.getText().toString().trim().equals("")) {
            aboutProblem.setError("enter problem description");
            return false;
        }

        if (allergies.getText().toString().trim().equals("")) {
            allergies.setError("enter allergies");
            return false;
        }



        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0 && resultCode == RESULT_OK && data != null) {
            overviewUri = data.getData();
            Picasso.get().load(overviewUri).into(ivCloseupBack);
        }
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            closeupUri = data.getData();
            Picasso.get().load(closeupUri).into(ivOverviewBack);

        }
    }
}
