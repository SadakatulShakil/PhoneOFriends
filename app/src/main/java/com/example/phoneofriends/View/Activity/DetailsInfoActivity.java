package com.example.phoneofriends.View.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.phoneofriends.R;
import com.example.phoneofriends.View.Fragment.DetailsInfoFragment;
import com.example.phoneofriends.View.Fragment.HomeContainerFragment;

public class DetailsInfoActivity extends AppCompatActivity {
    private FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_info);

        inItView();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameForDetails, new DetailsInfoFragment())
                .commit();
    }

    private void inItView() {
        frameLayout = findViewById(R.id.frameForDetails);
    }
}
