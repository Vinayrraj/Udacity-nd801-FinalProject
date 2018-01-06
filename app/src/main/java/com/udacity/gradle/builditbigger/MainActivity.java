package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.udacity.gradle.builditbigger.display.JokeDisplayActivity;
import com.udacity.gradle.builditbigger.remote.FetchJokeTask;


public class MainActivity extends AppCompatActivity {

    private ProgressBar mProgressBar;
    private boolean isTaskInProgress = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = findViewById(R.id.main_pd);
        mProgressBar.setVisibility(View.GONE);
        findViewById(R.id.main_btn_tell_a_joke).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchJoke(mFetchJokeTaskListener);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    void fetchJoke(FetchJokeTask.FetchJokeTaskListener onResult) {
        if (!isTaskInProgress) {
            isTaskInProgress = true;
            final FetchJokeTask task = new FetchJokeTask(onResult);
            task.execute();
        }
    }


    private final FetchJokeTask.FetchJokeTaskListener mFetchJokeTaskListener = new FetchJokeTask.FetchJokeTaskListener() {

        @Override
        public void onPreFetched() {
            toggleProgressBar(true);
        }

        @Override
        public void onJokeFetched(String joke) {
            isTaskInProgress = false;
            toggleProgressBar(false);
            showJoke(joke);
        }

        @Override
        public String getApi() {
            return getString(R.string.api_fetch_joke);
        }

        @Override
        public void onError(String Error) {
            isTaskInProgress = false;
            toggleProgressBar(false);
            Toast.makeText(MainActivity.this, getString(R.string.error_api_fetch_joke), Toast.LENGTH_SHORT).show();
        }
    };

    private void toggleProgressBar(boolean state) {
        mProgressBar.setVisibility(state ? View.VISIBLE : View.GONE);
    }

    private void showJoke(String joke) {
        Intent intent = new Intent(this, JokeDisplayActivity.class);
        //intent.putExtra(getString(R.string.extra_joke_text), new Joke().getJoke());
        intent.putExtra(getString(R.string.extra_joke_text), joke);
        startActivity(intent);
    }


}
