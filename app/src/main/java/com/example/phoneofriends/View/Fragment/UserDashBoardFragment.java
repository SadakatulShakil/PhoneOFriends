package com.example.phoneofriends.View.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.phoneofriends.R;
import com.example.phoneofriends.View.Activity.GroupChatActivity;
import com.example.phoneofriends.View.Activity.ProfileActivity;
import com.example.phoneofriends.View.Activity.SingleChatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserDashBoardFragment extends Fragment {
    private Context context;
    private CardView singleChatView, groupChatView, globalChatView, profileView;
    private String currentTime, currentDate;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference rootRef;
    private String currentUserId;
    public UserDashBoardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_dash_board, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        inItView(view);
        //updateUserStatus("online");

    }

    @Override
    public void onStart() {
        super.onStart();
        singleChatView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, SingleChatActivity.class);
                context.startActivity(intent);
            }
        });

        groupChatView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, GroupChatActivity.class);
                context.startActivity(intent);
            }
        });

        profileView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, ProfileActivity.class);
                context.startActivity(intent);
            }
        });
    }


    @Override
    public void onStop() {
        super.onStop();
        if(currentUserId != null){

            //updateUserStatus("offffffffffff");
        }
    }

    private void updateUserStatus(String state) {

        currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat myDateFormat = new SimpleDateFormat("hh:mm a");
        currentTime = myDateFormat.format(calendar.getTime());

        HashMap<String, Object> onlineStatusMap = new HashMap<>();
        onlineStatusMap.put("time", currentTime);
        onlineStatusMap.put("date", currentDate);
        onlineStatusMap.put("status", state);

        currentUserId = firebaseAuth.getCurrentUser().getUid();
        rootRef = FirebaseDatabase.getInstance().getReference("User");
        rootRef.child(currentUserId).child("UserState")
                .updateChildren(onlineStatusMap);

    }

    private void inItView(View view) {
        singleChatView = view.findViewById(R.id.chatStartButton);
        groupChatView = view.findViewById(R.id.groupChatButton);
        globalChatView = view.findViewById(R.id.globalGroupButton);
        profileView = view.findViewById(R.id.profileButton);
    }
}
