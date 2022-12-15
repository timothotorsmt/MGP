package com.sdm.mgpica;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class Losescreen extends Activity implements View.OnClickListener, StateBase {  //Using StateBase class

    //Define buttons
    private Button btn_menu;

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

        StateManager.Instance.AddState(new Losescreen());
    }

    @Override
    //Invoke a callback event in the view
    public void onClick(View v)
    {
        // Intent = action to be performed.
        // Intent is an object provides runtime binding.
        // new instance of this object intent

        Intent intent = new Intent();

        if (v == btn_menu)
        {
            //Intent intent = new Intent();
            // intent --> to set to another class which another page or screen that we are launching.
            //intent.setClass(this, Mainmenu.class);
            finish();
            intent.setClass(this, Mainmenu.class);
            StateManager.Instance.ChangeState("Mainmenu"); // Default is like a loading page

            PlayerEntity.Create().iHealth = 100;
        }
        startActivity(intent);
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
