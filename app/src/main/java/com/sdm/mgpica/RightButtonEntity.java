package com.sdm.mgpica;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.SurfaceView;

// Written By TanSzeTing2022

public class RightButtonEntity implements EntityBase {

    private Bitmap bmp = null;
    private Bitmap sbmp = null;

    int ScreenWidth, ScreenHeight;

    private float buttonDelay = 0;

    private boolean isDone = false;
    private int xPos, yPos;
    private boolean Paused = false;

    private boolean isInit = false;

    public boolean IsDone() {
        return isDone;
    }

    public void SetIsDone(boolean _isDone) {
        isDone = _isDone;
    }

    public void Init(SurfaceView _view) {
        bmp = BitmapFactory.decodeResource(_view.getResources(),
                R.drawable.right_arrow);

        DisplayMetrics metrics = _view.getResources().getDisplayMetrics();
        ScreenWidth = metrics.widthPixels;
        ScreenHeight = metrics.heightPixels;

        // Scale is hardcode number -- Adjust your own 00
        sbmp = Bitmap.createScaledBitmap(bmp, (int)(ScreenWidth)/4,
                (int) (ScreenHeight)/7, true);

        xPos = 450;
        yPos = ScreenHeight - 150;

        isInit = true;
    }

    public void Update(float _dt) {
        buttonDelay += _dt;

        if (TouchManager.Instance.HasTouch() && PlayerEntity.Create().ControlScheme != 1){
            if (!Paused) {
                float imgRadius = sbmp.getHeight() * 0.5f;

                if (Collision.SphereToSphere(TouchManager.Instance.GetPosX(),
                        TouchManager.Instance.GetPosY(), 0.0f, xPos, yPos, imgRadius)
                        && buttonDelay >= 0.25) {
                    if (PlayerEntity.Create().GetPosX() < ScreenWidth)
                        PlayerEntity.Create().SetPosX((int) (PlayerEntity.Create().GetPosX() + (1000 * _dt)));
                    else
                        PlayerEntity.Create().SetPosX(ScreenWidth);
                }
            }
        }
        else
            Paused = false;
    }

    public void Render(Canvas _canvas) {
        if (PlayerEntity.Create().ControlScheme != 1) {
            _canvas.drawBitmap(sbmp, xPos - sbmp.getWidth() * 0.5f,
                    yPos - sbmp.getHeight() * 0.5f, null);
        }
    }

    public boolean IsInit(){
        return isInit;
    }

    public void SetRenderLayer(int _newLayer){
        return;
    }

    public int GetRenderLayer(){
        return LayerConstants.RENDERBUTTON_LAYER;
    }

    public EntityBase.ENTITY_TYPE GetEntityType(){

        return ENTITY_TYPE.ENT_R_BUTTON;
    }

    public static RightButtonEntity Create(){
        RightButtonEntity result = new RightButtonEntity();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_R_BUTTON);
        return result;
    }
}
