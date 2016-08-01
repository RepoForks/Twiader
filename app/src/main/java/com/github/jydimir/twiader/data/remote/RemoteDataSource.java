package com.github.jydimir.twiader.data.remote;

import android.support.annotation.Nullable;

import com.github.jydimir.twiader.TwiaderApp;
import com.github.jydimir.twiader.data.DataSource;
import com.github.jydimir.twiader.data.model.ModelsMapper;
import com.github.jydimir.twiader.data.model.TweetLocal;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthToken;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer;
import se.akerfeldt.okhttp.signpost.SigningInterceptor;

public class RemoteDataSource implements DataSource {

    private String BASE_URL = "https://api.twitter.com/1.1/";

    @Inject
    public RemoteDataSource() {
    }

    /*It's better to create this every time here. More time consuming,
    but less memory consuming*/
    @Override
    public Observable<List<TweetLocal>> getTweets(@Nullable Long sinceId) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(createUrlBuilderInterceptor())
                .addInterceptor(createLoggingInterceptor())
                .addInterceptor(createSigningInterceptor())
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        TwitterService service = retrofit.create(TwitterService.class);

        return service.getTimeline(sinceId)
                .map(ModelsMapper::toLocal);
    }

    private HttpLoggingInterceptor createLoggingInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return loggingInterceptor;
    }

    private SigningInterceptor createSigningInterceptor() {
        OkHttpOAuthConsumer consumer = new OkHttpOAuthConsumer(TwiaderApp.TWITTER_KEY, TwiaderApp.TWITTER_SECRET);
        TwitterAuthToken authToken = Twitter.getSessionManager().getActiveSession().getAuthToken();
        consumer.setTokenWithSecret(authToken.token, authToken.secret);
        return new SigningInterceptor(consumer);
    }

    private Interceptor createUrlBuilderInterceptor() {
        return chain -> {
            HttpUrl url = chain.request().url()
                    .newBuilder()
                    .addQueryParameter("include_entities", "false")
                    .addQueryParameter("contributor_details", "false")
                    .addQueryParameter("count", "200") //load 200 tweets if since_id not specified
                    .build();
            Request request = chain.request().newBuilder().url(url).build();
            return chain.proceed(request);
        };
    }

    @Override
    public Observable<Long> saveTweets(List<TweetLocal> tweets) {
        throw new UnsupportedOperationException("This operation is unsupported for this class");
    }

    @Override
    public Observable<List<TweetLocal>> deleteReadTweets() {
        throw new UnsupportedOperationException("This operation is unsupported for this class");
    }

    @Override
    public Observable<TweetLocal> markTweetAsRead(long tweetId) {
        throw new UnsupportedOperationException("This operation is unsupported for this class");
    }

    @Override
    public Observable<Long> getUnreadQuantity() {
        throw new UnsupportedOperationException("This operation is unsupported for this class");
    }

    @Override
    public Observable<Long> deleteAllTweets() {
        throw new UnsupportedOperationException("This operation is unsupported for this class");
    }
}
