package com.sdm.mgpica;

// Created by TimothyLuk2023

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.SurfaceView;

public class BlockEntity implements EntityBase, Collidable {

    private Bitmap bmp = null;
    private Bitmap scaledbmp1 = null, scaledbmp2 = null, scaledbmp3 = null;
    private int ScreenWidth, ScreenHeight;

    private boolean isDone = false;
    private int xPos = 0; private float yPos = 0;
    private int xOffset = 0;
    private int blockScale = 1;
    private float tempPosition;

    private boolean isInit = false;
    private boolean hasTouched = false;


    public boolean IsDone() {
        return isDone;
    }

    public void SetIsDone(boolean _isDone) {
        isDone = _isDone;
    }

    @Override
    public void Init(SurfaceView _view) {
        bmp = BitmapFactory.decodeResource(_view.getResources(),
                R.drawable.groundstone);

        // get the screen size
        DisplayMetrics metrics = _view.getResources().getDisplayMetrics();
        ScreenWidth = metrics.widthPixels;
        ScreenHeight = metrics.heightPixels;

        scaledbmp1 = Bitmap.createScaledBitmap(bmp, (int) (ScreenWidth/5), 50, true);
        scaledbmp2 = Bitmap.createScaledBitmap(bmp, (int) (ScreenWidth/4), 50, true);
        scaledbmp3 = Bitmap.createScaledBitmap(bmp, (int) (ScreenWidth/3), 50, true);

        isInit = true;
    }

    public void Update(float _dt) {
        if (GameSystem.Instance.GetIsPaused())
            return;

        if (PlayerEntity.Create().isJumping) {
            tempPosition = yPos + _dt * PlayerEntity.Create().speed * 2;
            yPos = LinearInterpolation.Lerp(yPos, tempPosition, 0.5f);
        }
        else if (PlayerEntity.Create().isMoving == true) {
            tempPosition = yPos - _dt * PlayerEntity.Create().speed;
            yPos = LinearInterpolation.Lerp(yPos, tempPosition, 0.5f);
        }

        if ((ScreenHeight + yPos) < 100) {
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

        AABB platformAABB = new AABB();
        platformAABB.minY = ScreenHeight + yPos;
        platformAABB.width = (ScreenWidth / ((5 - (blockScale)) * 2));
        platformAABB.minX = xOffset + (platformAABB.width);
        platformAABB.height = 50;

        if (Collision.AABBtoAABB(playerAABB, platformAABB)) {
            if (platformAABB.minY - playerAABB.minY > (playerAABB.height - 20)) {
                PlayerEntity.Create().AmmoNumber = PlayerEntity.Create().MaxAmmoNumber;
                return false;
            }
        }

        return true;
    }

    public void Setoffset (int NewxOffset) {
        xOffset = NewxOffset;
    }
    public void SetScale (int newScale) {
        blockScale = newScale;
    }

    public void Render(Canvas _canvas) {
        if (blockScale == 0) {
            _canvas.drawBitmap(scaledbmp1, xPos + xOffset, ScreenHeight + yPos, null);
        }
        else if (blockScale == 1) {
            _canvas.drawBitmap(scaledbmp2, xPos + xOffset, ScreenHeight + yPos, null);
        } else {
            _canvas.drawBitmap(scaledbmp3, xPos + xOffset, ScreenHeight + yPos, null);
        }
    }

    public boolean IsInit(){
        return bmp != null;
    }

    public void SetRenderLayer(int _newLayer){
        return;
    }

    public int GetRenderLayer(){
        return LayerConstants.TERRAIN_LAYER;
    }

    public ENTITY_TYPE GetEntityType(){

        return ENTITY_TYPE.ENT_SMURF;
    }

    public static BlockEntity Create(){
        BlockEntity result = new BlockEntity();
        EntityManager.Instance.AddEntity(result,ENTITY_TYPE.ENT_BLOCKS);
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

    @Override
    public float GetRadius() {
        return 0;
    }

    @Override
    public void OnHit(Collidable _other) {

    }
}
