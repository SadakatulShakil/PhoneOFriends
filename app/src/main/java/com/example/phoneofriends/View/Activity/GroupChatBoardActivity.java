package com.example.phoneofriends.View.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.phoneofriends.R;
import com.example.phoneofriends.View.Adapters.GroupMessageAdapter;
import com.example.phoneofriends.View.Adapters.MessageAdapter;
import com.example.phoneofriends.View.Model.GroupChats;
import com.example.phoneofriends.View.Model.GroupDetail;
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

public class GroupChatBoardActivity extends AppCompatActivity {
    private Toolbar dToolbar;
    private GroupDetail groupDetail;
    private FirebaseUser firebaseUser;
    private DatabaseReference groupMessageRef;
    private User user;
    private ArrayList<GroupChats> groupChatList;
    private EditText messageField;
    private RecyclerView groupChatListRecycleView;
    private GroupChats groupChats;
    private GroupMessageAdapter groupMessageAdapter;
    private String date;
    private String sendingTime;
    private FloatingActionButton sendMessage;
    private static final String TAG = "GroupChatBoardActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat_board);

        inItView();

        dToolbar.setNavigationIcon(R.drawable.ic_back);
        dToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        final Intent intent = getIntent();
        groupDetail = (GroupDetail) intent.getSerializableExtra("groupInfo");

        if (groupDetail != null) {
            String name = groupDetail.getGroupName();
            dToolbar.setTitle(name);
        }
        groupChatList = new ArrayList<>();
        groupChatListRecycleView.setLayoutManager(new LinearLayoutManager(GroupChatBoardActivity.this));
        groupMessageAdapter = new GroupMessageAdapter(GroupChatBoardActivity.this, groupChatList);
        groupChatListRecycleView.setAdapter(groupMessageAdapter);

        RetrievedAllGroupChatData();

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                final String mSenderId = firebaseUser.getUid();
                final String mGroupId = groupDetail.getGroupId();
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

                storeGroupMessage(mGroupId, mSenderId, message,date,sendingTime);

                messageField.setText("");
            }
        });
    }

    private void RetrievedAllGroupChatData() {

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final String senderId = firebaseUser.getUid();
        groupMessageRef = FirebaseDatabase.getInstance().getReference("GroupChats");

        groupMessageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                groupChatList.clear();
                for(DataSnapshot chatSnapshot : dataSnapshot.getChildren()){
                    GroupChats groupChats = chatSnapshot.getValue(GroupChats.class);
                    if(groupDetail.getGroupId().equals(groupChats.getGroupId())){

                        groupChatList.add(groupChats);
                        Log.d(TAG, "onCheck: message :" + groupChats.getMessages());
                    }
                }
                //adapter setNotifyChanged//
                groupMessageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void storeGroupMessage(final String mGroupId, final String mSenderId,
                                   final String message, final String date, final String sendingTime) {

        groupMessageRef = FirebaseDatabase.getInstance().getReference("GroupChats");
        String pushGroupId = groupMessageRef.push().getKey();

        GroupChats groupChats = new GroupChats(mGroupId, mSenderId, message, date, sendingTime);

        groupMessageRef.child(pushGroupId).setValue(groupChats).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(GroupChatBoardActivity.this, "Successful!", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(GroupChatBoardActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void inItView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dToolbar = findViewById(R.id.toolbar);
        }
        messageField = findViewById(R.id.messageFieldET);
        sendMessage = findViewById(R.id.sendMessage);
        groupChatListRecycleView = findViewById(R.id.chatDataRecycleView);
    }
}
