package com.janta.esir.jibambetryx.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.janta.esir.jibambetryx.MainActivity;
import com.janta.esir.jibambetryx.R;
import com.janta.esir.jibambetryx.models.Movie;

import java.util.List;

/**
 * Created by isaiahngaruiya on 19/01/2018.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>{

    private Context mContext;
    private List<Movie> movieList;

    public MovieAdapter(Context mContext, List<Movie> movieList){
        this.movieList = movieList;
        this.mContext = mContext;
    }
    public void updateMovies(List<Movie> movieList){
        this.movieList = movieList;
        notifyDataSetChanged();
    }
    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View movieView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_card, parent, false);
        return new MovieViewHolder(movieView);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        //Do the actual inflation of the view
        final Movie movie = movieList.get(position);
        holder.movieName.setText(movie.getName());
        holder.movieDuration.setText(movie.getDuration());

        //For thumbnail(Movie cover) use glide
        Glide.with(mContext)
                .load(movie.getThumbnail())
                .into(holder.thumbnail);
        holder.thumbnail.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Open a new Activity providing the movies data
                        Intent playerActivity = new Intent(mContext, MainActivity.class);
                        Bundle args = new Bundle();
                        args.putString("url", movie.getSource());
                        playerActivity.putExtras(args);
                        mContext.startActivity(playerActivity);
                    }
                }
        );
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder{

        public TextView movieName, movieDuration;
        public ImageView thumbnail;

        public MovieViewHolder(View itemView) {
            super(itemView);
            movieName = itemView.findViewById(R.id.movie_name);
            movieDuration = itemView.findViewById(R.id.movie_duration);
            thumbnail = itemView.findViewById(R.id.movie_thumbnail);
        }
    }
}
