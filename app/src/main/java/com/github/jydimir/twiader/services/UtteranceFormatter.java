/*
 * Copyright 2016  Andrii Lisun
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.jydimir.twiader.services;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Patterns;

import com.github.jydimir.twiader.R;
import com.github.jydimir.twiader.data.model.TweetLocal;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtteranceFormatter {

    private final Pattern PATTERN_MENTION = Pattern.compile("[@]+([A-Za-z0-9-_]+)");
    private final Pattern PATTERN_HASHTAG = Pattern.compile("[#]+([A-Za-z0-9-_]+)");
    private final Pattern PATTERN_DUPLICATION = Pattern.compile("([\\s]*[.,:?!]{1}[\\s]*){2,}");
    private final String[] PUNCTUATION_PRIORITY_LIST = {"?", "!", ".", ",", ":"};
    private final String FORMAT_INTRO = "%s %s : ";
    private final String FORMAT_MENTION = " . %s : %s . ";
    private final String FORMAT_HASHTAG = " . %s : %s . ";
    private final String FORMAT_LINK = " . %s . ";

    private String tweeted;
    private String mention;
    private String hashtag;

    private String linkFormatted;

    public UtteranceFormatter(@NonNull Context context) {
        tweeted = context.getString(R.string.utterance_tweeted);
        mention = context.getString(R.string.utterance_mention);
        hashtag = context.getString(R.string.utterance_hashtag);
        linkFormatted = String.format(FORMAT_LINK, context.getString(R.string.utterance_link));
    }

    public String format(TweetLocal tweet) {
        String utterance = replaceMentions(tweet.getText());
        utterance = replaceHashTags(utterance);
        utterance = replaceLinks(utterance);
        utterance = replaceDuplication(utterance);
        return (createIntro(tweet.getUserName()) + utterance);
    }

    private String replaceMentions(String text) {
        return replaceOccurrences(PATTERN_MENTION, FORMAT_MENTION, mention, text);
    }

    private String replaceHashTags(String text) {
        return replaceOccurrences(PATTERN_HASHTAG, FORMAT_HASHTAG, hashtag, text);
    }

    private String replaceOccurrences(Pattern pattern, String template, String occurrenceName, String text) {
        StringBuffer sb = new StringBuffer();
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            //skip @ or #
            String target = matcher.group().substring(1);
            String formatted = String.format(template, occurrenceName, target);
            matcher.appendReplacement(sb, formatted);
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    private String replaceLinks(String text) {
        return text.replaceAll(Patterns.WEB_URL.toString(), linkFormatted);
    }

    private String replaceDuplication(String text) {
        StringBuffer sb = new StringBuffer();
        Matcher matcher = PATTERN_DUPLICATION.matcher(text);
        String format = "%s ";
        while (matcher.find()) {
            //lowest priority char
            String subsCharacter = PUNCTUATION_PRIORITY_LIST[PUNCTUATION_PRIORITY_LIST.length - 1];
            //iterate until pre last
            for (int i = 0; i < (PUNCTUATION_PRIORITY_LIST.length - 1); i++) {
                if (matcher.group().contains(PUNCTUATION_PRIORITY_LIST[i])) {
                    subsCharacter = PUNCTUATION_PRIORITY_LIST[i];
                    break;
                }
            }
            matcher.appendReplacement(sb, String.format(format, subsCharacter));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    private String createIntro(String name) {
        return String.format(FORMAT_INTRO, name, tweeted);
    }
}
