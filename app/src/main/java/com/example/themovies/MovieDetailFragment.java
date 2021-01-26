package com.example.themovies;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

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
import com.example.themovies.models.MovieDetails;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MovieDetailFragment extends Fragment {

    View view;
    int movie_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        Bundle bundle = this.getArguments();
        movie_id = bundle.getInt("id");

        // Fetch movie details and display.
        fetchMovieDetails();

        return view;
    }

    public void fetchMovieDetails() {
        String MOVIE_DETAIL_URL = "https://api.themoviedb.org/3/movie/" + String.valueOf(movie_id ) +
                "?api_key=d10738a9069219541d83289519ca9769";
        ArrayList<String> movieGenres = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(this.getActivity());

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, MOVIE_DETAIL_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String backdropPath = response.getString("backdrop_path");
//                            JSONObject backdropPath = response.getJSONObject("backdrop_path");
                            JSONArray genres = response.getJSONArray("genres");

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
                error.printStackTrace();
            }
        });

        requestQueue.add(objectRequest);
    }

    private void generateMovieDetailView(MovieDetails movieDetails) {
        String imagePath = "https://image.tmdb.org/t/p/w500/";
        ImageView backdrop, poster;
        TextView title, year, tagline, runtime, description, voteAverage, voteCount, genres;
        String movieGenres;

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
                .load( imagePath + movieDetails.getPoster())
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
        for(int i = 0; i < movieDetails.getGenres().size(); i++) {
            movieGenres = movieDetails.getGenres().get(i);
            if (i != movieDetails.getGenres().size() - 1) {
                movieGenres = movieGenres + " | ";
            }
            genres.setText(movieGenres);
        }

    }

}