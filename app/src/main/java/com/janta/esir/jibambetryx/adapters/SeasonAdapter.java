package com.janta.esir.jibambetryx.adapters;

import android.content.Context;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.janta.esir.jibambetryx.R;
import com.janta.esir.jibambetryx.models.Season;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by isaiahngaruiya on 07/02/2018.
 */

public class SeasonAdapter extends RecyclerView.Adapter<SeasonAdapter.SeasonViewHolder>{

    List<Season> seasonList;
    Context mContext;

    public SeasonAdapter(List<Season> seasonList, Context mContext){
        this.seasonList = seasonList;
        this.mContext = mContext;
    }
    public void updateSeasons(List<Season> allSeasons){
        this.seasonList = allSeasons;
        notifyDataSetChanged();
    }
    @Override
    public SeasonAdapter.SeasonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.season_card, parent, false);
        return new SeasonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SeasonAdapter.SeasonViewHolder holder, int position) {
        final Season season = seasonList.get(position);
        holder.seasonName.setText(season.getName());
        holder.numEpisodes.setText(season.getNum_episodes()+" Episodes");

        Glide.with(mContext)
                .load(season.getThumbnail())
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        holder.loading_img.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(holder.season_thumbnail);

        // On click of the Image, Open an activity holding all episodes of this season
        holder.season_thumbnail.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext, "You clicked " + season.getName(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    @Override
    public int getItemCount() {
        return seasonList.size();
    }
    static class SeasonViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.season_name) TextView seasonName;
        @BindView(R.id.num_episodes) TextView numEpisodes;
        @BindView(R.id.season_thumbnail) ImageView season_thumbnail;
        @BindView(R.id.loading_img) ContentLoadingProgressBar loading_img;

        public SeasonViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
