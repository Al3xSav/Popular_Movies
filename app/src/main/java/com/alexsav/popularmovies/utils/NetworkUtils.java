package com.alexsav.popularmovies.utils;

import com.alexsav.popularmovies.BuildConfig;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*
 * A way to communicate with theMovieDB
 */
public class NetworkUtils {

    /*  The Top Rated Feed
     * "https://api.themoviedb.org/3/movie/top_rated?api_key=API_KEY&page=1"
     *
     *  The Popular Feed
     * "https://api.themoviedb.org/3/movie/popular?api_key=API_KEY&page=1"
     */
    public static final String TOP_RATED = "top_rated";
    public static final String MOST_POPULAR = "popular";
    public static final String FAVORITE = "favorite";
    // Base URL
    public static final String MOVIES_URL = BuildConfig.BASE_URL;
    public static final String POSTER_URL = BuildConfig.BASE_URL_IMAGE_POSTER;
    public static final String BACKDROP_URL = BuildConfig.BASE_URL_IMAGE_BACKDROP;
    public static final String VIDEO_URL = BuildConfig.BASE_URL_VIDEO;
    // PUT API HERE
    private static final String API_CONS = BuildConfig.API_KEY;
    public static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    public static Retrofit retrofit;

    public static <S> S createService(Class<S> serviceClass) {
        HttpLoggingInterceptor httpLoggingInterceptor =
                new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

        httpClient.addInterceptor(httpLoggingInterceptor)
                .addInterceptor(new AuthenticationInterceptor());

        retrofit = new Retrofit.Builder()
                .baseUrl(MOVIES_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        return retrofit.create(serviceClass);
    }

    private static class AuthenticationInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            HttpUrl httpUrl = chain
                    .request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("api_key", API_CONS)
                    .build();

            Request request = chain
                    .request()
                    .newBuilder()
                    .url(httpUrl)
                    .build();

            return chain.proceed(request);
        }
    }


}
