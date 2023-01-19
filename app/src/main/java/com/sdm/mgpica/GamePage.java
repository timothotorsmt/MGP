package com.sdm.mgpica;

// Created by TanSiewLan2021
// Create a GamePage is an activity class used to hold the GameView which will have a surfaceview

// Edited by Tan Sze Ting

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

public class GamePage extends Activity {

    public static GamePage Instance = null;
    public boolean Controls = false;
    public int Volume = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //To make fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE); // Hide titlebar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);  // Hide topbar

        Instance = this;

        // Retrieving the value using its keys the file name
        // must be same in both saving and retrieving the data
        SharedPreferences sh = getSharedPreferences("com.sdm.mgpica.sharedpreferences", MODE_PRIVATE);

        // The value will be default as empty string because for
        // the very first time when the app is opened, there is nothing to show
        Controls = sh.getBoolean("controls", false);
        Volume = sh.getInt("volume", 100);


        setContentView(new GameView(this)); // Surfaceview = GameView
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        // WE are hijacking the touch event into our own system
        int x = (int) event.getX();
        int y = (int) event.getY();

        TouchManager.Instance.Update(x, y, event.getAction());

        return true;
    }

    public void toLossScreen(){
        finish();
        Intent intent = new Intent();
        intent.setClass(this, Losescreen.class);
        StateManager.Instance.ChangeState("Losescreen"); // Default is like a loading page
        startActivity(intent);
    }

}

