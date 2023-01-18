package com.sdm.mgpica;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.SurfaceView;

// Written by Sze Ting
// Edited by Timothy

public class PauseButtonEntity implements EntityBase {

    private Bitmap bmp, bmp1 = null;
    private Bitmap sbmp, sbmp1 = null;

    int ScreenWidth, ScreenHeight;

    private float buttonDelay = 0;

    private boolean isDone = false;
    private int xPos, yPos;
    public boolean Paused = false;

    static PauseButtonEntity Instance = null;

    private boolean isInit = false;

    public boolean IsDone() {
        return isDone;
    }

    public void SetIsDone(boolean _isDone) {
        isDone = _isDone;
    }

    public void Init(SurfaceView _view) {
        bmp = BitmapFactory.decodeResource(_view.getResources(),
                R.drawable.pause);
        bmp1 = BitmapFactory.decodeResource(_view.getResources(),
                R.drawable.pause1);

        DisplayMetrics metrics = _view.getResources().getDisplayMetrics();
        ScreenWidth = metrics.widthPixels;
        ScreenHeight = metrics.heightPixels;

        // Scale is hardcode number -- Adjust your own 00
        sbmp = Bitmap.createScaledBitmap(bmp, 200, 200, true);

        sbmp1 = Bitmap.createScaledBitmap(bmp1, 200, 200, true);

        xPos = ScreenWidth - 150;
        yPos = 150;

        isInit = true;
    }

    public void Update(float _dt) {
        buttonDelay += _dt;

        if (TouchManager.Instance.HasTouch()){
            if (TouchManager.Instance.IsDown() && !Paused) {
                float imgRadius = sbmp.getHeight() * 0.5f;

                if (Collision.SphereToSphere(TouchManager.Instance.GetPosX(),
                        TouchManager.Instance.GetPosY(), 0.0f, xPos, yPos, imgRadius)
                        && buttonDelay >= 0.25) {
                    //Paused = true;

                    if (PauseConfirmDialogFragment.IsShown)
                        return;

                    PauseConfirmDialogFragment newPauseConfirm = new PauseConfirmDialogFragment();
                    newPauseConfirm.show(GamePage.Instance.getFragmentManager(), "PauseConfirm");

                    // buttonDelay = 0;
                    // GameSystem.Instance.SetIsPaused(!GameSystem.Instance.GetIsPaused());
                }
            }
        }
        //else
            //Paused = false;
    }

    public void Render(Canvas _canvas) {

        if (Paused == false)
            _canvas.drawBitmap(sbmp, xPos - sbmp.getWidth() * 0.5f,
                    yPos - sbmp.getHeight() * 0.5f, null);

        else
            _canvas.drawBitmap(sbmp1, xPos - sbmp1.getWidth() * 0.5f,
                    yPos - sbmp1.getHeight() * 0.5f, null);
    }

    public boolean IsInit(){
        return isInit;
    }

    public void SetRenderLayer(int _newLayer){
        return;
    }

    public int GetRenderLayer(){
        return LayerConstants.UI_LAYER;
    }

    public EntityBase.ENTITY_TYPE GetEntityType(){

        return EntityBase.ENTITY_TYPE.ENT_PAUSE;
    }

    public static PauseButtonEntity Create() {
        if (Instance == null) {
            Instance = new PauseButtonEntity();
            EntityManager.Instance.AddEntity(Instance, EntityBase.ENTITY_TYPE.ENT_PAUSE);
        }
        return Instance;
    }

    public void Destroy(){
        Instance = null;
    }
}
