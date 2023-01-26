package com.sdm.mgpica;

import android.app.Activity;
import android.content.Entity;
import android.graphics.Canvas;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceView;

// Created by TanSiewLan2021
// Edited by Timothy Luk
// Edited by Tan Sze Ting

public class MainGameSceneState implements StateBase {
    private float timer = 0.0f;
    boolean GameOver = false;

    @Override
    public String GetName() {
        return "MainGame";
    }

    @Override
    public void OnEnter(SurfaceView _view)
    {
        GameOver = false;

        RenderBackground.Create();
        PlayerEntity.Create();
        PauseButtonEntity.Create(); // Week 8
        ActionButtonEntity.Create();
        RenderTextEntity.Create();
        //SmurfEntity.Create();
        LeftButtonEntity.Create();
        RightButtonEntity.Create();
        //EnemyEntity.Create();
        EnemyManager.Create();
        // Example to include another Renderview for Pause Button
        if (!AudioManager.Instance.IsPlaying(R.raw.bgm)) // bgm loop
             AudioManager.Instance.PlayAudio(R.raw.bgm, GamePage.Instance.Volume);
    }

    @Override
    public void OnExit() {
        AudioManager.Instance.Release();

        EntityManager.Instance.Clean();

        // Destroy singleton instances
        PlayerEntity.Create().Destroy();
        ActionButtonEntity.Create().Destroy();
        PauseButtonEntity.Create().Destroy();

        GamePage.Instance.finish();
    }

    @Override
    public void Render(Canvas _canvas)
    {
        EntityManager.Instance.Render(_canvas);
    }

    @Override
    public void Update(float _dt) {
        if (!GameOver) {
            if (PlayerEntity.Create().iHealth <= 0) {
                if (NameInputDialogFragment.IsShown)
                    return;

                NameInputDialogFragment newNameInput = new NameInputDialogFragment();
                newNameInput.show(GamePage.Instance.getFragmentManager(), "NameInput");

                GameSystem.Instance.SaveEditBegin();
                    GameSystem.Instance.SetIntInSave("Score", PlayerEntity.Create().iTotalScore);
                GameSystem.Instance.SaveEditEnd();

                if (GameSystem.Instance.GetIntFromSave("Highscore") == 0 || GameSystem.Instance.GetIntFromSave("Highscore") < PlayerEntity.Create().iTotalScore)
                    GameSystem.Instance.SetIntInSave("Highscore", PlayerEntity.Create().iTotalScore);
                GameOver = true;
            }

            if (!AudioManager.Instance.IsPlaying(R.raw.bgm)) // bgm loop
                AudioManager.Instance.PlayAudio(R.raw.bgm, GamePage.Instance.Volume/100);

            EntityManager.Instance.Update(_dt);
        }

        //if (TouchManager.Instance.IsDown()) {
			
            //Example of touch on screen in the main game to trigger back to Main menu
            //StateManager.Instance.ChangeState("Mainmenu");
        //}
    }
}

