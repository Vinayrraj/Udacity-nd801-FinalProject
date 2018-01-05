package com.udacity.gradle.builditbigger.remote;

import android.os.AsyncTask;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Singh on 05/01/18.
 */

public class FetchJokeTask extends AsyncTask<String, Integer, String> {

    private static final String _ERROR = "ERROR";
    private FetchJokeTaskListener mListener;

    public FetchJokeTask(FetchJokeTaskListener listener) {
        mListener = listener;
    }

    public interface FetchJokeTaskListener {
        void onJokeFetched(String joke);

        String getApi();

        void onError(String error);
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {
        OkHttpClient client = new OkHttpClient();
        Request request =
                new Request.Builder()
                        .url(mListener.getApi())
                        .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return _ERROR;
    }


    @Override
    protected void onPostExecute(String joke) {
        super.onPostExecute(joke);
        if (joke == null || joke.equals(_ERROR)) {
            mListener.onError(_ERROR);
        } else {
            mListener.onJokeFetched(joke);
        }
    }
}