package com.example.sunejas.sihproject.ChatPage;

import android.content.Context;
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

import com.example.sunejas.sihproject.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class ChatActivity extends AppCompatActivity {

    public static final String MY_PREFS_NAME = "MyPrefsFile";
    private String phoneNumber;
    private DatabaseReference databaseReference;
    private RecyclerView recyclerView;
    Button sendBtn;
    ArrayList<ChatMessage> itemList = new ArrayList<>();
    private EditText inputTextBody;
    private ChildEventListener mChildEventListener;
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_chat2 );




        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
     //   phoneNumber = prefs.getString("phone", null);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.keepSynced(true);

        phoneNumber =  "7015202013";
        populateItem();

        Log.d( "Items",itemList.size()+"" );
        final MessageListAdapter itemArrayAdapter = new MessageListAdapter(getApplicationContext(),itemList);

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
                itemList.add(chat);
                sendMessage(chat);

                recyclerView.setAdapter(itemArrayAdapter);
                itemArrayAdapter.notifyDataSetChanged();
            }
        } );


    }

    public void sendMessage(ChatMessage chat){
        databaseReference.child("messages").child( phoneNumber ).child("pendingMsg").setValue(itemList);
        databaseReference.child("messages").child("7840844065").child("recievedMsg").setValue(itemList);
    }

    void populateItem()
    {
        itemList.add( new ChatMessage("sfaf","sfzfzc","sdfsfsf","sfsfsf",ChatMessage.ItemType.ONE_ITEM));
        itemList.add( new ChatMessage("asfa","afaf","afafaf","afaff",ChatMessage.ItemType.TWO_ITEM));
        itemList.add( new ChatMessage("sfaf","sfzfzc","sdfsfsf","sfsfsf",ChatMessage.ItemType.ONE_ITEM));
        itemList.add( new ChatMessage("asfa","afaf","afafaf","afaff",ChatMessage.ItemType.TWO_ITEM));
        itemList.add( new ChatMessage("sfaf","sfzfzc","sdfsfsf","sfsfsf",ChatMessage.ItemType.ONE_ITEM));
        itemList.add( new ChatMessage("asfa","afaf","afafaf","afaff",ChatMessage.ItemType.TWO_ITEM));


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
            databaseReference.removeEventListener(mChildEventListener);
            mChildEventListener = null;
        }

    }
    private void attachDatabaseListener() {
        if (mChildEventListener == null) {

            mChildEventListener = new ChildEventListener() {

                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

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
            databaseReference.addChildEventListener(mChildEventListener);
        }
    }
}
