package com.sdm.mgpica;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewDebug;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

// Written by Tan Sze Ting

public class LeaderboardScreen extends Activity implements View.OnClickListener, StateBase {  //Using StateBase class

    //Define buttons
    private Button btn_menu;

    private TextView name;
    private TextView score;

    private TextView num1;
    private TextView num2;
    private TextView num3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide Title
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Hide Top Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.losescreen);

        btn_menu = (Button)findViewById(R.id.menuButton);
        btn_menu.setOnClickListener(this); //Set Listener to this button --> Start Button

        score = (TextView)findViewById(R.id.scoreTxt);
        score.setText(String.valueOf(GameSystem.Instance.GetIntFromSave("Score")));


    }

    @Override
    //Invoke a callback event in the view
    public void onClick(View v)
    {
        // Intent = action to be performed.
        // Intent is an object provides runtime binding.
        // new instance of this object intent

        if (v == btn_menu)
        {
            Intent intent = new Intent();
            // intent --> to set to another class which another page or screen that we are launching.
            finish();
            intent.setClass(this, Mainmenu.class);
            StateManager.Instance.ChangeState("Mainmenu"); // Default is like a loading page

            startActivity(intent);
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
        return "Losescreen";
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

/*
 {

    private TextView tv_score;
    private EditText ed_text;
    private Button bt_apply;
    private Button bt_save;

    public static final String SHARED_PREFS = "SharePref";
    public static final String TEXT = "text";

    private String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Hide Title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Hide Top Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.leaderboard);

        tv_score = (TextView) findViewById(R.id.tv_score);
        ed_text = (EditText) findViewById(R.id.ed_text);
        bt_apply = (Button) findViewById(R.id.btn_apply);
        bt_save = (Button) findViewById(R.id.btn_save);

        bt_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_score.setText(ed_text.getText().toString());
            }
        });

        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savedData();
            }
        });

        loadData();
        updateViews();
    }

    public void savedData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(TEXT, tv_score.getText().toString());

        editor.apply();

        Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show();
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        text = sharedPreferences.getString(TEXT, "");
    }

    public void updateViews() {
        tv_score.setText(text);
    }
}
 */