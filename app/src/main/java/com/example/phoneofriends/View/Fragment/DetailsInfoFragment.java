package com.example.phoneofriends.View.Fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.phoneofriends.R;
import com.example.phoneofriends.View.Model.User;
import com.example.phoneofriends.View.Model.UserDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsInfoFragment extends Fragment {
    private TextView updateButton;
    private Context context;
    private Toolbar dToolbar;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference userRef;
    private User user;
    private UserDetails userDetails;
    private DatabaseReference userDetailRef;
    private TextView tvName, tvEmail, tvContact, tvBirthday, tvGender;
    private TextView tvSchool, tvCollege, tvUniversity;
    private TextView tvHomeArea, tvRoadNo, tvHouseNo, tvJobTitle, tvOfficeName, tvOfficeLocation;
    public DetailsInfoFragment() {
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
        return inflater.inflate(R.layout.fragment_details_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inItView(view);
        dToolbar.setTitle("Profile Details");

        dToolbar.setNavigationIcon(R.drawable.ic_back);
        dToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        retrievedAllBioInfo();
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Fragment updateFragment = new UpdateInfoFragment();
               FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
               FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
               fragmentTransaction.replace(R.id.frameForDetails, updateFragment);
               fragmentTransaction.addToBackStack(null);
               fragmentTransaction.commit();
            }
        });
    }

    private void retrievedAllBioInfo() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        final String userId = firebaseUser.getUid();

        userRef = FirebaseDatabase.getInstance().getReference("User");
        userDetailRef = FirebaseDatabase.getInstance().getReference("UserDetail");

        userRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    user = dataSnapshot.getValue(User.class);

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

        userDetailRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    userDetails = dataSnapshot.getValue(UserDetails.class);

                    tvSchool.setText(userDetails.getUserSchool());
                    tvCollege.setText(userDetails.getUserCollege());
                    tvUniversity.setText(userDetails.getUserUniversity());
                    tvHomeArea.setText(userDetails.getUserHomeArea());
                    tvRoadNo.setText(userDetails.getUserHomeRoad());
                    tvHouseNo.setText(userDetails.getUserHouseNo());
                    tvJobTitle.setText(userDetails.getUserJobTitle());
                    tvOfficeName.setText(userDetails.getUserOfficeName());
                    tvOfficeLocation.setText(userDetails.getUserOfficeLocation());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void inItView(View view) {
        updateButton = view.findViewById(R.id.updateBt);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dToolbar = view.findViewById(R.id.toolbar);
        }
        tvName = view.findViewById(R.id.userName);
        tvEmail = view.findViewById(R.id.userEmail);
        tvContact = view.findViewById(R.id.userContact);
        tvBirthday = view.findViewById(R.id.userBirthDay);
        tvGender = view.findViewById(R.id.userGender);

        tvSchool = view.findViewById(R.id.userSchool);
        tvCollege = view.findViewById(R.id.userCollage);
        tvUniversity = view.findViewById(R.id.userUniversity);

        tvHomeArea = view.findViewById(R.id.userHomeArea);
        tvRoadNo = view.findViewById(R.id.roadNo);
        tvHouseNo = view.findViewById(R.id.houseNo);
        tvJobTitle = view.findViewById(R.id.jobTitle);
        tvOfficeName = view.findViewById(R.id.officeName);
        tvOfficeLocation = view.findViewById(R.id.officeLocation);
    }
}
