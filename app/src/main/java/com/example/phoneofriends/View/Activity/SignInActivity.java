package com.example.phoneofriends.View.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phoneofriends.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class SignInActivity extends AppCompatActivity {

    private Button btnSignIn;
    private TextView btnSignUp;
    private ProgressBar progressBar;
    private EditText emailET, passwordET;
    private FirebaseAuth firebaseAuth;
    private String currentTime, currentDate;
    private DatabaseReference rootRef;
    private String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        firebaseAuth = FirebaseAuth.getInstance();
        initView();

        if (firebaseAuth.getCurrentUser() != null) {
            Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
            updateUserStatus("online");
        }

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userValidation();
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

    }



    private void updateUserStatus(String state) {

        currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        firebaseAuth = FirebaseAuth.getInstance();
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

    private void userValidation() {
        String email = emailET.getText().toString().trim();
        String password = passwordET.getText().toString().trim();

        if (email.isEmpty()) {
            Toast.makeText(SignInActivity.this, "Email is required!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.isEmpty()) {
            Toast.makeText(SignInActivity.this, "Password is Required!", Toast.LENGTH_SHORT).show();
            return;
        }

        signIn(email, password);
    }

    private void signIn(String email, String password) {
        progressBar.setVisibility(View.VISIBLE);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(SignInActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                });
    }


    private void initView() {
        btnSignIn = findViewById(R.id.SignInBtn);
        btnSignUp = findViewById(R.id.signUpBtn);
        emailET = findViewById(R.id.userEmail);
        passwordET = findViewById(R.id.userPassword);
        progressBar = findViewById(R.id.progressBar);
    }
}
