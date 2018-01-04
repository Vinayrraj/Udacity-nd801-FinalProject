package com.udacity.gradle.builditbigger.lib;

/**
 * Created by Singh on 04/01/18.
 */

public class JokeTest {

    @org.junit.Test
    public void shouldReturnJoke() throws Exception {
        Joke joke = new Joke();
        assert joke.getJoke().length() != 0;
    }
}
