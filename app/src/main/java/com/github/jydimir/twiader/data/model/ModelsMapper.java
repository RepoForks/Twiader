package com.github.jydimir.twiader.data.model;

import com.github.jydimir.twiader.util.DateUtils;

import java.util.ArrayList;
import java.util.List;

public class ModelsMapper {
    public static List<TweetLocal> toLocal(List<TweetRemote> remoteTweets) {
        List<TweetLocal> localList = new ArrayList<>(remoteTweets.size());
        for (TweetRemote remote : remoteTweets) {
            TweetLocal local = new TweetLocal();
            local.setCreatedAt(DateUtils.parseTwitterUTC(remote.getCreatedAt()).getTime());
            local.setId(remote.getId());
            local.setText(remote.getText());
            local.setUserId(remote.getUser().getId());
            local.setUserName(remote.getUser().getName());
            local.setUserScreenName(remote.getUser().getScreenName());
            local.setFavorited(remote.getFavorited());
            local.setRetweeted(remote.getRetweeted());
            local.setLang(remote.getLang());
            localList.add(local);
        }
        return localList;
    }
}
