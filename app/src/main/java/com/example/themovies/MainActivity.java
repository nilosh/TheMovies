package com.example.themovies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Add app logo to title bar.
//        getSupportActionBar().setLogo(R.drawable.ic_launcher_logo);
//        getSupportActionBar().setDisplayUseLogoEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Enable full screen view.
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        // Add listener to bottom navigation pane to open designated fragments.
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        // Open first fragment as default.
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new PopularFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_popular:
                            selectedFragment = new PopularFragment();
                            break;
                        case R.id.nav_search:
                            selectedFragment = new SearchFragment();
                            break;
                        case R.id.nav_fav:
                            selectedFragment = new FavouritesFragment();
                            break;
                        case R.id.nav_upcoming:
                            selectedFragment = new UpcomingFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };
}