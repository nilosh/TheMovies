package com.app.themovies;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class AccountFragment extends Fragment {

    TextView name, email;
    ImageView photo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_account, container, false);

        // Initialize object for google sign in account and get data if account is not null.
        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(getContext());
        if (googleSignInAccount != null) {

//            String userName = googleSignInAccount.getDisplayName();
//            String userEmail = googleSignInAccount.getEmail();
//            Uri userPhoto = googleSignInAccount.getPhotoUrl();

//            name.setText(username);
//            email.setText(userEmail);
//            Picasso.get()
//                    .load(userPhoto)
//                    .into(photo);
        }

        // Inflate the layout for this fragment
        return view;
    }
}