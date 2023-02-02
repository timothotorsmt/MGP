package com.sdm.mgpica;

// Written by Tan Sze Ting
// Modified by Timothy Luk


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.SurfaceView;

import java.util.LinkedList;

public class ActionButtonEntity implements EntityBase {
    private static ActionButtonEntity Instance = null;

    private Bitmap bmp = null;
    private Bitmap sbmp = null;

    int ScreenWidth, ScreenHeight;

    private float buttonDelay = 0;
    public LinkedList<ProjectileEntity> projectileEntities = new LinkedList<>();

    private boolean isDone = false;
    private float timeBetweenShots = 0.1f;
    private float timer = 0;
    private int xPos, yPos;
    private boolean Paused = false;
    private boolean Toggle = true;

    private boolean isInit = false;

    public boolean IsDone() {
        return isDone;
    }

    public void SetIsDone(boolean _isDone) {
        isDone = _isDone;
    }

    public void Init(SurfaceView _view) {
        bmp = BitmapFactory.decodeResource(_view.getResources(),
                R.drawable.action_button);

        DisplayMetrics metrics = _view.getResources().getDisplayMetrics();
        ScreenWidth = metrics.widthPixels;
        ScreenHeight = metrics.heightPixels;

        // Scale is hardcode number -- Adjust your own 00
        sbmp = Bitmap.createScaledBitmap(bmp, (int)(ScreenWidth)/3,
                (int) (ScreenHeight)/6, true);

        xPos = ScreenWidth - 220;
        yPos = ScreenHeight - 150;

        timer = 0;

        isInit = true;
    }

    public void Update(float _dt) {
        buttonDelay += _dt;

        if (TouchManager.Instance.HasTouch()) {
            if (TouchManager.Instance.HasTouch() && !GameSystem.Instance.GetIsPaused()) {
                float imgRadius = sbmp.getHeight() * 0.5f;

                if (Collision.SphereToSphere(TouchManager.Instance.GetPosX(),
                        TouchManager.Instance.GetPosY(), 0.0f, xPos, yPos, imgRadius)) {
                    //PlayerEntity.Create().iHealth = 0;

                    if (!PlayerEntity.Create().isMidair) {
                        PlayerEntity.Create().SetToJump();
                    } else {
                        if (PlayerEntity.Create().AmmoNumber > 0) {
                            if (timer > timeBetweenShots) {
                                timer = 0;

                                // Jump/Shoot
                                AudioManager.Instance.PlayAudio(R.raw.laser, GamePage.Instance.Volume/100);
                                ProjectileEntity pe = ProjectileEntity.Create();
                                projectileEntities.add(pe);
                                if (PlayerEntity.Create().PlayerMode == 2) {
                                    ProjectileEntity pe1 = ProjectileEntity.Create();
                                    pe1.bulletType = 2;
                                    projectileEntities.add(pe1);
                                    ProjectileEntity pe2 = ProjectileEntity.Create();
                                    pe2.bulletType = 3;
                                    projectileEntities.add(pe2);
                                }
                                PlayerEntity.Create().AmmoNumber--;
                                PlayerEntity.Create().SetStall();
                            } else {
                                timer += _dt;
                            }
                        }
                    }
                }
            }
        }
        else {
            Paused = false;
        }
    }

    public void Render(Canvas _canvas) {
        _canvas.drawBitmap(sbmp, xPos - sbmp.getWidth() * 0.5f,
                yPos - sbmp.getHeight() * 0.5f, null);
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

        return ENTITY_TYPE.ENT_ACTION_BUTTON;
    }

    public static ActionButtonEntity Create() {
        if (Instance == null) {
            Instance = new ActionButtonEntity();
            EntityManager.Instance.AddEntity(Instance, ENTITY_TYPE.ENT_ACTION_BUTTON);
        }
        return Instance;
    }

    public void Destroy(){
        Instance = null;
    }
}
