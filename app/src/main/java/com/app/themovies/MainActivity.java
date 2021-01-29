package com.app.themovies;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    // Listener for bottom navigation pane.
    private final BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
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
    MenuItem profile, logout;
    GoogleSignInClient googleSignInClient;
    TextView name, email;
    ImageView photo;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Enable full screen view.
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        // Set toolbar of the app.
        toolbar = findViewById(R.id.actionToolBar);
        setSupportActionBar(toolbar);

        // Find the views of action bar options.
        profile = findViewById(R.id.profileItem);
        logout = findViewById(R.id.logoutItem);

        // Configure sign-in options.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        googleSignInClient = GoogleSignIn.getClient(this, gso);

        // Initialize object for google sign in account and get data if account is not null.
        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(MainActivity.this);
        if (googleSignInAccount != null) {

//            String userName = googleSignInAccount.getDisplayName();
//            String userEmail = googleSignInAccount.getEmail();
//            Uri userPhoto = googleSignInAccount.getPhotoUrl();

//            name.setText(userName);
//            email.setText(userEmail);
//            Picasso.get()
//                    .load(userPhoto)
//                    .into(photo);

            // Add listener to bottom navigation pane to open designated fragments.
            BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
            bottomNav.setOnNavigationItemSelectedListener(navListener);

            // Open first fragment as default.
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new PopularFragment())
                    .commit();
        }
    }

    // On selecting action bar items.
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profileItem:
                AccountFragment accountFragment = new AccountFragment();

                // Open user account fragment.
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, accountFragment)
                        .commit();
                return true;
            case R.id.logoutItem:
                signOut();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Sign Out method for google account.
    private void signOut() {
        googleSignInClient.signOut()
                .addOnCompleteListener(this, task -> {
                    Intent intent = new Intent(this, UserAuthentication.class);
                    startActivity(intent);
                    finish();
                });
    }

    // Create options menu in the action bar.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.action_bar, menu);
        return true;
    }
}