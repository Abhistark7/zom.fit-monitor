package com.example.zomfitmonitor.screens.landing;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.zomfitmonitor.R;
import com.example.zomfitmonitor.databinding.ActivityMainBinding;
import com.example.zomfitmonitor.model.User;
import com.example.zomfitmonitor.screens.landing.trends.ActivityTrendFragment;
import com.example.zomfitmonitor.screens.landing.trends.CenterTrendFragment;
import com.example.zomfitmonitor.screens.landing.trends.TrendFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.parceler.Parcels;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements BrowseFragment.OnFragmentInteractionListener,
        TrendFragment.OnFragmentInteractionListener, BottomNavigationView.OnNavigationItemSelectedListener,
        CenterTrendFragment.OnFragmentInteractionListener, ActivityTrendFragment.OnFragmentInteractionListener {

    private static final String ARG_USER = "user";
    private ActivityMainBinding binding;
    private User user;
    final Fragment fragment1 = new BrowseFragment();
    final Fragment fragment2 = new TrendFragment();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = fragment1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        binding.navigation.setSelectedItemId(R.id.browse);
        fm.beginTransaction().add(R.id.main_container, fragment2, "2").hide(fragment2).commit();
        fm.beginTransaction().add(R.id.main_container, fragment1, "1").commit();
        setupBottomNavigation();
        extract();
        initialize();
    }

    private void initialize() {

    }

    private void setupBottomNavigation() {
        BottomNavigationView bottomNavigationView = binding.navigation;
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    private void extract() {
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            user = Parcels.unwrap(intent.getExtras().getParcelable(ARG_USER));
            saveUserToSharedPreferences(user);
        }
    }

    private void saveUserToSharedPreferences(User user) {
        SharedPreferences sharedPref = MainActivity.this.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(getString(R.string.is_logged_in_label), true);
        editor.putString(getString(R.string.user_id_label), user.userId);
        editor.putString(getString(R.string.user_email_label), user.email);
        editor.putString(getString(R.string.user_name_label), user.name);
        editor.putString(getString(R.string.user_password_label), user.password);
        if (user.likedActivitiesList != null && user.savedAddressList != null) {
            Set<String> likedActivitySet = new HashSet<>(user.likedActivitiesList);
            Set<String> savedCenterSet = new HashSet<>(user.savedCenterIdList);
            editor.putStringSet(getString(R.string.user_saved_center), savedCenterSet);
            editor.putStringSet(getString(R.string.user_liked_activity), likedActivitySet);
        }
        editor.apply();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.browse:
                fm.beginTransaction().hide(active).show(fragment1).commit();
                active = fragment1;
                return true;
            case R.id.trends:
                fm.beginTransaction().hide(active).show(fragment2).commit();
                active = fragment2;
                return true;
        }
        return false;
    }
}
