package com.example.themovies;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.themovies.adapters.CastAdapter;
import com.example.themovies.models.Cast;
import com.example.themovies.models.MovieDetails;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MovieDetailFragment extends Fragment {

    View view;
    int movie_id;
    String imagePath = "https://image.tmdb.org/t/p/w500/";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        // Using bundle to pass the movie_id to the next fragment.
        Bundle bundle = this.getArguments();
        movie_id = bundle.getInt("id");

        // Fetch movie details and display.
        fetchMovieDetails();

        return view;
    }

    // This method gets the URL and concats the required key to generate a full API request URL.
    private String requestUrl(String key) {
        return "https://api.themoviedb.org/3/movie/" + key + "?api_key=d10738a9069219541d83289519ca9769";
    }
    // Fetches all movie details by sending the API Request.
    public void fetchMovieDetails() {
        String MOVIE_DETAIL_URL = requestUrl(String.valueOf(movie_id));
        ArrayList<String> movieGenres = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(this.getActivity());

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, MOVIE_DETAIL_URL, null,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        // Fetches and stores the required keys and values from JSON.
                        String backdropPath = response.getString("backdrop_path");
                        JSONArray genres = response.getJSONArray("genres");

                        // Get all genres associated with a single single movie.
                        for (int i = 0; i < genres.length(); i++) {
                            JSONObject genreNames = genres.getJSONObject(i);
                            movieGenres.add(genreNames.getString("name"));
                        }

                        String title = response.getString("title");
                        String overview = response.getString("overview");
                        String poster = response.getString("poster_path");
                        String releaseDate = response.getString("release_date");
                        String runtime = response.getString("runtime");
                        String tagline = response.getString("tagline");
                        String voteAverage = response.getString("vote_average");
                        String voteCount = response.getString("vote_count");

                        MovieDetails movieDetails = new MovieDetails(movie_id, title, tagline, releaseDate,
                                movieGenres, runtime, overview, voteAverage, voteCount, poster, backdropPath);

                        generateMovieDetailView(movieDetails);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace(); }
        });

        requestQueue.add(objectRequest);
    }

    private void generateMovieDetailView(MovieDetails movieDetails) {
        ImageView backdrop, poster;
        TextView title, year, tagline, runtime, description, voteAverage, voteCount, genres;
        String movieGenres = "";

        // Set variables to view fields.
        backdrop = view.findViewById(R.id.movieDetailBanner);
        poster = view.findViewById(R.id.movieDetailPoster);
        title = view.findViewById(R.id.movieDetailTitle);
        year = view.findViewById(R.id.movieDetailYear);
        tagline = view.findViewById(R.id.movieDetailTagline);
        runtime = view.findViewById(R.id.movieDetailRuntime);
        description = view.findViewById(R.id.movieDetailDescription);
        voteAverage = view.findViewById(R.id.movieDetailRatingText);
        voteCount = view.findViewById(R.id.movieDetailVoteCount);
        genres = view.findViewById(R.id.movieDetailGenres);

        // Set data to the fields.
        Picasso.get()
                .load(imagePath + movieDetails.getBackdrop())
                .into(backdrop);
        Picasso.get()
                .load(imagePath + movieDetails.getPoster())
                .into(poster);
        title.setText(movieDetails.getTitle());
        year.setText(movieDetails.getYear());
        if (movieDetails.getTagline().isEmpty()) {
            tagline.setTextSize(0);
        } else {
            tagline.setText(movieDetails.getTagline());
        }
        runtime.setText(movieDetails.getRuntime());
        description.setText(movieDetails.getDescription());
        voteAverage.setText(movieDetails.getVoteAverage());
        voteCount.setText(movieDetails.getVoteCount());

        // Get each genre from the array list and concat to a single string.
        if (movieDetails.getGenres().size() > 1) {
            for (int i = 0; i < movieDetails.getGenres().size(); i++) {
                if (i != movieDetails.getGenres().size() - 1) {
                    movieGenres = movieGenres + movieDetails.getGenres().get(i).toString() + " | ";
                } else {
                    movieGenres = movieGenres + movieDetails.getGenres().get(i).toString();
                }
            }
        } else {
            movieGenres = movieDetails.getGenres().get(0).toString();
        }
        genres.setText(movieGenres);

        // Display the movie cast.
        displayMovieCast();
    }

    private void displayMovieCast() {
        ArrayList<Cast> cast = new ArrayList<>();
        String CREDITS_URL = requestUrl(String.valueOf(movie_id + "/credits"));
        RequestQueue requestQueue = Volley.newRequestQueue(this.getActivity());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, CREDITS_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Get cast data from cast array in the JSON.
                            JSONArray jsonArray = response.getJSONArray("cast");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject people = jsonArray.getJSONObject(i);

                                // Create new cast object with person details.
                                Cast movieCast = new Cast(people.getInt("id"), people.getString("name"),
                                        people.getString("character"), people.getString("profile_path"));

                                // Add to array list.
                                cast.add(movieCast);
                            }

                            // Initialize the recycler view for the
                            initializeRecyclerView(cast);
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

    private void initializeRecyclerView(ArrayList<Cast> cast) {
        // Set up the horizontal recycler view.
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = getView().findViewById(R.id.movieDetailCastRecyclerView);
        recyclerView.setLayoutManager(layoutManager);
        CastAdapter castAdapter = new CastAdapter(getActivity(), cast);
        // If no cast, hide the cast related text views.
        if (cast.isEmpty()) {
            TextView textView = getView().findViewById(R.id.movieDetailCast);
            textView.setVisibility(View.GONE);
            View rule = getView().findViewById(R.id.hr);
            rule.setVisibility(View.GONE);
        } else {
            recyclerView.setAdapter(castAdapter);
        }
    }
}