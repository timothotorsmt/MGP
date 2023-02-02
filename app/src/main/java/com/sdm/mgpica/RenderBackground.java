package com.sdm.mgpica;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.SurfaceView;

import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

// Written by Timothy and Sze Ting
// Written by Timothy (platform generation)

public class RenderBackground implements EntityBase {
    boolean isDone = false;
    private Bitmap bmp = null;
    private Bitmap bmp1 = null;
    private Bitmap bmp2 = null;
    private Bitmap bmp3 = null;
    private float xPos = 0, yPos = 0, yPos1 = 0, yPos2 = 0, yPos3;
    private float playerY = 0;

    // Singleton Instance
    private LinkedList<BlockEntity> platforms = new LinkedList<>();
    private LinkedList<PowerupEntity> powerup = new LinkedList<>();
    float timer = 0;
    float lastPlayerY = 0.0f;

    int ScreenWidth, ScreenHeight;
    private Bitmap scaledbmp = null; // will be a scaled version of the bmp based on the screenWidth and Height
    private Bitmap scaledbmp1 = null;
    private Bitmap scaledbmp2 = null;
    private Bitmap scaledbmp3 = null;

    boolean HasWallInit = false;
    float interval = 0;

    public boolean IsDone() {
        return isDone;
    }
    public void SetIsDone(boolean _isDone) {
        isDone = _isDone;
    }

    public void Init(SurfaceView _view) {
        // Load the image
        bmp = BitmapFactory.decodeResource(_view.getResources(), R.drawable.background);
        bmp1 = BitmapFactory.decodeResource(_view.getResources(), R.drawable.l1);
        bmp2 = BitmapFactory.decodeResource(_view.getResources(), R.drawable.l2);
        bmp3 = BitmapFactory.decodeResource(_view.getResources(), R.drawable.l3);

        // get the screen size
        DisplayMetrics metrics = _view.getResources().getDisplayMetrics();
        ScreenWidth = metrics.widthPixels;
        ScreenHeight = metrics.heightPixels + 120;

        scaledbmp = Bitmap.createScaledBitmap(bmp, ScreenWidth, ScreenHeight, true);
        scaledbmp1 = Bitmap.createScaledBitmap(bmp1, ScreenWidth, ScreenHeight, true);
        scaledbmp2 = Bitmap.createScaledBitmap(bmp2, ScreenWidth, ScreenHeight, true);
        scaledbmp3 = Bitmap.createScaledBitmap(bmp3, ScreenWidth, ScreenHeight, true);

        timer = 0;
    }
    public void Update(float _dt) {
        if (GameSystem.Instance.GetIsPaused())
            return;

        if (Math.abs(-PlayerEntity.Create().GetPosY() - playerY) > 0.1) {
            playerY = LinearInterpolation.Lerp(playerY, -PlayerEntity.Create().GetPosY(), 0.2f);
        } else {
            playerY = -PlayerEntity.Create().GetPosY();
        }
        //if (!TouchManager.Instance.IsDown()) {
            yPos = (playerY%ScreenHeight);
            //yPos -= _dt * 100; // 500 is just a variable number; can be edited
            //if (yPos < -ScreenHeight) {
            //    yPos = 0;
            //}
            yPos1 = (playerY%(ScreenHeight/3))*3;
            //yPos1 -= _dt * 300; // 500 is just a variable number; can be edited
            //if (yPos1 < -ScreenHeight) {
            //    yPos1 = 0;
            //}
            yPos2 = (playerY%(ScreenHeight/5))*5;
            //yPos2 -= _dt * 500; // 500 is just a variable number; can be edited
            //if (yPos2 < -ScreenHeight) {
            //    yPos2 = 0;
            //}
            yPos3 = (playerY%(ScreenHeight/7))*7;
            //yPos3 -= _dt * 700; // 500 is just a variable number; can be edited
            //if (yPos3 < -ScreenHeight) {
            //    yPos3 = 0;
            //}
        //}

        if (!HasWallInit) {
            HasWallInit=  true;
            // Generate Cave Walls at the start of the game

            for (int i = 0; i < ScreenHeight / (ScreenWidth / 10); i++) {
                BlockEntity beLCW = BlockEntity.Create();
                beLCW.SetXoffset(0 - (ScreenWidth / 20));
                beLCW.SetYoffset(-ScreenHeight + (ScreenWidth / 10) * i);
                beLCW.SetType(1);

                platforms.add(beLCW);

                BlockEntity beRCW = BlockEntity.Create();
                beRCW.SetXoffset(ScreenWidth - ScreenWidth / 20);
                beRCW.SetYoffset(-ScreenHeight + (ScreenWidth / 10) * i);
                beRCW.SetType(1);

                platforms.add(beRCW);
            }
            lastPlayerY = 0;
        }

        if (Math.abs(-playerY - lastPlayerY) > (ScreenWidth / 3)) {
            lastPlayerY = -playerY;
            //nextTimerInterval = (float)Math.random() * (5.0f) + 2.0f;
            //int scale = 1;
            //timer = 0;
            //int b = (int)(Math.random() * (ScreenWidth / 4 * 3));

            MapSection ms = new MapSection();
            ms.GenerateChunk();

            for (int i = 0; i < 5; i++) {
                // Generate Cave Walls
                BlockEntity beLCW = BlockEntity.Create();
                beLCW.SetXoffset(0 - (ScreenWidth / 20));
                beLCW.SetYoffset((ScreenWidth / 10) * i);
                beLCW.SetType(1);

                platforms.add(beLCW);

                // Generate Cave Walls
                BlockEntity beRCW = BlockEntity.Create();
                beRCW.SetXoffset(ScreenWidth - ScreenWidth / 20);
                beRCW.SetYoffset((ScreenWidth / 10) * i);
                beRCW.SetType(1);

                platforms.add(beRCW);

                for (int j = 0; j < 9; j++) {
                    if (ms.Section[i][j] != 0) {
                        BlockEntity be = BlockEntity.Create();
                        be.SetXoffset((ScreenWidth / 20) + (ScreenWidth / 10) * j);
                        be.SetYoffset((ScreenWidth / 10) * i);
                        be.SetType(ms.Section[i][j]);

                        platforms.add(be);

                    }
                }
            }

        }

        if (-playerY > interval) {
            Random rand = new Random();
            int randInterval = rand.nextInt(2) + 1;
            interval = -playerY + ScreenHeight / 2 * randInterval;

            // Spawn a powerup
            PowerupEntity temp_powerup = PowerupEntity.Create();
            powerup.add(temp_powerup);
        }

        boolean isCollision = PlatformCollisions();

        PlayerEntity.Create().isMidair = isCollision;
        PlayerEntity.Create().isMoving  = isCollision;
    }

