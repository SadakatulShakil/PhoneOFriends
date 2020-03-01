package com.example.phoneofriends.View.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.phoneofriends.R;
import com.example.phoneofriends.View.Fragment.HomeContainerFragment;

public class HomeActivity extends AppCompatActivity {

    private FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initViews();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutID, new HomeContainerFragment())
                .commit();
    }

    private void initViews() {
            frameLayout = findViewById(R.id.frameLayoutID);
    }
}
