package com.example.jocke.projekt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.ToggleButton;

public class FilterActivity extends AppCompatActivity {
    private Button submitButton;
    private RadioGroup typeRadioGroup;
    private RadioGroup orientationRadioGroup;
    private ToggleButton safesearchToggle;
    private int checkedType;
    private int checkedOrientation;
    private Boolean safesearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        Intent intent = getIntent();

        typeRadioGroup = findViewById(R.id.typeGroup);
        orientationRadioGroup = findViewById(R.id.orientationGroup);
        safesearchToggle = findViewById(R.id.safesearchToggle);
        submitButton = findViewById(R.id.button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int checkedType = typeRadioGroup.getCheckedRadioButtonId();
                int checkedOrientation = orientationRadioGroup.getCheckedRadioButtonId();
                Boolean safesearch = safesearchToggle.isChecked();

                MainActivity.changeFilter(checkedType, checkedOrientation, safesearch);

                FilterActivity.super.onBackPressed();
            }
        });


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        checkedType = typeRadioGroup.getCheckedRadioButtonId();
        outState.putInt("type", checkedType);
        checkedOrientation = orientationRadioGroup.getCheckedRadioButtonId();
        outState.putInt("orientation", checkedOrientation);
        safesearch = safesearchToggle.isChecked();
        outState.putBoolean("safesearch", safesearch);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        int type = savedInstanceState.getInt("type");
        int orientation = savedInstanceState.getInt("orientation");
        boolean safesearch = savedInstanceState.getBoolean("safesearch");

        typeRadioGroup.check(type);
        orientationRadioGroup.check(orientation);
        if (safesearch) {
            if (!safesearchToggle.isChecked()) {
                safesearchToggle.toggle();
            }
        }
        super.onRestoreInstanceState(savedInstanceState);
    }
}
