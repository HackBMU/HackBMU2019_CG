package com.example.sunejas.sihproject.ChatPage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sunejas.sihproject.DoctorDashboardActivity;
import com.example.sunejas.sihproject.MainActivity;
import com.example.sunejas.sihproject.Models.EventDetails;
import com.example.sunejas.sihproject.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.valdesekamdem.library.mdtoast.MDToast;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class ChatActivity extends AppCompatActivity {

    public static final String MY_PREFS_NAME = "MyPrefsFile";
    private String phoneNumber,sentNo;
    private DatabaseReference databaseReference;
    private RecyclerView recyclerView;
    Button sendBtn;
    ArrayList<ChatMessage> itemList = new ArrayList<>();
    ArrayList<ChatMessage> itemListReciever = new ArrayList<>();
    ArrayList<ChatMessage> itemListSender = new ArrayList<>();
    private EditText inputTextBody;
    private ChildEventListener mChildEventListener;
    private Context mContext;
    private DatabaseReference mdatabase;
    private MessageListAdapter itemArrayAdapter;
    private View logoutButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_chat2 );
        Intent i=getIntent();
        String check=i.getStringExtra("tag");

        if(check.equals("d"))
        {
            phoneNumber="9467557310";
            sentNo = "7015202013";
        }
        else
        {
            sentNo="9467557310";
            phoneNumber = "7015202013";

        }


        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        phoneNumber = prefs.getString("phone", null);
        mdatabase = FirebaseDatabase.getInstance().getReference().child( "messages" ).child( phoneNumber ).child( "chatHistory" );
        databaseReference = FirebaseDatabase.getInstance().getReference();
        //databaseReference.keepSynced(true);


        logoutButton = findViewById(R.id.back_btn_chat);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             onBackPressed();
            }
        });

        Log.d( "Items",itemList.size()+"" );
        itemArrayAdapter = new MessageListAdapter(getApplicationContext(),itemList);

        recyclerView = (RecyclerView) findViewById(R.id.chat_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(ChatActivity.this));
        recyclerView.setAdapter(itemArrayAdapter);

        sendBtn = findViewById( R.id.btnSend );
        inputTextBody = findViewById( R.id.inputMsg );

        sendBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date currentTime = Calendar.getInstance().getTime();
                ChatMessage chat = new ChatMessage(inputTextBody.getText().toString()+"",currentTime.toString()+"",phoneNumber,"7840844065",ChatMessage.ItemType.TWO_ITEM);
                ChatMessage chatReciever = new ChatMessage(inputTextBody.getText().toString()+"",currentTime.toString()+"",phoneNumber,"7840844065",ChatMessage.ItemType.ONE_ITEM);
                itemListSender.add(chat);
                itemListReciever.add( chatReciever );
                sendMessage( );
//                sendMessage(chat);

               // recyclerView.setAdapter(itemArrayAdapter);
                //itemArrayAdapter.notifyDataSetChanged();
            }
        } );


    }

    public void sendMessage(){
        ArrayList<ChatMessage> itemL=new ArrayList<ChatMessage>();
        ArrayList<ChatMessage> temp= new ArrayList<ChatMessage>();
        itemL.addAll( itemList );
        temp.addAll( itemList );
        itemL.addAll( itemListSender );
        temp.addAll( itemListReciever );

        databaseReference.child("messages").child( phoneNumber ).child("chatHistory").setValue(itemL);
        databaseReference.child("messages").child(sentNo).child("chatHistory").setValue(temp);
        itemListSender.clear();
        itemListReciever.clear();
    }



    @Override
    public void onResume() {
        super.onResume();
        attachDatabaseListener();
    }

    @Override
    public void onPause() {
        super.onPause();
        detatchDatabaseListener();
    }


    private void detatchDatabaseListener() {
        if (mChildEventListener != null) {
            mdatabase.removeEventListener(mChildEventListener);
            mChildEventListener = null;
        }

    }
    private void attachDatabaseListener() {
        if (mChildEventListener == null) {

            mChildEventListener = new ChildEventListener() {

                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    try {
                      ChatMessage details = dataSnapshot.getValue( ChatMessage.class );
                      itemList.add( details );
                      Log.d( "Chat_tag",itemList.size()+"" );

                        recyclerView.setAdapter(itemArrayAdapter);
                        itemArrayAdapter.notifyDataSetChanged();
                    }
                    catch (Exception e)
                    {e.printStackTrace();

                    }

                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    try {
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            };
            mdatabase.addChildEventListener(mChildEventListener);

        }
    }
}
