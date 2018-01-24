package com.janta.esir.jibambetryx.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
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
    public void onBindViewHolder(final MovieViewHolder holder, int position) {
        //Do the actual inflation of the view
        final Movie movie = movieList.get(position);
        holder.movieName.setText(movie.getName());
        holder.movieDuration.setText(movie.getDuration());

        //For thumbnail(Movie cover) use glide
        Glide.with(mContext)
                .load(movie.getThumbnail())
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        // Hide the loading dialog when the Image has been loaded
                        holder.loading_img.setVisibility(View.GONE);
                        return false;
                    }
                })
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
        private ContentLoadingProgressBar loading_img;

        public MovieViewHolder(View itemView) {
            super(itemView);
            movieName = itemView.findViewById(R.id.movie_name);
            movieDuration = itemView.findViewById(R.id.movie_duration);
            thumbnail = itemView.findViewById(R.id.movie_thumbnail);
            loading_img = itemView.findViewById(R.id.loading_img);
        }
    }
}
