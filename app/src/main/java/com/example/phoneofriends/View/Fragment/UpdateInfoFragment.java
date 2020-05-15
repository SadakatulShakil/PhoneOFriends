package com.example.phoneofriends.View.Fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phoneofriends.R;
import com.example.phoneofriends.View.Activity.SingleChatBoardActivity;
import com.example.phoneofriends.View.Model.User;
import com.example.phoneofriends.View.Model.UserDetails;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateInfoFragment extends Fragment {
    private Context context;
    private Toolbar dToolbar;
    private Button updateInfo;
    private User user;
    private String userGender;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference userRef;
    private DatabaseReference userDetailRef;
    private UserDetails userDetails;
    protected static TextView tvBirthday;
    private EditText etName, etEmail, etContact, etSchool, etCollege, etUniversity;
    private EditText etHomeArea, etRoadNo, etHouseNo, etJobTitle, etOfficeName, etOfficeLocation;
    private RadioButton rMale, rFemale;

    public UpdateInfoFragment() {
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
        return inflater.inflate(R.layout.fragment_update_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inItView(view);

        retrieveUserInfo();
        dToolbar.setTitle("Update Details");

        dToolbar.setNavigationIcon(R.drawable.ic_back);
        dToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        updateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beforeUpdateDetails();
                personalDataUpdate(view);
                Fragment detailsFragment = new DetailsInfoFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameForDetails, detailsFragment);
                fragmentTransaction.commit();
            }
        });
    }

    private void personalDataUpdate(View view) {

        final RadioGroup rbg=(RadioGroup) view.findViewById(R.id.gender);
        int selected = rbg.getCheckedRadioButtonId();
        final RadioButton gender = view.findViewById(selected);

        String nameId = etName.getText().toString().trim();
        String emailId = etEmail.getText().toString().trim();
        String contactId = etContact.getText().toString().trim();
        String birthdayId = tvBirthday.getText().toString().trim();
        userGender = (String) gender.getText();

        if (nameId.isEmpty()) {
            etName.setError("Name is required!");
            etName.requestFocus();
            return;
        }

        if (emailId.isEmpty()) {
            etEmail.setError("Email is required!");
            etEmail.requestFocus();
            return;
        }

        if (contactId.isEmpty()) {
            etContact.setError("Name is required!");
            etContact.requestFocus();
            return;
        }


        if (birthdayId.isEmpty()) {
            tvBirthday.setError("BirthDay is required!");
            tvBirthday.requestFocus();
            return;
        }

        updatePersonal(nameId, emailId, contactId, birthdayId, userGender);
    }

    private void updatePersonal(final String nameId, final String emailId, final String contactId,
                                final String birthdayId, final String gender) {
        userRef = FirebaseDatabase.getInstance().getReference("User");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        String userId = firebaseUser.getUid();
        user = new User(userId, nameId, emailId, contactId, birthdayId, gender);

        userRef.child(userId).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(context, "Successful!", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void beforeUpdateDetails() {

        String school = etSchool.getText().toString().trim();
        String college = etCollege.getText().toString().trim();
        String university = etUniversity.getText().toString().trim();
        String homeArea = etHomeArea.getText().toString().trim();
        String homeRoad = etRoadNo.getText().toString().trim();
        String houseNo = etHouseNo.getText().toString().trim();
        String jobTitle = etJobTitle.getText().toString().trim();
        String officeName = etOfficeName.getText().toString().trim();
        String officeLocation = etOfficeLocation.getText().toString().trim();

        if (school.isEmpty()) {
            etSchool.setError("School is required!");
            etSchool.requestFocus();
            return;
        }
        if (college.isEmpty()) {
            etCollege.setError("College is required!");
            etCollege.requestFocus();
            return;
        }
        if (university.isEmpty()) {
            etUniversity.setError("SUniversity is required!");
            etUniversity.requestFocus();
            return;
        }
        if (homeArea.isEmpty()) {
            etHomeArea.setError("HomeArea is required!");
            etHomeArea.requestFocus();
            return;
        }
        if (homeRoad.isEmpty()) {
            etRoadNo.setError("RoadNo is required!");
            etRoadNo.requestFocus();
            return;
        }
        if (houseNo.isEmpty()) {
            etHouseNo.setError("HouseNo is required!");
            etHouseNo.requestFocus();
            return;
        }
        if (jobTitle.isEmpty()) {
            etJobTitle.setError("JobTitle is required!");
            etJobTitle.requestFocus();
            return;
        }
        if (officeName.isEmpty()) {
            etOfficeName.setError("OfficeName is required!");
            etOfficeName.requestFocus();
            return;
        }
        if (officeLocation.isEmpty()) {
            etOfficeLocation.setError("OfficeLocation is required!");
            etOfficeLocation.requestFocus();
            return;
        }

        upDateNow(school, college, university, homeArea, homeRoad, houseNo, jobTitle, officeName, officeLocation);
    }

    private void upDateNow(final String school, final String college, final String university, final String homeArea,
                           final String homeRoad, final String houseNo, final String jobTitle, final String officeName,
                           final String officeLocation) {

        userDetailRef = FirebaseDatabase.getInstance().getReference("UserDetail");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        String userId = firebaseUser.getUid();
        userDetails = new UserDetails(userId, school, college, university, homeArea, homeRoad,houseNo,jobTitle,
                officeName, officeLocation);

        userDetailRef.child(userId).setValue(userDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(context, "Successful!", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void retrieveUserInfo() {

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        FirebaseDatabase fb = FirebaseDatabase.getInstance();
        String userId = firebaseUser.getUid();
        userRef = fb.getReference("User");
        userDetailRef = FirebaseDatabase.getInstance().getReference("UserDetail");

        userRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        user = dataSnapshot.getValue(User.class);

                        etName.setText(user.getUserName());
                        etEmail.setText(user.getUserEmail());
                        etContact.setText(user.getUserContact());
                        tvBirthday.setText(user.getUserDateOfBirth());

                        if(user.getUserGender().equals("Male")){
                            rMale.setChecked(true);
                        }
                        else{
                            rFemale.setChecked(true);
                        }
                    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        userDetailRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    userDetails = dataSnapshot.getValue(UserDetails.class);

                    etSchool.setText(userDetails.getUserSchool());
                    etEmail.setText(userDetails.getUserCollege());
                    etContact.setText(userDetails.getUserUniversity());
                    etHomeArea.setText(userDetails.getUserHomeArea());
                    etRoadNo.setText(userDetails.getUserHomeRoad());
                    etHouseNo.setText(userDetails.getUserHouseNo());
                    etJobTitle.setText(userDetails.getUserJobTitle());
                    etOfficeName.setText(userDetails.getUserOfficeName());
                    etOfficeLocation.setText(userDetails.getUserOfficeLocation());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
            tvBirthday = getActivity().findViewById(R.id.userBirthday);
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
            tvBirthday.setText(df_medium_uk_str);
        }
    }
    //End of DatePickerMethods
    private void inItView(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dToolbar = view.findViewById(R.id.toolbar);
        }
        updateInfo = view.findViewById(R.id.updateAll);
        etName = view.findViewById(R.id.userName);
        etEmail = view.findViewById(R.id.userEmail);
        etContact = view.findViewById(R.id.userContact);
        tvBirthday = view.findViewById(R.id.userBirthday);
        rMale = view.findViewById(R.id.radio1);
        rFemale = view.findViewById(R.id.radio2);

        etSchool = view.findViewById(R.id.userSchool);
        etCollege = view.findViewById(R.id.userCollage);
        etUniversity = view.findViewById(R.id.userUniversity);
        etHomeArea = view.findViewById(R.id.userHomeArea);
        etRoadNo = view.findViewById(R.id.roadNo);
        etHouseNo = view.findViewById(R.id.houseNo);
        etJobTitle = view.findViewById(R.id.jobTitle);
        etOfficeName = view.findViewById(R.id.officeName);
        etOfficeLocation = view.findViewById(R.id.officeLocation);

    }
}
