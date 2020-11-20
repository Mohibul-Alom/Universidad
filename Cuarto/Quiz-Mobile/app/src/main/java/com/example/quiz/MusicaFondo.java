package com.example.quiz;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;


public class MusicaFondo extends Service {

    private MediaPlayer mediaPlayer;

    public MusicaFondo(){

    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
        mediaPlayer = MediaPlayer.create(this,R.raw.soho);
        mediaPlayer.setLooping(true); // Set looping
        mediaPlayer.setVolume(1, 1);
    }
    public int onStartCommand(Intent intent, int flags, int startId) {
        mediaPlayer.start();
        return startId;
    }
    public void onStart(Intent intent, int startId) {
    }

    public void onDestroy() {
        mediaPlayer.stop();
        mediaPlayer.release();
    }

    public void onLowMemory() {
    }



}

