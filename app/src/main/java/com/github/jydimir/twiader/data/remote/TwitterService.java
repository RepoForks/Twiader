package com.github.jydimir.twiader.data.remote;

import com.github.jydimir.twiader.data.model.TweetRemote;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface TwitterService {
    @GET("statuses/home_timeline.json")
    Observable<List<TweetRemote>> getTimeline(@Query("since_id") Long sinceId);
}
