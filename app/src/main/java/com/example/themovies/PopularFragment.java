package com.example.themovies;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.themovies.adapters.PopularMoviesAdapter;
import com.example.themovies.models.Item;
import com.example.themovies.models.MovieDetails;
import com.example.themovies.models.PopularMovies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PopularFragment extends Fragment implements PopularMoviesAdapter.OnMovieItemClickListener {

    public static final String EXTRA_ID = "id";
    List<Item> items = new ArrayList<>();
    ArrayList<PopularMovies> movies = new ArrayList<>();
    RecyclerView popularMoviesView;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // Inflate the layout for this fragment.
        view = inflater.inflate(R.layout.fragment_popular, container, false);
        popularMoviesView = view.findViewById(R.id.recyclerViewPopMovies);

        // Fetch movie data and display.
        fetchMovieData();

        return view;
    }

    public void fetchMovieData() {
        // API Request to get the most popular movies.
        String POPULAR_MOVIES_URL = "https://api.themoviedb.org/3/movie/popular?" +
                "api_key=d10738a9069219541d83289519ca9769";
        RequestQueue requestQueue = Volley.newRequestQueue(this.getActivity());


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, POPULAR_MOVIES_URL,
                null, new Response.Listener<JSONObject>() {
            @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray jsonArray = response.getJSONArray("results");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject results = jsonArray.getJSONObject(i);

                            PopularMovies movie = new PopularMovies(results.getInt("id"),
                                    "https://image.tmdb.org/t/p/w500/" + results.getString("poster_path"),
                                    results.getString("title"),
                                    results.getString("release_date"),
                                    results.getString("vote_average")
                                    );

                            // Add the movie to the movies array list.
                            movies.add(movie);

                            // Add movie to items to produce cell layout.
                            if (i == 0) {
                                items.add(new Item(0, movies.get(i)));
                            } else {
                                items.add(new Item(1, movies.get(i)));
                            }
                        }

                        // Set the adapter to generate the views.
                        PopularMoviesAdapter moviesAdapter = new PopularMoviesAdapter(items);
                        popularMoviesView.setAdapter(moviesAdapter);
                        moviesAdapter.setOnMovieItemClickListener(PopularFragment.this);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(request);
    }

    @Override
    public void onMovieItemClick(int position) {
        Intent intent = new Intent(this.getActivity(), MovieDetail.class);
        PopularMovies itemClicked = movies.get(position);
        intent.putExtra(EXTRA_ID, itemClicked.getMovieId());
        startActivity(intent);
    }

//    private String convertMinutesToHours(Double timeInMinutes) {
//        int indexOfSeparator = 0;
//        Double time = 0.0;
//        String timeAsString = "", timeInHours = "", timeInMins = "";
//        time = timeInMinutes / 60;
//        timeAsString = String.valueOf(time);
//        if (timeAsString.contains(".")) {
//            indexOfSeparator = timeAsString.indexOf(".");
//        } else {
//            timeInHours = timeAsString.substring(0, indexOfSeparator);
//            timeInMins = timeAsString.substring(indexOfSeparator + 1);
//        }
//         return timeInHours + "Hrs and " + timeInMins + "Minutes";
//    }
}
