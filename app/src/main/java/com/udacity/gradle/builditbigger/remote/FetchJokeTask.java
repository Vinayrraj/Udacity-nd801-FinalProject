package com.udacity.gradle.builditbigger.remote;

import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.jokeApi.JokeApi;


import java.io.IOException;

/**
 * Created by Singh on 05/01/18.
 */

public class FetchJokeTask extends AsyncTask<String, Integer, String> {

    private static final String _ERROR = "ERROR";
    private FetchJokeTaskListener mListener;
    private static JokeApi apiService = null;

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
        String response = getJokeFromLocal();
        if (response != null) return response;

        return _ERROR;
    }

    private String getJokeFromLocal() {
        try {
            return getService().joke().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    private JokeApi getService() {
        if (apiService == null) {  // Only do this once
            JokeApi.Builder builder = new JokeApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl(mListener.getApi())
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            apiService = builder.build();
        }

        return apiService;
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