    public boolean PlatformCollisions() {
        boolean playerMove = true;
        for (BlockEntity be: platforms) {
            if (be.collision() == false) {
                playerMove = false;
            }
        }

        for (PowerupEntity pe : powerup) {
            pe.collision();
        }

        return playerMove;
    }

    public void Render(Canvas _canvas) {
        _canvas.drawBitmap(scaledbmp, xPos, yPos, null);
        _canvas.drawBitmap(scaledbmp, xPos, (yPos + ScreenHeight), null);

        _canvas.drawBitmap(scaledbmp1, xPos, yPos1, null);
        _canvas.drawBitmap(scaledbmp1, xPos, (yPos1 + ScreenHeight), null);

        _canvas.drawBitmap(scaledbmp2, xPos, yPos2, null);
        _canvas.drawBitmap(scaledbmp2, xPos, (yPos2 + ScreenHeight), null);

        _canvas.drawBitmap(scaledbmp3, xPos, yPos3, null);
        _canvas.drawBitmap(scaledbmp3, xPos, (yPos3 + ScreenHeight), null);
    }

    public boolean IsInit() {
        return bmp != null;
    }

    public void SetRenderLayer(int _newLayer) {

    }

    public int GetRenderLayer() {
        return LayerConstants.BACKGROUND_LAYER;
    }

    public ENTITY_TYPE GetEntityType() {
        return ENTITY_TYPE.ENT_DEFAULT;
    }

    public static RenderBackground Create() {
        RenderBackground results  = new RenderBackground();
        EntityManager.Instance.AddEntity(results, ENTITY_TYPE.ENT_DEFAULT);
        return results;
    }
}