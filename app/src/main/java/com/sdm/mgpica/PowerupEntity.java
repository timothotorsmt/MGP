package com.sdm.mgpica;

import android.app.Notification;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.SurfaceView;

import java.util.Random;

// Written by Tan Sze Ting
// Edited by Timothy Luk

public class PowerupEntity implements EntityBase, Collidable {
    // Comment for now and use if code from Slide no 7 is type in
    int ScreenWidth, ScreenHeight;

    private Bitmap bmp = null;
    private Bitmap sbmp = null;

    private boolean isDone = false;
    private int xPos = 0, yPos = 0;
    private int xOffset =0;
    private float oscillation = 0;
    public float width = 150;

    private boolean isInit = false;
    private boolean hasTouched = false;

    private boolean isMidair = true;
    private boolean isJumping = false;

    public boolean IsDone() {
        return isDone;
    }

    public void SetIsDone(boolean _isDone) {
        isDone = _isDone;
    }

    public void Init(SurfaceView _view) {
        DisplayMetrics metrics = _view.getResources().getDisplayMetrics();
        ScreenWidth = metrics.widthPixels;
        ScreenHeight = metrics.heightPixels;

        Random rand = new Random();
        xOffset = ScreenWidth / 2;
        yPos = (int) ScreenHeight * 2 - 50;


        bmp = BitmapFactory.decodeResource(_view.getResources(),
                R.drawable.powerup);

        sbmp = Bitmap.createScaledBitmap(bmp, (int) (ScreenWidth/8), (ScreenWidth/8), true);

        isInit = true;
    }

    public void Update(float _dt) {
        if (GameSystem.Instance.GetIsPaused())
            return;

        yPos -= _dt * 200;
        oscillation += _dt;
        xPos = (int) (Math.sin(oscillation) * (ScreenWidth / 3) + xOffset);

        if ((yPos) < 0) {
            SetIsDone(true);
        }
    }

    public boolean collision(){
        // Collision (i think)
        AABB playerAABB = new AABB();
        playerAABB.minX = PlayerEntity.Create().GetPosX();
        playerAABB.minY = PlayerEntity.Create().yPosOnScreen;
        playerAABB.height = PlayerEntity.Create().width;
        playerAABB.width = PlayerEntity.Create().width;

        AABB enemyAABB = new AABB();
        enemyAABB.minX = xPos;
        enemyAABB.minY = yPos - PlayerEntity.Create().GetPosY();
        enemyAABB.width = width * 2;
        enemyAABB.height = width * 2;

        if (Collision.AABBtoAABB(playerAABB, enemyAABB)) {
            PlayerEntity.Create().PlayerMode = 2;
            SetIsDone(true);
            xOffset = (int) (ScreenWidth * 1.5);
        }

        return true;
    }

    public void Render(Canvas _canvas) {
        _canvas.drawBitmap(sbmp, xPos, (int) (yPos - PlayerEntity.Create().GetPosY()), null);
    }

    public boolean IsInit(){

        return bmp != null;
    }

    public void SetRenderLayer(int _newLayer){
        return;
    }

    public int GetRenderLayer(){
        return LayerConstants.RENDERPROJECTILE_LAYER;
    }

    public ENTITY_TYPE GetEntityType(){

        return ENTITY_TYPE.ENT_PROJECTILE;
    }

    public static PowerupEntity Create(){
        PowerupEntity result = new PowerupEntity();
        EntityManager.Instance.AddEntity(result,ENTITY_TYPE.ENT_PROJECTILE);
        return result;
    }

    @Override
    public String GetType() {
        return null;
    }

    @Override
    public float GetPosX() {
        return xPos;
    }

    @Override
    public float GetPosY() {
        return yPos;
    }

    public void SetPosX(int xPos) {
        this.xPos = xPos;
    }

    @Override
    public float GetRadius() {
        return (width / 2);
    }

    @Override
    public void OnHit(Collidable _other) {

    }
}