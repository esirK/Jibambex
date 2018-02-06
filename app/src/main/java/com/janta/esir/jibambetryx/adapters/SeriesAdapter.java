package com.janta.esir.jibambetryx.adapters;

import android.content.Context;
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
import com.janta.esir.jibambetryx.R;
import com.janta.esir.jibambetryx.models.Series;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by isaiahngaruiya on 04/02/2018.
 */

public class SeriesAdapter extends RecyclerView.Adapter<SeriesAdapter.SeriesViewHolder>{

    private Context mContext;
    private List<Series> seriesList;

    public SeriesAdapter(Context context, List<Series> seriesList){
        this.mContext = context;
        this.seriesList = seriesList;
    }
    public void updateSeries(List<Series> ALlSeries){
        this.seriesList = ALlSeries;
        notifyDataSetChanged();
    }
    @Override
    public SeriesAdapter.SeriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View SeriesView = LayoutInflater.from(parent.getContext()).inflate(R.layout.series_layout, parent, false);
        return new SeriesViewHolder(SeriesView);
    }

    @Override
    public void onBindViewHolder(final SeriesAdapter.SeriesViewHolder holder, int position) {
        //Get single series from series list
        Series SingleSeries = seriesList.get(position);
        holder.name.setText(SingleSeries.getName());
        holder.seasons.setText("Seasons "+SingleSeries.getSeasons());

        //Load series thumbnail using glide

        Glide.with(mContext).load(SingleSeries.getThumbnail())
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
                .into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        if(seriesList != null) {
            return seriesList.size();
        }else {
            return 0;
        }
    }

    static class SeriesViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.series_thumbnail) ImageView thumbnail;
        @BindView(R.id.series_name) TextView name;
        @BindView(R.id.series_seasons) TextView seasons;
        @BindView(R.id.loading_img) ContentLoadingProgressBar loading_img;

        SeriesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
