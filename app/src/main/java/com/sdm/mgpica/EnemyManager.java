package com.sdm.mgpica;

// Written by Timothy Luk

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.SurfaceView;

import java.util.LinkedList;

public class EnemyManager implements EntityBase {

    boolean isDone = false;
    private float xPos = 0, yPos = 0, yPos1 = 0, yPos2 = 0, yPos3;
    private float playerY = 0;

    // Singleton Instance
    public LinkedList<EnemyEntity> enemyList = new LinkedList<>();
    float timer = 0;
    float nextTimerInterval = 1.0f;

    int ScreenWidth, ScreenHeight;

    public boolean IsDone() {
        return isDone;
    }
    public void SetIsDone(boolean _isDone) {
        isDone = _isDone;
    }

    public void Init(SurfaceView _view) {
        // get the screen size
        DisplayMetrics metrics = _view.getResources().getDisplayMetrics();
        ScreenWidth = metrics.widthPixels;
        ScreenHeight = metrics.heightPixels + 120;

        timer = 0;
    }
    public void Update(float _dt) {
        if (GameSystem.Instance.GetIsPaused())
            return;

        if (PlayerEntity.Create().isMoving && !PlayerEntity.Create().isJumping) {
            timer += _dt;
        }
        if (timer > nextTimerInterval) {
            nextTimerInterval = (float) Math.random() * (5.0f) + 2.0f;
            timer = 0;
            EnemyEntity enem = EnemyEntity.Create();

            enemyList.add(enem);
        }

        EnemyCollisions();

        for (int i = 0; i < enemyList.size(); i++) {
            if (enemyList.get(i).GetPosY() < -50) {
                enemyList.get(i).SetPosX(-100);
                enemyList.get(i).SetIsDone(true);
                enemyList.remove(enemyList.get(i));
            }
        }
    }

    public void EnemyCollisions() {
        for (EnemyEntity enem: enemyList) {
            enem.collision();
        }
    }

    public void Render(Canvas _canvas) {

    }

    public boolean IsInit() {
        return true;
    }

    public void SetRenderLayer(int _newLayer) {

    }
    public int GetRenderLayer() {
        return LayerConstants.BACKGROUND_LAYER;
    }

    public EntityBase.ENTITY_TYPE GetEntityType() {
        return EntityBase.ENTITY_TYPE.ENT_DEFAULT;
    }

    public static EnemyManager Create() {
        EnemyManager results  = new EnemyManager();
        EntityManager.Instance.AddEntity(results, EntityBase.ENTITY_TYPE.ENT_DEFAULT);
        return results;
    }
}
