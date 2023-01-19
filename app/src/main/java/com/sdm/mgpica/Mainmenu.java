package com.sdm.mgpica;

import android.app.Activity;
import android.graphics.Canvas;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.content.Intent;
import android.widget.ImageButton;

// Created by TanSiewLan2021
// Edited by Tan Sze Ting
// Edited by Timothy Luk

public class Mainmenu extends Activity implements OnClickListener, StateBase {  //Using StateBase class

    //Define buttons
    private Button btn_start;
    private Button btn_settings;
    private Button btn_quit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide Title
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Hide Top Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.mainmenu);

        btn_start = (Button)findViewById(R.id.startButton);
        btn_start.setOnClickListener(this); //Set Listener to this button --> Start Button

        btn_settings = (Button)findViewById(R.id.settingsButton);
        btn_settings.setOnClickListener(this); //Set Listener to this button --> Start Button

        btn_quit = (Button)findViewById(R.id.quitButton);
        btn_quit.setOnClickListener(this);

        StateManager.Instance.AddState(new Mainmenu());
    }

    @Override
    //Invoke a callback event in the view
    public void onClick(View v)
    {
        // Intent = action to be performed.
        // Intent is an object provides runtime binding.
        // new instance of this object intent


        if (v == btn_start)
        {
            Intent intent = new Intent();
            finish();
            // intent --> to set to another class which another page or screen that we are launching.
            intent.setClass(this, GamePage.class);
 				 StateManager.Instance.ChangeState("MainGame"); // Default is like a loading page

            startActivity(intent);
        }
        if (v == btn_settings)
        {
            Intent intent = new Intent();
            finish();
            // intent --> to set to another class which another page or screen that we are launching.
            intent.setClass(this, Settings.class);
                StateManager.Instance.ChangeState("Settings"); // Default is like a loading page

            startActivity(intent);
        }
        if (v == btn_quit)
        {
            finish();
            System.exit(0);
        }
    }

    @Override
    public void Render(Canvas _canvas)
    {
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
        return "Mainmenu";
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
}
