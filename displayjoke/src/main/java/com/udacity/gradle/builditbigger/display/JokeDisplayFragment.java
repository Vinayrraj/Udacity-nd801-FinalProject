package com.udacity.gradle.builditbigger.display;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class JokeDisplayFragment extends Fragment {

    private JokeDisplayListener mListener = null;
    private TextView tvJokeView = null;

    public JokeDisplayFragment() {
    }

    interface JokeDisplayListener {
        String getJoke();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_joke_display, container, false);
        tvJokeView = view.findViewById(R.id.joke_display_tv_joke_content);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof JokeDisplayListener) {
            mListener = (JokeDisplayListener) context;
        } else {
            throw new RuntimeException("Activity needs to implement JokeDisplayListener");
        }
    }


    @Override
    public void onDetach() {
        mListener = null;
        super.onDetach();
    }

    @Override
    public void onStart() {
        super.onStart();
        displayJoke();
    }

    private void displayJoke() {
        tvJokeView.setText(mListener.getJoke());
    }
}
