package com.github.jydimir.twiader.services;

import android.content.Context;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.support.annotation.NonNull;

import java.util.HashMap;

import javax.inject.Inject;

public class BetterTTS implements TextToSpeech.OnInitListener {

    public interface SpeakProgressListener {
        void onComplete(String utteranceId);
    }

    private TextToSpeech tts;
    private Boolean initialized = false;
    private SpeakProgressListener progressListener;
    // TODO: 09.07.2016 hardcoded for now but set custom in future
    private float speechSpeed = 1f;

    @Inject
    public BetterTTS(@NonNull Context context, SpeakProgressListener progressListener) {
        tts = new TextToSpeech(context, this);
        tts.setSpeechRate(speechSpeed);
        tts.setOnUtteranceProgressListener(new ProgressListener());
        this.progressListener = progressListener;

    }

    public void speak(String text, String utteranceId) {
        if (isInitialized()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId);
            } else {
                HashMap<String, String> params = new HashMap<>(1);
                params.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID,
                        utteranceId);
                //noinspection deprecation
                tts.speak(text, TextToSpeech.QUEUE_FLUSH, params);
            }
        }
    }

    public void stopSpeak() {
        if (isInitialized()) {
            tts.stop();
        }
    }

    public void shutdown() {
        if (isInitialized()) {
            tts.shutdown();
        }
    }

    @Override
    public void onInit(int status) {
        initialized = (status == TextToSpeech.SUCCESS);
    }

    private boolean isInitialized() {
        return initialized;
    }

    private class ProgressListener extends UtteranceProgressListener {

        @Override
        public void onDone(String utteranceId) {
            if (progressListener != null) {
                progressListener.onComplete(utteranceId);
            }
        }

        @Override
        public void onError(String utteranceId) {
        }

        @Override
        public void onStart(String utteranceId) {
        }
    }
}
