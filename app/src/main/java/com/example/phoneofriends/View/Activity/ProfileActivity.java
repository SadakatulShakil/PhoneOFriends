package com.example.phoneofriends.View.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baoyachi.stepview.HorizontalStepView;
import com.baoyachi.stepview.bean.StepBean;
import com.example.phoneofriends.R;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {
    private Toolbar dToolbar;
    private ImageView closeNote;
    private LinearLayout noteLayOut;
    private TextView btAbout;
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
    }


    private void inItView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dToolbar = findViewById(R.id.toolbar);
        }
        closeNote = findViewById(R.id.notifyClose);
        noteLayOut = findViewById(R.id.notificationLayout);
        btAbout = findViewById(R.id.about);
    }
}
