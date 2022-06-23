package com.example.timer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private int sec = 0;
    private boolean isRunning;
    private boolean wasRunning;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            sec = savedInstanceState.getInt("seconds");
            isRunning = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }

        runningTimer();


    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("seconds", sec);
        savedInstanceState.putBoolean("running", isRunning);
        savedInstanceState.putBoolean("wasRunning", wasRunning);
    }

    @Override
    protected void onPause() {
        super.onPause();
        wasRunning = isRunning;
        isRunning = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (wasRunning) {
            isRunning = true;
        }
    }

    public void onClickStart(View view) {
        isRunning = true;
    }

    public void onClickStop(View view) {
        isRunning = false;
    }

    public void onClickReset(View view) {
        isRunning = false;
        sec = 0;
    }

    private void runningTimer() {
        final TextView textView = (TextView) findViewById(R.id.timeViewTV);
        final Handler handle = new Handler();

        handle.post(new Runnable() {
            @Override
            public void run() {
                int hrs = sec / 3600;
                int mins = (sec % 3600) / 60;
                int secs = sec % 60;

                String time = String.format(Locale.getDefault(), "    %d:%02d:%02d   ", hrs, mins, secs);
                textView.setText(time);

                if (isRunning) {
                    sec++;
                }
                handle.postDelayed(this, 1000);
            }
        });
    }


}