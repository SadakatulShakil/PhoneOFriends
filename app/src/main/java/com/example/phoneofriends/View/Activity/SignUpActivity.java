package com.example.phoneofriends.View.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phoneofriends.R;
import com.example.phoneofriends.View.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SignUpActivity extends AppCompatActivity {
    private ImageView backArrow;
    private EditText nameET, emailET, contactET, passwordET;
    protected static TextView userBirthDate;
    private String userGender;
    private String userId;
    private Button signUpBtn;
    public static final String EMAIL_PATTERN = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        final FragmentManager fm = getSupportFragmentManager();
        inItView();

        userBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // create the datePickerFragment
                AppCompatDialogFragment newFragment = new DatePickerFragment1();

                newFragment.show(fm, "datePicker");

            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                beforeSignUp();
            }
        });
    }

    private void beforeSignUp() {
        final RadioGroup rbg=(RadioGroup) findViewById(R.id.gender);
        int selected = rbg.getCheckedRadioButtonId();
        RadioButton gender = findViewById(selected);


        String nameId = nameET.getText().toString().trim();
        String emailId = emailET.getText().toString().trim();
        String contactId = contactET.getText().toString().trim();
        String passwordId = passwordET.getText().toString().trim();
        String birthdayId = userBirthDate.getText().toString().trim();
        userGender = (String) gender.getText();

        if (nameId.isEmpty()) {
            nameET.setError("Name is rerquired!");
            nameET.requestFocus();
            return;
        }

        if (emailId.isEmpty()) {
            emailET.setError("Email is rerquired!");
            emailET.requestFocus();
            return;
        }

        if (contactId.isEmpty()) {
            contactET.setError("Name is rerquired!");
            contactET.requestFocus();
            return;
        }

        if (passwordId.isEmpty()) {
            passwordET.setError("Email is rerquired!");
            passwordET.requestFocus();
            return;
        }

        if (birthdayId.isEmpty()) {
            userBirthDate.setError("BirthDay is required!");
            userBirthDate.requestFocus();
            return;
        }

        signUp(nameId, emailId, contactId, passwordId, birthdayId, userGender);
    }

    private void signUp(final String nameId, final String emailId, final String contactId,
                        final String passwordId, final String birthdayId, final String userGender) {
        progressBar.setVisibility(View.VISIBLE);
        firebaseAuth.createUserWithEmailAndPassword(emailId, passwordId)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            userId = firebaseAuth.getCurrentUser().getUid();
                            User user = new User(userId, nameId, emailId, contactId, birthdayId, userGender);
                            DatabaseReference userRef = databaseReference.child("User").child(userId);
                            userRef.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(SignUpActivity.this, "Successfully Sign up!", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                        Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);

                                    }
                                }
                            });
                        }
                    }
                });

    }

    private void inItView() {

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        nameET = findViewById(R.id.userName);
        emailET = findViewById(R.id.userEmail);
        contactET = findViewById(R.id.userContact);
        passwordET = findViewById(R.id.userPassword);
        userBirthDate = findViewById(R.id.userBirthday);
        progressBar = findViewById(R.id.progressBar);

        signUpBtn = findViewById(R.id.signUpBtn);
    }


    //DatePickerMethods
    @SuppressLint("ValidFragment")
    public static class DatePickerFragment1 extends AppCompatDialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                    AlertDialog.THEME_HOLO_LIGHT, this, year, month, day);
            return dpd;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the chosen date
            userBirthDate = getActivity().findViewById(R.id.userBirthday);
           /* int actualMonth = month+1; // Because month index start from zero
            // Display the unformatted date to TextView
            tvDate.setText("Year : " + year + ", Month : " + actualMonth + ", Day : " + day + "\n\n");*/

            // Create a Date variable/object with user chosen date
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(0);
            cal.set(year, month, day, 0, 0, 0);
            Date chosenDate = cal.getTime();

            // Format the date using style medium and UK locale
            DateFormat df_medium_uk = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.UK);
            String df_medium_uk_str = df_medium_uk.format(chosenDate);
            // Display the formatted date
            userBirthDate.setText(df_medium_uk_str);
        }
    }
    //End of DatePickerMethods
}
