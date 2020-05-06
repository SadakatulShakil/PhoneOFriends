package com.example.phoneofriends.View.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.phoneofriends.R;
import com.example.phoneofriends.View.Fragment.HomeContainerFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity {

    private FrameLayout frameLayout;
    private String currentTime, currentDate;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference rootRef;
    private String currentUserId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        firebaseAuth = FirebaseAuth.getInstance();
        initViews();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutID, new HomeContainerFragment())
                .commit();

    }

    @Override
    protected void onStop() {
        super.onStop();
        //updateUserStatus("offffffffff");
    }

    private void initViews() {
            frameLayout = findViewById(R.id.frameLayoutID);
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
}
