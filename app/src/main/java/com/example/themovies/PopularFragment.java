package com.example.themovies;

import android.os.Bundle;
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
import com.example.themovies.models.PopularMovies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

                            // Add movie to items to produce multi cell layout.
                            // 0 id for large cell and 1 is for small cel.
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

        PopularMovies itemClicked = movies.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id", itemClicked.getMovieId());

        MovieDetailFragment fragment = new MovieDetailFragment();
        fragment.setArguments(bundle);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null).commit();
    }
}
