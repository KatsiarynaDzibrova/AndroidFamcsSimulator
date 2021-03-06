package com.source.studsimulator.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.source.studsimulator.R;
import com.source.studsimulator.ui.StudSimulatorApplication;

@SuppressLint("Registered")
public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    private Button githubButton;
    private Button formButton;
    private Button startButton;
    private Button stopButton;
    private Button rulesButton;

    private boolean IsActive = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        githubButton = findViewById(R.id.gitButton);
        githubButton.setOnClickListener(v -> GoToGithub());

        formButton = findViewById(R.id.formButton);
        formButton.setOnClickListener(v -> leaveFeedback());

        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);

        startButton.setOnClickListener(this);
        stopButton.setOnClickListener(this);

        rulesButton = findViewById(R.id.rulesButton);
        rulesButton.setOnClickListener(v -> {
            Intent intent = new Intent(SettingsActivity.this, RulesActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onClick(View view) {
        if (view == startButton) {
            if (!IsActive) {
                startService(new Intent(this, MyService.class));
                IsActive = true;
            }
        }
        if (view == stopButton) {
            stopService(new Intent(this, MyService.class));
            IsActive = false;
        }
    }

    private void GoToGithub() {
        Uri uriUrl = Uri.parse(StudSimulatorApplication.getContext().getString(R.string.github));
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }

    private void leaveFeedback() {
        Uri uriUrl = Uri.parse(StudSimulatorApplication.getContext().getString(R.string.feedback_link));
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }

}
