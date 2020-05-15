package com.example.phoneofriends.View.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baoyachi.stepview.HorizontalStepView;
import com.baoyachi.stepview.bean.StepBean;
import com.example.phoneofriends.R;
import com.example.phoneofriends.View.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {
    private Toolbar dToolbar;
    private ImageView closeNote;
    private LinearLayout noteLayOut;
    private TextView btAbout;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference userRef;
    private User user;
    private TextView tvName, tvEmail, tvContact, tvBirthday, tvGender;

    private static final String TAG = "ProfileActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        inItView();

        dToolbar.setTitle("Profile");

        dToolbar.setNavigationIcon(R.drawable.ic_back);
        dToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        closeNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               noteLayOut.setVisibility(View.GONE);
            }
        });

        btAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, DetailsInfoActivity.class);
                startActivity(intent);
            }
        });
        //stepBarView();
        retrievedInfo();
    }

    private void retrievedInfo() {

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        final String userId = firebaseUser.getUid();

        userRef = FirebaseDatabase.getInstance().getReference("User");

        userRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    user = dataSnapshot.getValue(User.class);
                    Log.d(TAG, "onUserData: " + user.toString());
                    tvName.setText(user.getUserName());
                    tvEmail.setText(user.getUserEmail());
                    tvContact.setText(user.getUserContact());
                    tvBirthday.setText(user.getUserDateOfBirth());
                    tvGender.setText(user.getUserGender());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void inItView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dToolbar = findViewById(R.id.toolbar);
        }
        closeNote = findViewById(R.id.notifyClose);
        noteLayOut = findViewById(R.id.notificationLayout);
        btAbout = findViewById(R.id.about);

        tvName = findViewById(R.id.userName);
        tvEmail = findViewById(R.id.emailTv);
        tvContact = findViewById(R.id.contactTv);
        tvBirthday = findViewById(R.id.birthDayTv);
        tvGender = findViewById(R.id.genderTv);
    }
}
