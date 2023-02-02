package com.sdm.mgpica;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.SurfaceView;

import java.util.LinkedList;
import java.util.Map;

// Written by Timothy and Sze Ting
// Written by Timothy (platform generation)

public class RenderUI implements EntityBase {
    boolean isDone = false;
    private Bitmap bmp = null;
    private Bitmap bmp1 = null;
    private Bitmap bmp2 = null;
    private Bitmap bmp3 = null;
    private float xPos = 0, yPos = 0, yPos1 = 0, yPos2 = 0, yPos3;
    private float playerY = 0;

    int ScreenWidth, ScreenHeight;
    private Bitmap scaledbmp = null; // will be a scaled version of the bmp based on the screenWidth and Height
    private Bitmap scaledbmp1 = null;
    private Bitmap scaledbmp2 = null;
    private Bitmap scaledbmp3 = null;

    // Variables
    int HealthBarLength;

    public boolean IsDone() {
        return isDone;
    }
    public void SetIsDone(boolean _isDone) {
        isDone = _isDone;
    }

    public void Init(SurfaceView _view) {
        bmp = BitmapFactory.decodeResource(_view.getResources(), R.drawable.fillbar);
        bmp1 = BitmapFactory.decodeResource(_view.getResources(), R.drawable.scrollbar_handle);
        bmp3 = BitmapFactory.decodeResource(_view.getResources(), R.drawable.health);
        bmp2 = BitmapFactory.decodeResource(_view.getResources(), R.drawable.health_bar);

        DisplayMetrics metrics = _view.getResources().getDisplayMetrics();
        ScreenWidth = metrics.widthPixels;
        ScreenHeight = metrics.heightPixels;

        scaledbmp = Bitmap.createScaledBitmap(bmp, ScreenWidth/10 * 9, ScreenHeight / 5, true);
        scaledbmp1 = Bitmap.createScaledBitmap(bmp1, ScreenWidth/3, ScreenHeight / 20, true);
        scaledbmp2 = Bitmap.createScaledBitmap(bmp2, ScreenWidth/2, ScreenHeight / 8, true);

        HealthBarLength = ScreenWidth/8 * 3;
        scaledbmp3 = Bitmap.createScaledBitmap(bmp3, HealthBarLength, ScreenHeight / 20, true);
    }

    public void Update(float _dt) {
        if (PlayerEntity.Create().iHealth > 0) {
            HealthBarLength = (ScreenWidth / 31 * 3) * (PlayerEntity.Create().iHealth / 25);
            scaledbmp3 = Bitmap.createScaledBitmap(bmp3, HealthBarLength, ScreenHeight / 20, true);
        }
    }

    public void Render(Canvas _canvas) {
        _canvas.drawBitmap(scaledbmp, ScreenWidth/20, ScreenHeight / 20, null);
        for (int i = 0; i < PlayerEntity.Create().AmmoNumber; i++) {
            _canvas.drawBitmap(scaledbmp1, ScreenWidth/20 + (ScreenWidth/12 * i), ScreenHeight / 8, null);

        }
        _canvas.drawBitmap(scaledbmp2, ScreenWidth/19, ScreenHeight / 35, null);
        _canvas.drawBitmap(scaledbmp3, ScreenWidth/9, ScreenHeight / 15, null);

    }

    public boolean IsInit() {
        return bmp != null;
    }

    public void SetRenderLayer(int _newLayer) {

    }

    public int GetRenderLayer() {
        return LayerConstants.UI_LAYER;
    }

    public ENTITY_TYPE GetEntityType() {
        return ENTITY_TYPE.ENT_DEFAULT;
    }

    public static RenderUI Create() {
        RenderUI results  = new RenderUI();
        EntityManager.Instance.AddEntity(results, ENTITY_TYPE.ENT_DEFAULT);
        return results;
    }
}