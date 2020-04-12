package com.example.phoneofriends.View.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.phoneofriends.R;
import com.example.phoneofriends.View.Adapters.MessageAdapter;
import com.example.phoneofriends.View.Model.SingleChats;
import com.example.phoneofriends.View.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SingleChatBoardActivity extends AppCompatActivity {
    private FrameLayout frameLayout;
    private Toolbar dToolbar;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private User user;
    private ArrayList<SingleChats> singleChatList;
    private MessageAdapter messageAdapter;
    private DatabaseReference messageRef;
    private RecyclerView chatListRecycleView;
    private SingleChats singleChats;
    private EditText messageField;
    private String date;
    private String sendingTime;
    private FloatingActionButton sendMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_chat_board);

        initViews();


        dToolbar.setNavigationIcon(R.drawable.ic_back);
        dToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        final Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("userInfo");

        if (user != null) {
            String name = user.getUserName();
            dToolbar.setTitle(name);
        }
        singleChatList = new ArrayList<>();
        chatListRecycleView.setLayoutManager(new LinearLayoutManager(SingleChatBoardActivity.this));
        messageAdapter = new MessageAdapter(SingleChatBoardActivity.this, singleChatList);
        chatListRecycleView.setAdapter(messageAdapter);

        RetrievedAllChatData();

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                final String mSenderId = firebaseUser.getUid();
                final String mReceiverId = user.getUserId();

                date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat myDateFormat = new SimpleDateFormat("hh:mm a");
                sendingTime = myDateFormat.format(calendar.getTime());

                String message = messageField.getText().toString().trim();

                if (message.isEmpty()) {
                    messageField.setError("Empty message can't be sent !");
                    messageField.requestFocus();
                    return;
                }

                storeMessages(mSenderId, mReceiverId, message, date, sendingTime);

                messageField.setText("");
            }
        });
    }

    private void RetrievedAllChatData() {
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final String senderId = firebaseUser.getUid();
        messageRef = FirebaseDatabase.getInstance().getReference("Chats");

        messageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                singleChatList.clear();
                for(DataSnapshot chatSnapshot : dataSnapshot.getChildren()){
                    SingleChats singleChats = chatSnapshot.getValue(SingleChats.class);

                    if(singleChats.getSenderId().equals(senderId) && singleChats.getReceiverId().equals(user.getUserId()) ||
                        singleChats.getReceiverId().equals(senderId) && singleChats.getSenderId().equals(user.getUserId())){

                        singleChatList.add(singleChats);
                    }
                }
                messageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void storeMessages(final String mSenderId, final String mReceiverId, final String message,
                               final String date, final String sendingTime) {

        messageRef = FirebaseDatabase.getInstance().getReference("Chats");
        String pushId = messageRef.push().getKey();

        singleChats = new SingleChats(mSenderId, mReceiverId, message, date, sendingTime);

        messageRef.child(pushId).setValue(singleChats).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(SingleChatBoardActivity.this, "Successful!", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SingleChatBoardActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initViews() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dToolbar = findViewById(R.id.toolbar);
        }
        messageField = findViewById(R.id.messageFieldET);
        sendMessage = findViewById(R.id.sendMessage);
        chatListRecycleView = findViewById(R.id.chatDataRecycleView);
    }
}
