package com.udacity.gradle.builditbigger.display;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class JokeDisplayActivity extends AppCompatActivity implements JokeDisplayFragment.JokeDisplayListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_display);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public String getJoke() {
        return getIntent().getStringExtra(getString(R.string.extra_joke_text));
    }
}
