package com.app.themovies;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

public class AccountFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_account, container, false);

       TextView name = view.findViewById(R.id.profileUserName);
       TextView email = view.findViewById(R.id.profileUserEmail);
       ImageView photo = view.findViewById(R.id.profileImage);

       // Initialize object for google sign in account and get data if account is not null.
        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(getContext());
        if (googleSignInAccount != null) {

            // Get user details.
            String userName = googleSignInAccount.getDisplayName();
            String userEmail = googleSignInAccount.getEmail();
            Uri userPhoto = googleSignInAccount.getPhotoUrl();

            // Set user details to the views.
            name.setText(userName);
            email.setText(userEmail);
            Picasso.get()
                    .load(userPhoto)
                    .into(photo);
        }

        return view;
    }
}