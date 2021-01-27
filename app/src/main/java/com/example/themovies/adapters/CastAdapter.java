package com.example.themovies.adapters;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.themovies.R;
import com.example.themovies.models.Cast;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.CastViewHolder> {

    private Context context;
    private ArrayList<Cast> cast = new ArrayList<>();

    public CastAdapter(Context context, ArrayList<Cast> cast) {
        this.context = context;
        this.cast = cast;
    }

    @NonNull
    @Override
    public CastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Inflate the layout to cast_cell.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cast_cell, parent, false);
        return new CastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CastViewHolder holder, int position) {

        // Bind the data from cast object to the holder.
        holder.setCastItems(cast.get(position));

        holder.photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add on click listener for actors.
            }
        });
    }

    @Override
    public int getItemCount() {
        return cast.size();
    }

    public class CastViewHolder extends RecyclerView.ViewHolder {
        ImageView photo;
        TextView name, character;

        CastViewHolder(@NonNull View itemView) {
            super(itemView);
            photo = itemView.findViewById(R.id.movieDetailCastImage);
            name = itemView.findViewById(R.id.movieDetailCastName);
            character = itemView.findViewById(R.id.movieDetailCastCharacter);
        }

        // Method to set cast details to view.
        void setCastItems(Cast cast) {
            Picasso.get()
                    .load(cast.getPhoto())
                    .into(photo);
            name.setText(cast.getName());
            character.setText(cast.getCharacter());
        }
    }
}
