package com.sdm.mgpica;

// Created by TimothyLuk2023

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.SurfaceView;

public class BlockEntity implements EntityBase, Collidable {

    private Bitmap caveWallbmp = null, DBbmp = null, platformbmp = null;
    private Bitmap scaledCaveWallbmp = null, scaledDBbmp = null, scaledplatformbmp = null;
    private int ScreenWidth, ScreenHeight;

    private boolean isDone = false;
    private int xPos = 0; private float yPos = 0;
    private int xOffset = 0, yOffset =0;
    private int blockScale = 1;
    private float tempPosition;
    private int type = 1;

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
        caveWallbmp = BitmapFactory.decodeResource(_view.getResources(),
                R.drawable.cavewall);
        DBbmp = BitmapFactory.decodeResource(_view.getResources(),
                R.drawable.breakable);
        platformbmp = BitmapFactory.decodeResource(_view.getResources(),
                R.drawable.platform);

        // get the screen size
        DisplayMetrics metrics = _view.getResources().getDisplayMetrics();
        ScreenWidth = metrics.widthPixels;
        ScreenHeight = metrics.heightPixels;

        scaledCaveWallbmp = Bitmap.createScaledBitmap(caveWallbmp, (int) (ScreenWidth/10), (ScreenWidth/10), true);
        scaledDBbmp = Bitmap.createScaledBitmap(DBbmp, (int) (ScreenWidth/10), (ScreenWidth/10), true);
        scaledplatformbmp = Bitmap.createScaledBitmap(DBbmp, (int) (ScreenWidth/10), (ScreenWidth/10), true);

        isInit = true;
    }

    public void Update(float _dt) {
        if (GameSystem.Instance.GetIsPaused())
            return;

        if (PlayerEntity.Create().isJumping) {
            tempPosition = yPos + _dt * PlayerEntity.Create().speed * 6;
            yPos = LinearInterpolation.Lerp(yPos, tempPosition, 0.5f);
        }
        else if (PlayerEntity.Create().isMoving == true) {
            tempPosition = yPos - _dt * PlayerEntity.Create().speed * 3;
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
        platformAABB.minY = ScreenHeight + yPos + yOffset;
        platformAABB.width = (ScreenWidth / 10);
        platformAABB.minX = xOffset + (platformAABB.width);
        platformAABB.height = 50;

        if (Collision.AABBtoAABB(playerAABB, platformAABB)) {
            //TODO: fix this fucking piece of shit
            //if (platformAABB.minX + platformAABB.width > playerAABB.minX && platformAABB.minX > playerAABB.minX && Math.abs(platformAABB.minY - playerAABB.minY) < platformAABB.height / 2). {
            //    PlayerEntity.Create().SetPosX((int)(platformAABB.minX - platformAABB.width * 2));
            //} else if (platformAABB.minX < playerAABB.minX + playerAABB.width && platformAABB.minX < playerAABB.minX && Math.abs(platformAABB.minY - playerAABB.minY) < platformAABB.height / 2) {
            //    PlayerEntity.Create().SetPosX((int)(platformAABB.minX + platformAABB.width / 1.5));
            //}

            if (platformAABB.minY + playerAABB.minY> (playerAABB.height - 10)) {
                PlayerEntity.Create().AmmoNumber = PlayerEntity.Create().MaxAmmoNumber;
                return false;
            }
        }

        if (getType() == 2) {
            for (ProjectileEntity pe : ActionButtonEntity.Create().projectileEntities) {
                AABB ProjectileAABB = new AABB();
                ProjectileAABB.minX = pe.GetPosX() + pe.width / 3;
                ProjectileAABB.minY = PlayerEntity.Create().yPosOnScreen + pe.GetPosY() - PlayerEntity.Create().GetPosY();
                ProjectileAABB.height = pe.width;
                ProjectileAABB.width = pe.width / 2;

                if (Collision.AABBtoAABB(ProjectileAABB, platformAABB)) {
                    xOffset = -ScreenWidth;
                    SetIsDone(true);
                    pe.SetPosX(-100);
                    pe.SetIsDone(true);
                }
            }
        }

        return true;
    }

    public void SetXoffset (int NewxOffset) {
        xOffset = NewxOffset;
    }
    public void SetYoffset (int NewyOffset) {
        yOffset = NewyOffset;
    }

    public void SetType (int NewType) {
        type = NewType;
    }
    public int getType() {
        return type;
    }

    public void Render(Canvas _canvas) {
        switch (type) {
            case 1:
                _canvas.drawBitmap(scaledCaveWallbmp, xPos + xOffset, ScreenHeight + yOffset + yPos, null);
                break;
            case 2:
                _canvas.drawBitmap(scaledDBbmp, xPos + xOffset, ScreenHeight + yOffset + yPos, null);
                break;
            case 3:
                _canvas.drawBitmap(scaledplatformbmp, xPos + xOffset, ScreenHeight + yOffset + yPos, null);
                break;
        }
    }

    public boolean IsInit(){
        return caveWallbmp != null;
    }

    public void SetRenderLayer(int _newLayer){
        return;
    }

    public int GetRenderLayer(){
        return LayerConstants.TERRAIN_LAYER;
    }

    public ENTITY_TYPE GetEntityType(){

        return ENTITY_TYPE.ENT_BLOCKS;
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
