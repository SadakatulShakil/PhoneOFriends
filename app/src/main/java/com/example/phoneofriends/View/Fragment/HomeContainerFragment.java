package com.example.phoneofriends.View.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.phoneofriends.R;
import com.example.phoneofriends.View.Activity.SignInActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeContainerFragment extends Fragment {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private FrameLayout frameLayout;
    private Context context;
    public HomeContainerFragment() {
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
        return inflater.inflate(R.layout.fragment_home_container, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);

        if (getActivity() != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        }

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_closed);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        initNavigationViewDrawer();

        getChildFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, new UserDashBoardFragment())
                .commit();
    }

    private void initNavigationViewDrawer() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch ((item.getItemId())) {

                    case R.id.settings:
                       /* getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frameLayoutID, new SettingsFragment())
                                .addToBackStack(null)
                                .commit();*/
                        Toast.makeText(context, "Settings Under Construction be Happy!", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.feedback:
                        /*Intent intent = new Intent(context, FeedBackActivity.class);
                        startActivity(intent);*/
                        Toast.makeText(context, "FeedBack Under Construction be Happy!", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.adminpanel:
                        /*Intent intent1 = new Intent(context, AdminActivity.class);
                        startActivity(intent1);*/
                        Toast.makeText(context, "AdminPanel Under Construction be Happy!", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.about:
                        /*Intent intent3 = new Intent(context, AboutUsActivity.class);
                        startActivity(intent3);*/
                        Toast.makeText(context, "About Under Construction be Happy!", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.logOut:
                        FirebaseAuth.getInstance().signOut();
                        getActivity().finish();
                        Intent intent4 = new Intent(context, SignInActivity.class);
                        startActivity(intent4);
                        break;

                    default:
                        break;
                }
                return false;
            }
        });

    }

    private void initView(View view) {

        frameLayout = view.findViewById(R.id.fragmentContainer);
        drawerLayout = view.findViewById(R.id.drawer);
        navigationView = view.findViewById(R.id.navigationDrawer);
        toolbar = view.findViewById(R.id.mainTB);
    }
}
