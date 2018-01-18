package com.janta.esir.jibambetryx;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by isaiahngaruiya on 17/01/2018.
 */

public class MoviesFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.movie_fragment, container, false);
        Bundle args = getArguments();
        ((TextView)rootView.findViewById(R.id.movie_name)).setText(Integer.toString(args.getInt("Title")));
        return rootView;
    }
}
