package com.example.phoneofriends.View.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.phoneofriends.R;

public class SignInActivity extends AppCompatActivity {

    private Button btnSignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        initView();

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initView() {
        btnSignIn = findViewById(R.id.SignInBtn);
    }
}
