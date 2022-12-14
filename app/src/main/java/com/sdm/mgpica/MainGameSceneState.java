package com.sdm.mgpica;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceView;

// Created by TanSiewLan2021

public class MainGameSceneState implements StateBase {
    private float timer = 0.0f;

    @Override
    public String GetName() {
        return "MainGame";
    }

    @Override
    public void OnEnter(SurfaceView _view)
    {
        RenderBackground.Create();
        RenderTextEntity.Create();
        //SmurfEntity.Create();
        //StarEntity.Create(); // Week 8
        PlayerEntity.Create();
        PauseButtonEntity.Create(); // Week 8
        LeftButtonEntity.Create();
        RightButtonEntity.Create();
        ActionButtonEntity.Create();
        // Example to include another Renderview for Pause Button
    }

    @Override
    public void OnExit() {
        EntityManager.Instance.Clean();
        GamePage.Instance.finish();
    }

    @Override
    public void Render(Canvas _canvas)
    {
        EntityManager.Instance.Render(_canvas);
    }

    @Override
    public void Update(float _dt) {

        EntityManager.Instance.Update(_dt);

        if (PlayerEntity.Create().GetHealth() <= 0)
        {
            GamePage.Instance.toLossScreen();
        }
        if (TouchManager.Instance.IsDown()) {
            //Example of touch on screen in the main game to trigger back to Main menu
            //StateManager.Instance.ChangeState("Mainmenu");
        }
    }
}



