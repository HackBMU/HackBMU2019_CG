package com.example.sunejas.sihproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sunejas.sihproject.ChatPage.ChatActivity;
import com.example.sunejas.sihproject.Models.EventDetails;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.example.sunejas.sihproject.DoctorDashboardActivity.MY_PREFS_NAME;

public class SingleEventDoctorActivity extends AppCompatActivity {
   DatabaseReference mdatabase;
    SharedPreferences pref;
   TextView date;
   ImageView backButton;
    private ChildEventListener mChildEventListener;
    ImageView chat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_event_doctor);
        backButton=findViewById( R.id.back_btn );
        backButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        } );
        mdatabase = FirebaseDatabase.getInstance().getReference().child( "event" );
        final Intent i = getIntent();
        final String id = i.getStringExtra( "userid" );
        EditText pres=findViewById(R.id.et_about_problem);
        Button presc=findViewById(R.id.but_pres);
        final TextView title = findViewById( R.id.ans1 );
        final TextView comments = findViewById( R.id.ans2 );
        final ImageView closeup1 = findViewById( R.id.iv_closeup_back );
        final ImageView overview1 = findViewById( R.id.iv_overview_back );
        date = findViewById( R.id.today_date );
        chat=findViewById(R.id.chat);
        pref = this.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        final String name = pref.getString("name", null);
        final String phone = pref.getString("phone", null);
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SingleEventDoctorActivity.this, ChatActivity.class);
                intent.putExtra("tag","d");

                startActivity(intent);
            }
        });
        String c;
        String a;

        final TextView alert = findViewById( R.id.ans3 );
        // Toast.makeText(this,id,Toast.LENGTH_LONG).show();
        EventDetails event = new EventDetails();
        final String prescription=pres.getText().toString().trim().toLowerCase();
         final ArrayList<String> prescribed_medicines=new ArrayList<>(10);
        prescribed_medicines.add("aceclofenac+Paracetamol750");
        prescribed_medicines.add("aceclofenac+paracetamol+famotidine744");
        prescribed_medicines.add("aceclofenac+paracetamol+rabeprazole705");
        prescribed_medicines.add("aceclofenac+paracetamol+rabeprazole705");
        prescribed_medicines.add("acetaminophen+guaifenesin+dextromethorphan+chlorpheniramine945");
        prescribed_medicines.add("acetaminophen+loratadine+ambroxol+phenylephrine906");
        prescribed_medicines.add("acriflavine+thymol+cetrimide1014");
        prescribed_medicines.add("acrivastine+paracetamol+caffeine+phenylephrine915");
        prescribed_medicines.add("albuterol+bromhexine+theophylline1033");
        prescribed_medicines.add("albuterol+etofylline+bromhexine+menthol1032");

      pres.setOnClickListener(new View.OnClickListener() {

          @Override
          public void onClick(View view) {
              for(String med : prescribed_medicines){
                  if(med.equals(prescription)) {
                      Toast.makeText(getApplicationContext(), "Medicine is banned!", Toast.LENGTH_SHORT).show();
                      break;
                  }

              }
          }
      });
        for(String med : prescribed_medicines){
            if(!med.equals(prescription)) {
                Toast.makeText(this, "Medicine is banned!", Toast.LENGTH_SHORT).show();
                break;
            }
        }



        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                try {


                    EventDetails details = dataSnapshot.getValue(EventDetails.class);
                    details.setUserkey(dataSnapshot.getKey());
                    String m=details.getUserkey();
                    //Toast.makeText(getApplicationContext(),m,Toast.LENGTH_LONG).show();
                    if(details.getUserkey().equals(id)) {
                        String t = details.getName();
                        String c = details.getComment();
                        String a = details.getAllergies();
                        String closeup = details.getCloseupImage();
                        long d=details.getDate();
                        String d1=String.valueOf(d);
                        String overview = details.getOverviewImage();
                        title.setText(t);
                        comments.setText(c);
                        alert.setText(a);
                        date.setText(d1);

                        Picasso.get().load(closeup).into(closeup1);
                        Picasso.get().load(overview).into(overview1);

                    }

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
    mdatabase.addChildEventListener(mChildEventListener);
    }
}
