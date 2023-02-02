package com.sdm.mgpica;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.content.Intent;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Switch;

// Created by TanSzeTing2022

public class Settings extends Activity implements OnClickListener, StateBase {  //Using StateBase class

    //Define buttons
    private Button btn_back;
    private Switch control_toggle;
    private Switch vibration_toggle;
    private SeekBar volume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide Title
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Hide Top Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.settings);

        btn_back = (Button)findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this); //Set Listener to this button --> Start Button

        control_toggle = (Switch)findViewById(R.id.control_toggle);
        vibration_toggle = (Switch)findViewById(R.id.vibration_toggle);
        // Retrieving the value using its keys the file name
        // must be same in both saving and retrieving the data
        SharedPreferences sh = getSharedPreferences("com.sdm.mgpica.sharedpreferences", MODE_PRIVATE);

        // The value will be default as empty string because for
        // the very first time when the app is opened, there is nothing to show
        control_toggle.setChecked(sh.getBoolean("controls", false));
        control_toggle.setOnCheckedChangeListener(this::onCheckedChanged);

        vibration_toggle.setChecked(sh.getBoolean("Vibration", true));
        vibration_toggle.setOnCheckedChangeListener(this::onCheckedChanged);

        volume = (SeekBar)findViewById(R.id.VolumeBar);
        volume.setProgress(sh.getInt("volume", 100));
        volume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (seekBar == volume) {
                    // Storing data into SharedPreferences
                    SharedPreferences sharedPreferences = getSharedPreferences("com.sdm.mgpica.sharedpreferences",MODE_PRIVATE);

                    // Creating an Editor object to edit(write to the file)
                    SharedPreferences.Editor myEdit = sharedPreferences.edit();

                    // Storing the key and its value as the data fetched from edittext
                    myEdit.putInt("volume", i);

                    // Once the changes have been made,
                    // we need to commit to apply those changes made,
                    // otherwise, it will throw an error
                    myEdit.commit();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    @Override
    //Invoke a callback event in the view
    public void onClick(View v)
    {
        // Intent = action to be performed.
        // Intent is an object provides runtime binding.
        // new instance of this object intent

        if (v == btn_back)
        {
            Intent intent = new Intent();

            finish();
            intent.setClass(this, Mainmenu.class);
            // intent --> to set to another class which another page or screen that we are launching.
            StateManager.Instance.ChangeState("Mainmenu"); // Default is like a loading page
            startActivity(intent);
        }
    }

    @Override
    public void Render(Canvas _canvas) {
    }

    @Override
    public void OnEnter(SurfaceView _view) {
    }

    @Override
    public void OnExit() {
    }

    @Override
    public void Update(float _dt) {
    }

    @Override
    public String GetName() {
        return "Settings";
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView == control_toggle) {
            // Storing data into SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences("com.sdm.mgpica.sharedpreferences",MODE_PRIVATE);

            // Creating an Editor object to edit(write to the file)
            SharedPreferences.Editor myEdit = sharedPreferences.edit();

            // Storing the key and its value as the data fetched from edittext
            myEdit.putBoolean("controls", isChecked);

            // Once the changes have been made,
            // we need to commit to apply those changes made,
            // otherwise, it will throw an error
            myEdit.commit();
        }
        if (buttonView == vibration_toggle) {
            // Storing data into SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences("com.sdm.mgpica.sharedpreferences",MODE_PRIVATE);

            // Creating an Editor object to edit(write to the file)
            SharedPreferences.Editor myEdit = sharedPreferences.edit();

            // Storing the key and its value as the data fetched from edittext
            myEdit.putBoolean("Vibration", isChecked);

            // Once the changes have been made,
            // we need to commit to apply those changes made,
            // otherwise, it will throw an error
            myEdit.commit();
        }
    }
}

